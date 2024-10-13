
const userService = require('../services/user');

const createUser = async (req, res) => {
    const {username, password, profilePic, fireBaseToken, city, email} = req.body;
    const serviceResponse = await userService.createUser(username, password, profilePic, fireBaseToken, city, email);

 return res.status(serviceResponse.status).json(serviceResponse);
};

const getUserByUsername = async (req, res) => {
    const serviceResponse = await userService.getUserByUsername(req.params.username)
    return res.status(serviceResponse.status).json(serviceResponse);
};

const loginAdmin = async (req, res) => {
    const {username, password} = req.body;
    const serviceResponse = await userService.loginAdmin(username, password);

 return res.status(serviceResponse.status).json(serviceResponse);
}


const addFriendByUsername = async (req, res) => {
    const { username, friendUsername } = req.body;
    const serviceResponse = await userService.addFriendByUsername(username, friendUsername);

 return res.status(serviceResponse.status).json(serviceResponse);
};

const checkAvailableUsername = async (req,res) => {
    const username = req.params.username;
    const serviceResponse = await userService.checkAvailableUsername(username);

 return res.status(serviceResponse.status).json(serviceResponse);
}

const forgotPassword = async (req, res) => {
    const serviceResponse = await userService.forgotPassword(req.params.username);

 return res.status(serviceResponse.status).json(serviceResponse);
};


const resetPassword = async (req, res) => {
    const { username, token, newPassword } = req.body;
    const serviceResponse = await userService.resetPassword(username, token, newPassword);

 return res.status(serviceResponse.status).json(serviceResponse);
}

const deleteUserByUsername = async (req, res) => {
    const serviceResponse = await userService.deleteUserByUsername(req.params.username)
    return res.status(serviceResponse.status).json(serviceResponse);
}

const updateUserCity = async (req, res) => {
    const username = req.params.username;
    const {city} = req.body;
    const serviceResponse = await userService.updateUserCity(username, city);
    return res.status(serviceResponse.status).json(serviceResponse);
};

const updateUserProfilePic = async (req, res) => {
    const username = req.params.username;
    const {profilePic} = req.body;
    const serviceResponse = await userService.updateUserProfilePic(username, profilePic);
    return res.status(serviceResponse.status).json(serviceResponse);
};

const getAllUsers = async (req, res) => {
    //console.log("here");
    const serviceResponse = await userService.getAllUsers();
    return res.status(serviceResponse.status).json(serviceResponse);
}


module.exports = { createUser, getUserByUsername, addFriendByUsername, checkAvailableUsername, forgotPassword, resetPassword,
    deleteUserByUsername, updateUserCity, updateUserProfilePic, loginAdmin, getAllUsers };