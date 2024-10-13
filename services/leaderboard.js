const User = require('../models/user');

const getUserCityLeaderboard = async (username) => {
    // If the username includes the email, we need to extract the username:
    if (username.includes('@')) {
        username = username.split('@')[0];
    }
    
    const user = await User.findOne({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        }
        return response;
    }
    // Getting the user's city:
    const userCity = user.city;

    // Getting all users from the same city:
    const cityUsers = await User.find({ city: userCity }).sort({ userPoints: -1 });

    // Returning the sorted list of users = the user's city leaderboard:
    const response = {
        success: true,
        message: `Leaderboard for ${userCity}, city of ${user}, retrieved successfully`,
        status: 200,
        data: cityUsers
    }

    //console.log(response);

    return response;
}

const getUserCountryLeaderboard = async (username) => {
    // If the username includes the email, we need to extract the username:
    if (username.includes('@')) {
        username = username.split('@')[0];
    }

    const user = await User.findOne({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        }
        return response;
    }
   
    // Getting all users in the DB:
    const countryUsers = await User.find().sort({ userPoints: -1 });

    // Returning the sorted list of users = the user's country leaderboard (all users):
    const response = {
        success: true,
        message: `Leaderboard for ${user} country (all users), retrieved successfully`,
        status: 200,
        data: countryUsers
    }

    return response;
}



const getUserFriendsLeaderboard = async (username) => {
    // If the username includes the email, we need to extract the username:
    if (username.includes('@')) {
        username = username.split('@')[0];
    }

    const user = await User.findOne({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        }
        return response;
    }
    // Getting the user's friends:
    const userFriends = user.friends;

    // We might want to add .limit(3) to get only the top 3 friends, we might change this later.
    // Note that we also include the user itself in the leaderboard:
    const friendUsers = await User.find({ username: { $in: [...userFriends, username] } }).sort({ userPoints: -1 });

    // Returning the sorted list of users = the user's friend leaderboard:
    const response = {
        success: true,
        message: `Leaderboard among friends of ${user}, retrieved successfully`,
        status: 200,
        data: friendUsers
    }

    return response;
}

const getUserPositionCity = async (username) => {
    console.log('username:', username);
    // If the username includes the email, we need to extract the username:
    if (username.includes('@')) {
        username = username.split('@')[0];
    }
    console.log('username:', username);

    const user = await User.findOne({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        }
        return response;
    }
    
    // Getting the leaderboard for the user's city:
    var userCityLeaderboard = await getUserCityLeaderboard(username);
    // now, we only want the array of field 'data':
    //console.log(userCityLeaderboard);
    userCityLeaderboard = userCityLeaderboard.data;

    // The line above gets undefined, so we fix it below:
    //const userCityLeaderboard = await User.find({ city: user.city }).sort({ userPoints: -1 });

    // Getting the user's position in the city leaderboard:
    var userPosition = -1;

    for (let i = 0; i < userCityLeaderboard.length; i++) {
        if (userCityLeaderboard[i].username === username) {
            userPosition = i;
            break;
        }
    }

    // Returning the user's position in the city leaderboard:
    const response = {
        success: true,
        message: `Position of ${user} in the city leaderboard, retrieved successfully`,
        status: 200,
        data: userPosition
    }


    return response;
}

const getUserPositionCountry = async (username) => {
    console.log('username:', username);
    // If the username includes the email, we need to extract the username:
    if (username.includes('@')) {
        username = username.split('@')[0];
    }
    console.log('username:', username);

    const user = await User.findOne({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        }
        return response;
    }
    
    // Getting the leaderboard for the user's country:
    var userCountryLeaderboard = await getUserCountryLeaderboard(username);

    // now, we only want the array of field 'data':
    userCountryLeaderboard = userCountryLeaderboard.data;


    // Getting the user's position in the country leaderboard:
    var userPosition = -1;

    for (let i = 0; i < userCountryLeaderboard.length; i++) {
        if (userCountryLeaderboard[i].username === username) {
            userPosition = i;
            break;
        }
    }

    // Returning the user's position in the country leaderboard:
    const response = {
        success: true,
        message: `Position of ${user} in the country leaderboard, retrieved successfully`,
        status: 200,
        data: userPosition
    }


    return response;
}

async function getTop10UsersByScore() {
    const top = await User.find().sort({userPoints: -1}).limit(10);
    const response = {
        success: true,
        message: `Top 10 users retrieved successfully`,
        status: 200,
        data: top
    }
    return response;
}
const getUserPositionFriends = async (username) => {
    console.log('username:', username);
    // If the username includes the email, we need to extract the username:
    if (username.includes('@')) {
        username = username.split('@')[0];
    }
    console.log('username:', username);

    const user = await User.findOne({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        }
        return response;
    }

    // Getting the leaderboard for the user's friends:
    var userFriendsLeaderboard = await getUserFriendsLeaderboard(username);

    // now, we only want the array of field 'data':
    userFriendsLeaderboard = userFriendsLeaderboard.data;
    
    // Getting the user's position among friends:
    var userPosition = -1;
    // We will iterate over all the friends list, and when we find a friend with number of points greater than the user, we know it is the user's position:
    for (let i = 0; i < userFriendsLeaderboard.length; i++) {
        
        if (userFriendsLeaderboard[i].userPoints <= user.userPoints) {
            userPosition = i;
            break;
        }
    }

    // Returning the sorted list of users = the user's friend leaderboard:
    const response = {
        success: true,
        message: `Position of ${user} among friends, retrieved successfully`,
        status: 200,
        data: userPosition
    }

    return response;
}


module.exports = { getUserCityLeaderboard, getUserCountryLeaderboard, getUserFriendsLeaderboard, getUserPositionCity, getUserPositionCountry, getUserPositionFriends,getTop10UsersByScore};
