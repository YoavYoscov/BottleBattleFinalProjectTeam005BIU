
const leaderboardService = require('../services/leaderboard');

const getUserCityLeaderboard = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await leaderboardService.getUserCityLeaderboard(username);

    return res.status(serviceResponse.status).json(serviceResponse);
}

const getUserCountryLeaderboard = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await leaderboardService.getUserCountryLeaderboard(username);
    return res.status(serviceResponse.status).json(serviceResponse);
}

const getTop10UsersByScore = async (req, res) => {
    const serviceResponse = await leaderboardService.getTop10UsersByScore();
    return res.status(serviceResponse.status).json(serviceResponse);
}

const getUserFriendsLeaderboard = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await leaderboardService.getUserFriendsLeaderboard(username);
    return res.status(serviceResponse.status).json(serviceResponse);
}

const getUserPositionCity = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await leaderboardService.getUserPositionCity(username);
    return res.status(serviceResponse.status).json(serviceResponse);

}

const getUserPositionCountry = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await leaderboardService.getUserPositionCountry(username);
    return res.status(serviceResponse.status).json(serviceResponse);

}

const getUserPositionFriends = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await leaderboardService.getUserPositionFriends(username);
    return res.status(serviceResponse.status).json(serviceResponse);
}


/*
const getUserByUsername = async (req, res) => {
    const user = await userService.getUserByUsername(req.params.username)

    if(user == null){
        return res.status(404).json({message: "User not found"});
    }
    return res.status(200).json(user);
};*/

module.exports = { getUserCityLeaderboard, getUserCountryLeaderboard, getUserFriendsLeaderboard, getUserPositionCity, getUserPositionCountry, getUserPositionFriends,getTop10UsersByScore };