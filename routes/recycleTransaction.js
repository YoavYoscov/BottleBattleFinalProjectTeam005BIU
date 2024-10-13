const recycleTransactionController = require('../controllers/recycleTransaction');
const verify = require('../jwt/verify');

const express = require('express');
var router = express.Router();

// POST DOMAIN/api/RecycleTransactions/
router.route('/')
    .post(verify.isLoggedIn,recycleTransactionController.addRecycleTransaction)
// GET DOMAIN/api/RecycleTransactions/GetTop10RecycledItems
router.route('/GetTop10RecycledItems')
    .get(verify.isLoggedIn,recycleTransactionController.getTop10RecycledItems)
// GET DOMAIN/api/RecycleTransactions/:username
router.route('/:username')
    .get(verify.isLoggedIn,recycleTransactionController.getUserRecycleTransactions)



// GET DOMAIN/api/RecycleTransactions/GetDateOfLastTransaction/:username
router.route('/GetDateOfLastTransaction/:username')
    .get(verify.isLoggedIn,recycleTransactionController.getDateOfLastTransaction)

module.exports = router;