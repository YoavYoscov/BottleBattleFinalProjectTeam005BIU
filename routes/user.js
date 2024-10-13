const userController = require('../controllers/user');
const verify = require('../jwt/verify');

const express = require('express');
var router = express.Router();

// POST DOMAIN/api/Users
router.route('/')
    .post(userController.createUser);

// POST DOMAIN/api/Users/LoginAdmin
router.route('/LoginAdmin')
    .post(userController.loginAdmin)


// POST DOMAIN/api/Users/AddFriend
router.route('/AddFriend')
    .post(verify.isLoggedIn,userController.addFriendByUsername)

// GET DOMAIN/api/Users/CheckAvailableUsername/:username
router.route('/CheckAvailableUsername/:username')
    .get(userController.checkAvailableUsername) // there is no need to check if the user is logged in to check if a username is available

// GET DOMAIN/api/Users/ForgotPassword/:username
router.route('/ForgotPassword/:username')
    .get(userController.forgotPassword)

// POST DOMAIN/api/Users/ResetPassword
router.route('/ResetPassword')
    .post(userController.resetPassword)

// GET DOMAIN/api/Users/:username
router.route('/:username')
    .get(verify.isLoggedIn, userController.getUserByUsername)

// DELETE DOMAIN/api/Users/DeleteUser/:username
router.route('/DeleteUser/:username')
    .delete(verify.isLoggedIn, userController.deleteUserByUsername)

// PUT DOMAIN/api/Users/City/:username
router.route('/City/:username')
    .put(verify.isLoggedIn, userController.updateUserCity)

// PUT DOMAIN/api/Users/ProfilePic/:username
router.route('/ProfilePic/:username')
    .put(verify.isLoggedIn, userController.updateUserProfilePic)

// GET DOMAIN/api/Users/GetAllUsers
router.route('/GetAllUsers/GetAllUsers')
    .get(userController.getAllUsers)

module.exports = router;