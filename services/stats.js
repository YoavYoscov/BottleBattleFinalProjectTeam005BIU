const User = require('../models/user');
const RecycleTransaction = require('../models/recycleTransaction');
const RecycleItem = require('../models/recycleItem');

const getTotalPlasticSavedForUser = async (username) => {
    const userTransaction = await RecycleTransaction.findOne({username: username}).populate('recycleItems._id');

    if (userTransaction == null) {
        const response = {
            success: true,
            message: `Recycle transactions for user ${username} not found - hasn't recycled yet at all`,
            status: 200,
            data: 0
        }
        return response;
    }


    const promises = userTransaction.recycleItems.map(async (current) => {
        const item = await RecycleItem.findById(current.itemId);
        if (item.suitableRecycleBin === "refundable" && item.notesAndSuggestions !== "glassNote" && item.notesAndSuggestions !== "aluminiumCanNote") {
            //console.log("going to return:", current.quantity * 0.1);
            return current.quantity * 0.1;
        }
        // Otherwise, the item doesn't save any plastic:
        return 0;
    });
    
    // Waiting for resolving of all promises:
    const plasticSavedArray = await Promise.all(promises);
    


    // Summing all the values in plasticSavedArray (this is the total plastic saved):
    let totalPlasticSaved = 0;
    for (let i = 0; i < plasticSavedArray.length; i++) {
        totalPlasticSaved += plasticSavedArray[i];
    }

    // NOTE: the toFixed(1) - this rounds the number to 1 decimal places, we have to use this since otherwise we get for example 0.3000000000000004 instead of 0.3,
    // this stems from floating point arithmetic representation in JS.
    totalPlasticSaved = totalPlasticSaved.toFixed(1);

    
    const response = {
        success: true,
        message: `Total plastic saved for user ${username} retrieved successfully`,
        status: 200,
        data: totalPlasticSaved
    };
    
    return response;
}


const getTotalMoneySavedForUser = async (username) => {
    // Actually, this data is saved for each user in the DB, so we just have to retrieve it:
    const userData = await User.findOne({ username: username });
    if (userData == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        };
        return response;
    }

    const totalMoneySaved = userData.moneySaved;

    const response = {
        success: true,
        message: `Total money saved for user ${username} retrieved successfully`,
        status: 200,
        data: totalMoneySaved
    };
    return response;
}


const getUserPoints = async (username) => {
    // Actually, this data is saved for each user in the DB, so we just have to retrieve it:
    const userData = await User.findOne({ username: username });
    if (userData == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        };
        return response;
    }

    const userPoints = userData.userPoints;

    const response = {
        success: true,
        message: `User points for user ${username} retrieved successfully`,
        status: 200,
        data: userPoints
    };
    return response;
}


const getUserLevel = async (username) => {
    // Actually, this data is saved for each user in the DB, so we just have to retrieve it:
    const userData = await User.findOne({ username: username });
    if (userData == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        };
        return response;
    }


    var userLevel = userData.userLevel;
    
    switch (userLevel) {
        case 1:
            userLevel = "Novice Recycler";
            break;
        case 2:
            userLevel = "Intermediate Recycler";
            break;
        case 3:
            userLevel = "Expert Recycler";
            break;
        case 4:
            userLevel = "Recycling Wizard";
            break;
        case 5:
            userLevel = "Recycling Master";
            break;
        default:
            // Shouldn't happen, but just in case, we'll return "Beginner" as the user level:
            userLevel = "Beginner";
            break;
    }


    const response = {
        success: true,
        message: `User level for user ${username} retrieved successfully`,
        status: 200,
        data: userLevel
    };
    return response;
}



module.exports = { getTotalPlasticSavedForUser, getTotalMoneySavedForUser, getUserPoints, getUserLevel };