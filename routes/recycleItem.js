const recycleItemController = require('../controllers/recycleItem');
const verify = require('../jwt/verify');

const express = require('express');
var router = express.Router();


// GET DOMAIN/api/RecycleItems/
router.route('/')
    .get(verify.isLoggedIn,recycleItemController.getAllRecycleItems)

// GET DOMAIN/api/RecycleItems/:id
router.route('/:id')
    .get(verify.isLoggedIn,recycleItemController.getRecycleItemById)

// GET DOMAIN/api/RecycleItems/IsHadap/:id
router.route('/IsHadap/:id')
    .get(verify.isLoggedIn,recycleItemController.isHadap)


// GET DOMAIN/api/RecycleItems/GetSuitableRecycleBin/:id
router.route('/GetSuitableRecycleBin/:id')
    .get(verify.isLoggedIn,recycleItemController.getSuitableRecycleBin)

// GET DOMAIN/api/RecycleItems/GetNotesAndSuggestions/:objectId
router.route('/GetNotesAndSuggestions/:id')
    .get(verify.isLoggedIn,recycleItemController.getNotesAndSuggestions)

// GET DOMAIN/api/RecycleItems/GetBetterAlternativeIfSuchExists/:id
router.route('/GetBetterAlternativeIfSuchExists/:id')
    .get(verify.isLoggedIn,recycleItemController.getBetterAlternativeIfSuchExists)

// GET DOMAIN/api/RecycleItems/GetItemFullNameWithManufacturerBrand/:id
router.route('/GetItemFullNameWithManufacturerBrand/:id')
    .get(verify.isLoggedIn,recycleItemController.getItemFullNameWithManufacturerBrand)

// The 2 below might be needed, in our current plan they're not needed.
/*
// GET DOMAIN/api/RecycleItems/:id
router.route('/:id')
    .get(verify.isLoggedIn,recycleItemController.getRecycleItemById)

    
// POST DOMAIN/api/RecycleItems/
router.route('/')
    .post(verify.isLoggedIn,recycleItemController.createRecycleItem)
*/


module.exports = router;