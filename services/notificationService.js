const { GoogleAuth } = require('google-auth-library');
const fetch = require('node-fetch');
const path = require('path');

const serviceAccountPath = path.resolve(__dirname, '../config/bottle-battle-project-firebase-adminsdk-cqxzq-aaf86367b9.json');

// Initialize Google Auth with the service account credentials
const auth = new GoogleAuth({
    keyFile: serviceAccountPath,
    scopes: ['https://www.googleapis.com/auth/cloud-platform'],
});

async function sendNotification(reciever, title, body) {
    // Log we're here:
    console.log('Sending notification to: ', reciever.fireBaseToken, 'username: ', reciever.username);

    let notificationReq = {
        message: {
            token: reciever.fireBaseToken,
            notification: {
                title: title,
                body: body,
            }
        }
    };

    try {
        // Get OAuth 2.0 token
        const client = await auth.getClient();
        const accessToken = await client.getAccessToken();

        // Send the notification using the HTTP v1 API
        const response = await fetch('https://fcm.googleapis.com/v1/projects/bottle-battle-project/messages:send', {
            method: 'POST',
            headers: {
                // Note that we're using the Bearer token instead of the server key- since we're using the HTTP v1 API, and not the legacy API!
                'Authorization': `Bearer ${accessToken.token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(notificationReq),
        });

        const data = await response.json();
        console.log('Successfully sent message:', data);

    } catch (error) {
        console.log('Error sending message:', error);
    }
}

module.exports = sendNotification;
