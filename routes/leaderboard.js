const leaderboardController = require('../controllers/leaderboard');
const verify = require('../jwt/verify');

const express = require('express');
var router = express.Router();

// GET DOMAIN/api/Leaderboard/yourCity/:username
router.route('/yourCity/:username')
    .get(verify.isLoggedIn,leaderboardController.getUserCityLeaderboard)

// GET DOMAIN/api/Leaderboard/yourCountry/:username
router.route('/yourCountry/:username')
    .get(verify.isLoggedIn,leaderboardController.getUserCountryLeaderboard)

// GET DOMAIN/api/Leaderboard/top10/
router.route('/top10')
    .get(verify.isLoggedIn,leaderboardController.getTop10UsersByScore)

// GET DOMAIN/api/Leaderboard/yourFriends/:username
router.route('/yourFriends/:username')
    .get(verify.isLoggedIn,leaderboardController.getUserFriendsLeaderboard)

router.route('/getUserPosition/yourCity/:username')
    .get(verify.isLoggedIn,leaderboardController.getUserPositionCity)

router.route('/getUserPosition/yourCountry/:username')
    .get(verify.isLoggedIn,leaderboardController.getUserPositionCountry)

router.route('/getUserPosition/yourFriends/:username')
    .get(verify.isLoggedIn,leaderboardController.getUserPositionFriends)

module.exports = router;