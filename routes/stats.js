const statsController = require('../controllers/stats');
const verify = require('../jwt/verify');

const express = require('express');
var router = express.Router();


// GET DOMAIN/api/Stats/PlasticSaved/:username
router.route('/PlasticSaved/:username')
    .get(verify.isLoggedIn,statsController.getTotalPlasticSavedForUser)

// GET DOMAIN/api/Stats/MoneySaved/:username
router.route('/MoneySaved/:username')
    .get(verify.isLoggedIn,statsController.getTotalMoneySavedForUser)

// GET DOMAIN/api/Stats/UserPoints/:username
router.route('/UserPoints/:username')
    .get(verify.isLoggedIn,statsController.getUserPoints)

// GET DOMAIN/api/Stats/UserLevel/:username
router.route('/UserLevel/:username')
    .get(verify.isLoggedIn,statsController.getUserLevel)


module.exports = router;