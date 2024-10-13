const User = require('../models/user');
const RecycleTransaction = require('../models/recycleTransaction');
const RecycleItem = require('../models/recycleItem');
const { getUserCityLeaderboard, getUserCountryLeaderboard, getUserFriendsLeaderboard } = require('./leaderboard');

const sendNotification = require('./notificationService');

// Used to fix the timezone issue, we need UTC+3:
const moment = require('moment-timezone');


const getTop10RecycledItems = async () => {
    const result = await RecycleTransaction.aggregate([
        // Pay attention that recycleItems is an array, so we have to "unwind" it (it means that we have to "split" the array into separate documents)
        { $unwind: "$recycleItems" },
      
        // Now, we group by the itemId and sum the quantity of each item (it is easier to use Mongo's complex operations when relevant, just like we learned in Advanced Programming 2 Course...)
        {
          $group: {
            _id: "$recycleItems.itemId",
            totalQuantity: { $sum: "$recycleItems.quantity" }
          }
        },
      
        // Now, we sort the result by the totalQuantity in descending order:
        { $sort: { totalQuantity: -1 } },
      
        // Also, we take only 10 of them
        { $limit: 10 },
      
        // This is an important part which took most of the time for this- we only get partial data about the item (from the RecycleTransaction collection), so we want to lookup the item details from the RecycleItem collection!
        {
          $lookup: {
            from: "recycleitems",
            localField: "_id",
            foreignField: "_id",
            as: "itemDetails"
          }
        },
      
        // Similarly to what we've done before, we have to unwind the itemDetails- it is an array:
        { $unwind: "$itemDetails" },
      
        // Finally, we can prject- only the data we're interested to show the user!
        {
          $project: {
            itemId: "$_id",
            totalQuantity: 1,
            "itemDetails.productName": 1
          }
        }
      ])

    const response = {
        success: true,
        message: `Top 10 recycled items retrieved successfully`,
        status: 200,
        data: result
    }
    return response;
}

const addRecycleTransaction = async (username, recycleItemId, quantity) => {
    
    //console.log(username2, recycleItemId, quantity);
    //const userTransaction = await RecycleTransaction.findOne({ bla: "bla"});

    //console.log(username, recycleItemId, quantity);

    //console.log(userTransaction);
    const userTransaction = await RecycleTransaction.findOne({ username: username});


    // First, we update the userPoints, userLevel and moneySaved fields for the user:
    const user = await User.findOne ({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User with username ${username} not found`,
            status: 404
        }
        return response;
    }

    // We find the recycleItem by its id:
    const recycleItem = await RecycleItem.findOne({ _id: recycleItemId });


    // First, we get all the three leaderboards, BEFORE updating the user's points:
    // Note that we use the 'getUserCityLeaderboard', 'getUserCountryLeaderboard' functions to be consistent with the other services:

    // Getting the user's city leaderboard:
    const userCityLeaderboardBeforeReporting = await getUserCityLeaderboard(username);

    // Getting the user's country leaderboard:
    const userCountryLeaderboardBeforeReporting = await getUserCountryLeaderboard(username);

    
    // Getting all the friends of the user:
    const listOfFriends = user.friends;

    // Defining an array to store the friend's leaderboard before the user's recycling transaction, in length listOfFriends.length:
    let friendLeaderboardBeforeReporting = [];
        

    // Checking whether the user has any friends to notify:
    if (listOfFriends.length !== 0) {
        
        // We want to save the friends leaderboard for each friend of the user, so we can send them notifications if their position has changed:
        for (let i = 0; i < listOfFriends.length; i++) {
            friend = listOfFriends[i];
            friendLeaderboardBeforeReporting[i] = await getUserFriendsLeaderboard(friend);
        }
    }

    // Updating money saved and points ASYNCH


    // If the item has productName of "Paper", we grant the user 0.1 points per every sheet of paper:
    if (recycleItem.productName === "Paper") {
        user.userPoints += quantity * 0.1;
    }

    // Note that this is also (and maybe especially) for items with suitableRecycleBin = "green":
    // If the item has isHadap=1, we DEDUCT 5 points per each such item - this is a penalty for using disposable dishes, which are not recyclable:
    // Note that unlike the previous deduction, this is a real panulty, since the user isn't just getting less points (7 instead of 10), but here he is only getting -5 points, since disposable dishes are related to green recycle bins, so the user won't get 10 points for them, according to the 1st check.
    if (recycleItem.isHadap === 1) {
        user.userPoints -= quantity * 5;
    }


    // We want analyzeLeaderboardsAndNotifyUsers to run ASYNCHRONOUSLY, so we don't wait for it to finish before returning the response to the user:
    // setImmediate is used to run the function after the current event loop, so we can make sure we aren't causing any blocking:
    //setImmediate(() => {
        analyzeLeaderboardsAndNotifyUsers(username, userCityLeaderboardBeforeReporting, userCountryLeaderboardBeforeReporting, friendLeaderboardBeforeReporting);
    //});

    // We update the user's moneySaved and userPoints fields, but we can do this ASYNCHRONOUSLY, since we don't need to wait for the response to be sent to the user to update these fields in the database:
    //setImmediate(() => {
        updateUserMoneySavedAndPoints(user, recycleItem, quantity);
    //});

    // We update the userLevel based on the userPoints:
    if (user.userPoints >= 100) {
        user.userLevel = 1;
    }
    if (user.userPoints >= 200) {
        user.userLevel = 2;
    }
    if (user.userPoints >= 300) {
        user.userLevel = 3;
    }
    if (user.userPoints >= 400) {
        user.userLevel = 4;
    }
    if (user.userPoints >= 500) {
        user.userLevel = 5;
    }

    await user.save();

    
    

    // If the user already has a recycle transaction, we have to UPDATE it rather than create a new one.
    if (userTransaction) {
        //console.log("User already has a recycle transaction");
        //for (const item of items) { // If we want to support multiple (DIFFERENT - not just quantity) items in a single transaction - not needed according to our current plan.
        // Checking whether the item already exists in the user's previous recycle transaction:
        const existingItem = userTransaction.recycleItems.find(
            (item) => item.itemId == recycleItemId
        );

        //console.log("Existing item: ", existingItem);

        // If the item already exists in the user's previous recycle transaction, we'll just update the quantity of that item.
        if (existingItem) {
            //console.log("Item already exists in the user's previous recycle transaction, existingItemQuantity:", existingItem.quantity, "quantityToAdd:", quantity, "newQuantity:", existingItem.quantity + quantity);
            existingItem.quantity += quantity;
        }
        // Otherwise - adding the item to the user's recycle transaction:
        else {
            //console.log("Item doesn't exist in the user's previous recycle transaction, item");
            userTransaction.recycleItems.push({ itemId: recycleItemId, quantity: quantity });
        }
        //}

        await userTransaction.save();
        const response = {
            success: true,
            message: `Recycle transaction for user with username ${username} updated (on top of existing transactions for this user) successfully`,
            status: 200,
            data: userTransaction
        }
        return response;
    }

    // If we reach here, it means that the user doesn't have any recycle transactions yet (not just for this item - but hasn't recycled yet at all).
    // Hence, we'll create a NEW recycle transaction for the user.
    const transaction = new RecycleTransaction({
        username: username,
        recycleItems: [{ itemId: recycleItemId, quantity: quantity }]
    });

    await transaction.save();
    const response = {
        success: true,
        message: `1st Recycle transaction for user with username ${username} created successfully`,
        status: 200,
        data: transaction
    }
    return response;

}

const updateUserMoneySavedAndPoints = async (user, recycleItem, quantity) => {
    // We add 0.3 to the moneySaved if the item currently recycled is with a suitableRecycleBin of "refundable" (i.e. a bottle):
    if (recycleItem.suitableRecycleBin === "refundable") {
        user.moneySaved += quantity * 0.3;
    }


    // We grant the user 10 points for each item recycled, unless the suitableRecycleBin is "green", in which case we grant 0 points - the item cannot be recycled:
    if (recycleItem.suitableRecycleBin !== "green" && recycleItem.productName !== "Paper") {
        user.userPoints += quantity * 10;

        // If the user has reported recycling a carton box (general category), we grant the user 5 extra points per each such item:
        if (recycleItem.suitableRecycleBin === "cardboardRecyclingContainer") {
            user.userPoints += quantity * 5;
        }


        // If the item has notesAndSuggestions of "2.0LiterNote", we grant the user 10 extra points per each such item:
        if (recycleItem.notesAndSuggestions === "2.0LiterNote") {
            user.userPoints += quantity * 10;
        }

        // If the item has notesAndSuggestions of "1.5LiterNote", we grant the user 5 extra points per each such item:
        if (recycleItem.notesAndSuggestions === "1.5LiterNote") {
            user.userPoints += quantity * 5;
        }

        // If the item has notesAndSuggestions of "aluminiumCanNote", we grant the user 5 extra points per each such item:
        if (recycleItem.notesAndSuggestions === "aluminiumCanNote") {
            user.userPoints += quantity * 5;
        }

        // If the item has notesAndSuggestions note starting with "thisIs", we DEDUCT 3 points per each such item - this is a penalty for recycling items that have better alternatives (same product, better container in terms of recycling):
        // Note that we have to check first that notesAndSuggestions is not null, otherwise we get an error when trying to call startsWith on null.
        if (recycleItem.notesAndSuggestions !== null) {
            if (recycleItem.notesAndSuggestions.startsWith("thisIs")) {
                user.userPoints -= quantity * 3;
            }
        }

        // If the item has notesAndSuggestions of "glassNote", we DEDUCT 1 point per each such item - this is a penalty for recycling glass, which is even less environmentally friendly than plastic:
        if (recycleItem.notesAndSuggestions === "glassNote") {
            user.userPoints -= quantity;
        }

        
    }
}


const analyzeLeaderboardsAndNotifyUsers = async (username, userCityLeaderboardBeforeReporting, userCountryLeaderboardBeforeReporting, friendLeaderboardBeforeReporting) => {
    // Now, we have finished updating the user's points, so we can can the updated leaderboards, AFTER updating the user's points:
    // Getting the user's city leaderboard:
    const userCityLeaderboardAfterReporting = await getUserCityLeaderboard(username);

    // Getting the user's country leaderboard:
    const userCountryLeaderboardAfterReporting = await getUserCountryLeaderboard(username);

     // Defining an array to store the friend's leaderboard after the user's recycling transaction, in length listOfFriends.length:
     let friendLeaderboardAfterReporting = [];

    // Getting the listOfFriends of the user:
    const user = await User.findOne({ username: username });
    const listOfFriends = user.friends;


     // We check whether the user has any friends to notify:
     if (listOfFriends.length !== 0) {
        // We want to save the friends leaderboard for each friend of the user, so we can send them notifications if their position has changed:
        for (let i = 0; i < listOfFriends.length; i++) {
            friend = listOfFriends[i];
            friendLeaderboardAfterReporting[i] = await getUserFriendsLeaderboard(friend);
        }
    }

     // Now, we compare friendLeaderboardBeforeReporting to friendLeaderboardAfterReporting, to see if any friend's position has changed in the leaderboard.
        // If so, we want to send a notification to the friend whose position has changed, to notify him about the change:
        // Note: this is the return value of getting a leaderboard: const countryUsers = await User.find().sort({ userPoints: -1 });

        // The first loop is for each friend of the user - we want to compare the two leaderboards of each friend of the user:
        for (let i = 0; i < friendLeaderboardBeforeReporting.length; i++) {
            // The inner loop is for each user in the friend's leaderboard:
            for (let j = 0; j < friendLeaderboardBeforeReporting[i].data.length; j++) {
                if (friendLeaderboardBeforeReporting[i].data[j].username != friendLeaderboardAfterReporting[i].data[j].username) {

                    // The friend whose position has changed is:
                    const friendWhosePositionChanged = friendLeaderboardAfterReporting[i].data[j];

                    // Now, we will send him a notification indicating that his position has changed in the friend's leaderboard:

                    reciever = friendWhosePositionChanged;


                    // Checking whether the friend's position has gotten better or worse:
                    if (friendLeaderboardBeforeReporting[i].data[j].username > friendLeaderboardAfterReporting[i].data[j].username) {
                        title = 'Friend Leaderboard position change - Improvement';
                        content = `Your position in the friend's leaderboard has improved! You are now at position ${j+1}!`;
                    } else {
                        title = 'Friend Leaderboard position change - Deterioration';
                        content = `Your position in the friend's leaderboard has deteriorated! You are now at position ${j+1}!`;    
                    }

                    // We send the notification to the friend whose position has changed:
                    sendNotification(reciever, title, content);


                    /*
                    // Retrieve server key stores in env file:
                    const serverKey = process.env.SERVER_KEY;


                    let notificationReq = {
                        to: reciever.fireBaseToken,
                        notification: {
                            title: title,
                            body: content,
                        }
                    }
                    await fetch('https://fcm.googleapis.com/fcm/send', {
                        method: 'POST',
                        headers: {
                            'Authorization': `key=${serverKey}`,
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(notificationReq),
                    })
                        */
                }
            }
        }


    
        


    // Now, we want to compare the leaderboards before and after the user's recycling transaction, to see if ANY position of any user in any leaderboard has changed.
    // If so, we want to send a notification to the user whose position has changed, to notify him about the change:
    // Note: this is the return value of getting a leaderboard: const countryUsers = await User.find().sort({ userPoints: -1 });

    for (let i = 0; i < userCityLeaderboardBeforeReporting.data.length; i++) {
        if (userCityLeaderboardBeforeReporting.data[i].username != userCityLeaderboardAfterReporting.data[i].username) {
            
            // The user whose position has changed is:
            const userWhosePositionChanged = userCityLeaderboardAfterReporting.data[i];

            // Now, we will send him a notification indicating that his position has changed in city leaderboard:
            
            reciever = userWhosePositionChanged;
            
            // Checking whether the user's position has gotten better or worse:
            if (userCityLeaderboardBeforeReporting.data[i].username > userCityLeaderboardAfterReporting.data[i].username) {
                title = 'City Leaderboard position change - Improvement';
                content = `Your position in the city leaderboard has improved! You are now at position ${i+1}!`;
            } else {
                title = 'City Leaderboard position change - Deterioration';
                content = `Your position in the city leaderboard has deteriorated! You are now at position ${i+1}!`;
            }

            // We send the notification to the user whose position has changed:
            sendNotification(reciever, title, content);


            /*
            // Retrieve server key stores in env file:
            const serverKey = process.env.SERVER_KEY;


            let notificationReq = {
                to: reciever.fireBaseToken,
                notification: {
                    title: title,
                    body: content,
                }
            }
            await fetch('https://fcm.googleapis.com/fcm/send', {
                method: 'POST',
                headers: {
                    'Authorization': `key=${serverKey}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(notificationReq),
            })
                */
        }
    }

    // Doing the same for the country leaderboard:
    for (let i = 0; i < userCountryLeaderboardBeforeReporting.data.length; i++) {
        if (userCountryLeaderboardBeforeReporting.data[i].username != userCountryLeaderboardAfterReporting.data[i].username) {
            
            // The user whose position has changed is:
            const userWhosePositionChanged = userCountryLeaderboardAfterReporting.data[i];

            // Now, we will send him a notification indicating that his position has changed in the country leaderboard:
            
            reciever = userWhosePositionChanged;
            
            // Checking whether the user's position has gotten better or worse:
            if (userCountryLeaderboardBeforeReporting.data[i].username > userCountryLeaderboardAfterReporting.data[i].username) {
                title = 'Country Leaderboard position change - Improvement';
                content = `Your position in the country leaderboard has improved! You are now at position ${i+1}!`;
            } else {
                title = 'Country Leaderboard position change - Deterioration';
                content = `Your position in the country leaderboard has deteriorated! You are now at position ${i+1}!`;
            }

            // We send the notification to the user whose position has changed:
            sendNotification(reciever, title, content);

            /*
            // Retrieve server key stores in env file:
            const serverKey = process.env.SERVER_KEY;

            let notificationReq = {
                to: reciever.fireBaseToken,
                notification: {
                    title: title,
                    body: content,
                }
            }
            await fetch('https://fcm.googleapis.com/fcm/send', {
                method: 'POST',
                headers: {
                    'Authorization': `key=${serverKey}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(notificationReq),
            })
                */
        }
    }
}


const getUserRecycleTransactions = async (username) => {
    const userTransaction = await RecycleTransaction.findOne({username: username}).populate('recycleItems.itemId');

    if (userTransaction == null) {
        const response = {
            success: false,
            message: `Recycle transactions for user ${username} not found`,
            status: 404
        }
        return response;
    }

    const response = {
        success: true,
        message: `Recycle transactions for user ${username} retrieved successfully`,
        status: 200,
        data: userTransaction
    }
    return response;
}

const getDateOfLastTransaction = async (username) => {
    const userTransaction = await RecycleTransaction.findOne({username: username}).populate('recycleItems.itemId');

    if (userTransaction == null) {
        const response = {
            success: false,
            message: `Recycle transactions for user ${username} not found`,
            status: 404
        }
        return response;
    }

    const lastTransactionInUTCTime = userTransaction.updatedAt;

    // We have to convert the UTC time to UTC+3 time:
    // Note that Istanbul time is UTC+3, like Israel.
    const lastTransactionInUTCPlus3Time = moment.utc(lastTransactionInUTCTime).tz('Europe/Istanbul');

    const response = {
        success: true,
        message: `Date of last recycle transaction for user ${username} retrieved successfully`,
        status: 200,
        data: lastTransactionInUTCPlus3Time
    }
    return response;
}

module.exports = { addRecycleTransaction, getUserRecycleTransactions, getDateOfLastTransaction,getTop10RecycledItems };