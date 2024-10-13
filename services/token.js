const User = require('../models/user');
const jwt = require("jsonwebtoken")
const key = "Some super secret key shhhhhhhhhhhhhhhhh!!!!!"

const sendNotification = require('./notificationService');


const login = async (username, password, fireBaseToken) => {

    const userExist = await User.findOne({ username: username });
    if (userExist == null || !userExist.comparePassword(password)) {
        const response = {
            success: false,
            message: `Invalid username and/or password`,
            status: 404
        }
        return response;
    }

    const data = { username: username }
    const token = jwt.sign(data, key)

    const response = {
        success: true,
        status: 200,
        data: token
    }
    //save the user's fireBaseToken in the database in order to send notifications later
    await User.updateOne({ username : username }, {$set:{ fireBaseToken : fireBaseToken}});



    //send a push notification to the user who was added as a friend:
    reciever = await User.findOne({ username: username });

    
    sendNotification(reciever, 'Happy Recycling!', 'You have logged in to Bottle Battle successfully');


    /*
    const { GoogleAuth } = require('google-auth-library');
    const path = require('path');
    const fetch = require('node-fetch');
    const serviceAccountPath = path.resolve(__dirname, '../config/bottle-battle-project-firebase-adminsdk-cqxzq-aaf86367b9.json');

    
    const auth = new GoogleAuth({
        keyFile: serviceAccountPath,
        scopes: ['https://www.googleapis.com/auth/cloud-platform'],
    });

    async function sendNotification(reciever) {
        // Log we're here:
        console.log('Sending notification to: ', reciever.fireBaseToken, 'username: ', reciever.username);

        // Create the message payload in the new format
        let notificationReq = {
            message: {
                token: reciever.fireBaseToken,
                notification: {
                    title: 'Happy Recycling!',
                    body: 'You have logged in to Bottle Battle successfully',
                }
            }
        };

        // Get OAuth 2.0 token
        const client = await auth.getClient();
        const accessToken = await client.getAccessToken();

        // Send the notification using the HTTP v1 API
        await fetch('https://fcm.googleapis.com/v1/projects/bottle-battle-project/messages:send', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${accessToken.token}`, // Use Bearer token instead of server key
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(notificationReq),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Successfully sent message:', data);
        })
        .catch(error => {
            console.log('Error sending message:', error);
        });
    }
    sendNotification(reciever);
    */
















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

    // POST https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send

    //await fetch('https://fcm.googleapis.com/fcm/send', {
    await fetch('https://fcm.googleapis.com/v1/projects/bottle-battle-project/messages:send', {
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

module.exports = { login };