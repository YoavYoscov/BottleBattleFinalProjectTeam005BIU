const User = require('../models/user');
const crypto = require('crypto');

const sendNotification = require('./notificationService');
const recycleTransaction = require('../models/recycleTransaction');



const updateUserCity = async (username,city)  =>{
    try {
    const user = await User .findOneAndUpdate(
        { username: username }, 
        { city: city }, { new: true });
        return {
            success: true,
            data: user,
            message: `User ${username} updated successfully`,
            status: 200,
        }
    } catch (error) {
        return {
            success: false,
            message: `User ${username} couldn't be updated`,
            status: 404,
        }
    }
}

const updateUserProfilePic = async (username,profilePic)  =>{
    try {
        const user = await User .findOneAndUpdate(
            { username: username },
            { profilePic: profilePic }, { new: true });
        return {
            success: true,
            data: user,
            message: `User ${username} updated successfully`,
            status: 200,
        }
    } catch (error) {
        return {
            success: false,
            message: `User ${username} couldn't be updated`,
            status: 404,
        }
    }
}

// Note that we don't get userLevel, userPoints and moneySaved from the request body, since when creating a new user, these values should be set to 0.
const createUser = async (username, password, profilePic, fireBaseToken, city, email) => {

    // Although we already check that the username is available in checkAvailableUsername, we should also check here, in case the client doesn't check availability before calling this function (safety measure).
    const userExist = await User.findOne({ username: username });
    if (userExist != null) {
        const response = {
            success: false,
            message: `Username ${username} already exists`,
            status: 409
        }
        return response;
    }
    const user = new User({ username: username, password: password, profilePic: profilePic, fireBaseToken : fireBaseToken, userLevel: 0, userPoints: 0, city: city, friends: [], moneySaved: 0,email});
 
    const userRes = await user.save();
    const response = {
        success: true,
        message: `User ${username} created successfully`,
        status: 200,
        data: {
            "username": userRes.username,
            "profilePic": userRes.profilePic,
            "fireBaseToken" : fireBaseToken,
            "userLevel": userRes.userLevel,
            "userPoints": userRes.userPoints,
            "city": userRes.city,
            "friends": userRes.friends,
            "moneySaved": userRes.moneySaved,
            "email": email
        }
    }
    return response;
}

const addFriendByUsername = async (username, friendUsername) => {

    const userExist = await User.findOne({ username: username });
    if (userExist == null) {
        const response = {
            success: false,
            message: `User with username ${username} doesn't exist`,
            status: 404
        }
        return response;
    }

    const friendExist = await User.findOne({ username: friendUsername });
    if (friendExist == null) {
        const response = {
            success: false,
            message: `Friend with username ${friendUsername} doesn't exist`,
            status: 404
        }
        return response;
    }

    // Check if the friend is already in the user's friends list
    if (userExist.friends.includes(friendExist.username)) {
        const response = {
            success: false,
            message: `Friend with username ${friendUsername} already exists as a friend of user with username ${username}`,
            status: 409
        }
        return response;
    }

    const user = await User.findOneAndUpdate({ username : username }, { $push: { friends: friendUsername } }, { new: true });
    if (user == null) {
        const response = {
            success: false,
            message: `Friend with username ${friendUsername} couldn't be added to user with username ${username}`,
            status: 404
        }
        return response;
    }
    
    const response = {
        success: true,
        message: `Friend with username ${friendUsername} added to user with username ${username} successfully`,
        status: 200,
        data: {
            "username": user.username,
            "profilePic": user.profilePic,
            "fireBaseToken" : user.fireBaseToken,
            "userLevel": user.userLevel,
            "userPoints": user.userPoints,
            "city": user.city,
            "friends": user.friends,
            "moneySaved": user.moneySaved,
            "email": user.email
        }
    }


    //send a push notification to the user who was added as a friend:
    reciever = await User.findOne({ username: friendUsername });
    sender = await User.findOne({ username : username });
    

    const content = `${sender.username} added you as a friend!`;
    const title = 'Bottle Battle: New Friend!';

    // Send a push notification to the user who was added as a friend:
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




    return response;
}

const loginAdmin = async (username, password) => {
    // We just check if username == 'admin' and that THE HASH of the password == 'menahalimYoavAmir1' (for simplicity)
    // In a real-world scenario, we would use a more secure method to authenticate the admin.
    if (username != 'admin') {
        const response = {
            success: false,
            message: `This user isn't an admin`,
            status: 401
        }
        //console.log("This user isn't an admin");
        return response;
    }
    // Now we check the password:
    const hash = crypto.createHash('sha256');
    // Now, we hash the password:
    const hashedPassword = hash.update(password).digest('hex');
    //Now, we compare the hashed password to the hashed password of the admin:
    // Note that the hashed password of the admin is 'menahalimYoavAmir1' (for simplicity)
    const hashedPasswordAdmin = '5ea8698491ed37d0041294c4b38304a45a1ad7a965b93bce8d082d4d84a7c890'
    if (hashedPassword != hashedPasswordAdmin) {
        const response = {
            success: false,
            message: `Wrong password`,
            status: 401
        }
        //console.log("Wrong password");
        return response;
    }
    const response = {
        success: true,
        message: `Admin logged in successfully`,
        status: 200
    }
    //console.log("Admin logged in successfully");
    return response;
}


const getAllUsers = async () => {
    try {
        const users = await User.find();
        const response = {
            success: true,
            message: `Users retrieved successfully`,
            status: 200,
            data: users
        }
        return response;
    } catch (error) {
        const response = {
            success: false,
            message: `Users couldn't be retrieved`,
            status: 404
        }
        return response;
    }
}



const checkAvailableUsername = async (username) => {
    const userExist = await User.findOne({ username: username });

    if (userExist != null) {
        const response = {
            success: false,
            message: `Username ${username} already exists`,
            status: 409
        }
        return response;
    }
    // If we reach here, it means that the username is available:
    const response = {
        success: true,
        message: `Username ${username} is available`,
        status: 200,
        data: {
            "username": username,
            "message": `Username ${username} is available`,
        }
    }
    return response;
}


const getUserByUsername = async (username) => {
    try {
        const user = await User.findOne({ username: username });
        if (user == null) {
            const response = {
                success: false,
                message: `User ${username} not found`,
                status: 404
            }
            return response;
        }

        const response = {
            success: true,
            message: `User ${username} retrieved successfully`,
            status: 200,
            data: {
                "username": user.username,
                "profilePic": user.profilePic,
                "fireBaseToken" : user.fireBaseToken,
                "userLevel": user.userLevel,
                "userPoints": user.userPoints,
                "city": user.city,
                "friends": user.friends,
                "moneySaved": user.moneySaved,
                "email": user.email
            }
        }
        return response;
    } catch (error) {
        const response = {
            success: false,
            message: `User ${username} couldn't be retrieved`,
            status: 404
        }
        return response;
    }
};


const forgotPassword = async (username) => {
    const user = await User .findOne({ username: username });
    if (user == null) {
        const response = {
            success: false,
            message: `User ${username} not found`,
            status: 404
        }
        return response;
    }
    // Generate a token:
    // Option 1 - redirecting user to a webpage:
    // const token = crypto.randomBytes(20).toString('hex');

    // Option 2 - generating  token (that will be sent to the user by email) that will be used inside the app:
    // It has to be logical to type by the user, so we generated a 6 digit token (3 bytes, hex):
    const token = crypto.randomBytes(3).toString('hex');

    // Set the token expiration date:
    user.resetPasswordToken = token;

    // Set the token expiration date:
    user.resetPasswordExpires = Date.now() + 3600000; // 1 hour

    // Save the user:
    await user.save();
    const nodemailer = require('nodemailer')
    // Send an email to the user with the token:
    const transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: 'bottlebattleproj',
            pass: 'emgnwtncdcjtmsau'
        }
    });

    const mailOptions = {
        from: 'bottlebattleproj@gmail.com',
        to: user.email,
        subject: 'Bottle Battle: Password Reset Request',
        text: 
            // New version - sending a token that should be inserted inside the app by the user:
            `Hi ${user.username}!\n\n` +
            `This email was sent in accordance with a password-reset request submitted regarding your Bottle Battle account.\n` +
            `If you haven't requested to reset your password, please ignore this email (and your account will be unaffected).\n` +
            `If you have requested to reset your password, please use the following token to choose a new password for your account (provide this token inside the BottleBattle app, in the "change password" screen):\n\n` +
            `Token: ` +
            token + 
            `\n\nPlease note that you have to complete the process within one hour (afterwards the generated token will expire, but you will able to generate a new one by submitting a new password-reset request).\n\n` +
            `Happy recycling,\n`+
            `Bottle Battle Team\n`

            // Old version - redirecting to a webpage:
            /*
            `Hi ${user.username}!\n\n` +
            `This email was sent in accordance with a password-reset request submitted regarding your Bottle Battle account.\n` +
            `If you haven't requested to reset your password, please ignore this email (and your account will be unaffected).\n` +
            `If you have requested to reset your password, please click on the following link (or paste this link into your browser) to choose a new password for your account:\n\n` +
            `http://localhost:3000/ResetPassword/` +
            token + 
            `\n\nPlease note that you have to complete the process within one hour (afterwards the generated token will expire, but you will able to generate a new one by submitting a new password-reset request).\n\n` +
            `Happy recycling,\n`+
            `Bottle Battle Team\n`
            */
    };

    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            console.log(error);
            const response = {
                success: false,
                message: `Email couldn't be sent`,
                status: 404
            }
            return response;
        }
        console.log('Email sent: ' + info.response);
        const response = {
            success: true,
            message: `Email sent successfully`,
            status: 200
        }
        return response;
    });

    const response = {
        success: true,
        message: `Token sent successfully`,
        status: 200
    }
    return response;

}

// Note the token verification:
const resetPassword = async (username, token, newPassword) => {
    const user = await User.findOne({ username: username, resetPasswordToken: token, resetPasswordExpires: { $gt: Date.now() } });
    if (user == null) {
        const response = {
            success: false,
            message: `Token is invalid or expired`,
            status: 404
        }
        return response;
    }

    user.password = newPassword; 
    user.resetPasswordToken = undefined;
    user.resetPasswordExpires = undefined;

    await user.save();

    const response = {
        success: true,
        message: `Password reset successfully`,
        status: 200
    }
    return response;
}



const deleteUserByUsername = async (username) => {
    try {
        const user = await User.findOneAndDelete({ username: username });
        if (user == null) {
            const response = {
                success: false,
                message: `User with username: ${username} hasn't been found`,
                status: 404
            }
            return response;
        }
        //await recycleTransaction.deleteMany({username: username});
        const response = {
            success: true,
            message: `User with username: ${username} deleted successfully`,
            status: 200
        }
        return response;
    }
    catch (error) {
        console.log(error);

        const response = {
            success: false,
            message: `User with username: ${username} couldn't be deleted`,
            status: 404
        }
        return response;
    }
}


module.exports = { createUser, updateUserCity, getUserByUsername, addFriendByUsername, checkAvailableUsername,
    forgotPassword, resetPassword, deleteUserByUsername, updateUserProfilePic, loginAdmin, getAllUsers };