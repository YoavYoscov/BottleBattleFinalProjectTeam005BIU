const {OAuth2Client} = require('google-auth-library');
const userService = require('../services/user');
const jwt = require("jsonwebtoken")
const key = "Some super secret key shhhhhhhhhhhhhhhhh!!!!!"

const sendNotification = require('./notificationService');

const User = require('../models/user');

const googleSignInOptions = {
    clientId: process.env.GOOGLE_CLIENT_ID,
    clientSecret: process.env.GOOGLE_CLIENT_SECRET,
    // We are going to use this to redirect to 'save user after login' page:
    redirectUri: process.env.GOOGLE_REDIRECT_URI
}
const client = new OAuth2Client(googleSignInOptions);


//const googleSignIn = async (token) => {
const googleSignIn = async (token, req) => {
    try {
        const ticket = await client.verifyIdToken({
            idToken: token,
            audience: googleSignInOptions.clientId
        });
        const payload = ticket.getPayload();
        //const { email : username, name, sub }  = payload;
        const { email , name, sub }  = payload;

        // The username should be the email before the dot (for example, adam.adam@gmail.com should be adam.adam):
        const username = email.split('@')[0];

        // insert user in db if not exists
        let userResponse = await userService.getUserByUsername(username);
        const { profilePic, fireBaseToken, city } = req.body;
        if (userResponse.success == false || userResponse.status == 404) {
            //userResponse = await userService.createUser(username, null, profilePic, fireBaseToken, city, email);
            userResponse = await userService.createUser(username, null, profilePic, fireBaseToken, city, email);
        } else {
            //save the user's fireBaseToken in the database in order to send notifications later
            await User.updateOne({ username : username }, {$set:{ fireBaseToken : fireBaseToken}});
        }
        
        //send a push notification to the user who was added as a friend:
        reciever = await User.findOne({ username: username });
        

        // Send a push notification to the user who was added as a friend:
        sendNotification(reciever, 'Happy Recycling!', 'You have logged in to Bottle Battle successfully');


        /*
        // Retrieve server key stores in env file:
        const serverKey = process.env.SERVER_KEY;

        // Log we're here:
        console.log('Sending notification to: ', reciever.fireBaseToken, 'username: ', reciever.username);

        let notificationReq = {
            to: reciever.fireBaseToken,
            notification: {
                title: 'Happy Recycling!',
                body: 'You have logged in to Bottle Battle successfully',
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


        

        if (userResponse.success) {
            const data = { username: username }
            const jwtToken = jwt.sign(data, key)
            const response = {
                success: true,
                status: 200,
                data: jwtToken
            }
            return response
        }
        else {
            return userResponse
        }
    } catch (error) {
        return  {
            success: false,
            message: `Error while signing in: ${error.message}`,
            status: 500,
            data: null
        }
    }
}

module.exports = {
    googleSignIn
}
