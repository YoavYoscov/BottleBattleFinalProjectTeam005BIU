# Final Project for B.Sc in Computer Science, 2023-2024
<p align="center"><b><i>Yoav Yoscovich  |  Amir Schreiber       <br/><sub>Bar-Ilan University</sub></i></b></p>

> <div align="center"><h3>Click the link or image below to watch a short Project Introduction Video,<br/> demonstrating the different features in our project! </h3></div>
<div align="center">
  <h1><u><a href="https://youtu.be/66jHrUYvo44" target="_blank">Link to Project Introduction Video</a></u></h1>
</div>

<br/>


[![Bottle Battle Project](https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/BottleBattleFeatureGraphicNew.png)](https://youtu.be/66jHrUYvo44)

## Table of Contents
- [Introduction and Links to the Server, Web Client & Android Client:](#introduction-and-links-to-the-server-web-client--android-client)
- [Project Architecture Illustration Pictures](#project-architecture-illustration-pictures)
- [Features](#features)
  - [Android Client (Application) Features:](#android-client-application-features)
  - [Web Client (Admin User Management Tool) Features:](#web-client-admin-user-management-tool-features)
- [Instructions for running BottleBattle (Server, Web & Android Clients)](#instructions-for-running-bottlebattle-server-web--android-clients)
  - [Running the Server](#running-the-server)
  - [Running the Web Client](#running-the-web-client)
  - [Running the Android Client](#running-the-android-client-on-emulatorphone-conncted-to-the-computer-using-android-studio)
- [3rd Party Libraries used in the project](#3rd-party-libraries-used-in-the-project)


<br/><br/>

<hr/>

## Introduction and Links to the Server, Web Client & Android Client:
<br/>
<p>
  <b>Bottle Battle project includes 3 different components:</b>
  1. NodeJS Server.
  2. Android Client.
  3. Web Client (user management & flagging system alerting about suspicious users for admins).
</p>

All three components have been deployed online and may be tested without any local installations; Links:
1. NodeJS Server (used by android & web clients): https://bottlebattle.onrender.com
2. Android Client: https://bottlebattlebuild.netlify.app/download
3. Web Client: https://bottlebattlebuild.netlify.app
4. Privacy Policy & 3rd Party Libraries we have used: https://sites.google.com/view/bottle-battle-details/3rd-party-libraries

<br/>

</hr>

## Project Architecture Illustration Pictures:
<h3>1. Illustration of the different components, technologies & software languages:</h3>

![Project Architecture Illustration Picture 1](https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/ArchitectureIllustration1.png)
<h3>2. Informative “snapshot” illustrating the connections between the different components.</h3>

![Project Architecture Illustration Picture 2](https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/ArchitectureIllustration2.png)


<br>

<hr/>

## Features
### Android Client (Application) Features:
- The app enables users to report recycling different items using three methods:
 <table align="center">
    <tr>
      <td colspan="2" align="center">
        <h3>Three Methods to Report Recycling an Item:</h3>
      </td>
    </tr>
    <tr style="padding: 10px;">
      <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/itemNameIcon.jpg" width="50"></td><td><b>Typing <i>item's name</i></b> (for example, קוקה קולה 500 מ"ל).</td>
    </tr>
    <tr style="padding: 10px;">
      <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/barcodeIcon.jpg" width="50"></td><td><b>Scanning <i>item's barcode</i></b> (the app uses Firebase Machine Learning, "Firebase ML-Kit", in order to detect barcode numbers from pictures).</td>
    </tr>
    <tr style="padding: 10px;">
      <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/categoriesIcon.jpg" height="85"></td><td><b>Reporting recycling an item from <i>a list of categories</i></b> (i.e. plastic bottle 250ml).</td>
    </tr>
  </table>
  
- The app enables users to register using <b>our project's user management database</b> (using MongoDB) or using <b>Google Sign-In</b>.
- The app integrates with a database supporting <b><i>more than 5000 items</i></b>; the dataset is based on different sources, including the <i>Ministry of Health</i>.



- The app recognizes the recycled item which the user has reported, and using our database, instructs the user what is the suitable recycle bin.
<table align="center">
  <tr>
    <td colspan="2" align="center">
      <h3>Example (real app screenshots): Scanning Item's Barcode & Getting Instructions Regarding the Suitable Recycle Bin:</h3>
    </td>
  </tr>
  <tr style="padding: 10px;">
    <td width="20%"><b>Scanning <i>item's barcode</i></b></td><td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/milkScanBarcodeWithFrame.jpg" height="400"></td>
  </tr>
  <tr style="padding: 10px;">
    <td width="20%"><b>Instructing the user regarding the <i>suitable recycle bin</i></b></td><td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/guidingRecycleBinWithFrame.jpg" height="400"></td>
  </tr>
</table>


- <b>The app keeps track of each user's <i>recycling history</i></b>; this information is saved for further personalized information.
- Every time the user reports recycling a product, the user earns points. Each product results in earning a different number of points, depending on the nature of the product; <b><i>Products that are more environmentally friendly will reward the user with more points!</i></b> Some of the considerations that are taken into account in the item-scoring algorithm are:
  - :wastebasket: Any non-recyclable product (mostly disposable products, such as napkins) doesn't reward the user with any points.
  - :recycle: Any recyclable product rewards the user 10 points, and on top of that:
    <table align="center">
      <tr>
        <td colspan="2" align="center">
          <h3>Additional Criteria - Points Rewarding Algorithm</h3>
        </td>
      </tr>
      <tr style="padding: 10px;">
        <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/alternativeProductIcon.jpg" width="50"></td><td><b>If the item has a more environmentally friendly alternative</b>, we deduct 3 points from the initial 10 points the user should have received. For example, if the user reports recycling a 0.5L water plastic bottle, <b>the app advises the user to consider using the same product, but in a bigger container, to save plastic & money. <i>The app refers the user to the specific item which the app has recognized as identical in its contents, but in a more environmentally friendly packaging</i></b>. In total, by recycling an item which has a better packaging alternative, the user gets only 7 points.</td>
      </tr>
    
      <tr style="padding: 10px;">
        <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/twoLiterBottleIcon.jpg" width="40"></td><td><b>If the item is in a 2.0 Liter container</b>, we reward the user 10 extra points (earns 20 points in total). The reason for granting these 10 extra points is that using bottles with volume of 2.0L consumes less plastic than 1.5L or 0.5L bottles, hence more environmental-friendly. </td>
      </tr>

      <tr style="padding: 10px;">
        <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/oneAndHalfLiterBottleIcon.jpg" width="40"></td><td><b>If the item is in a 1.5 Liter container</b>, we reward the user 5 extra points (earns 15 points in total). The reason for granting these 5 extra points is that using bottles with volume of 1.5L consumes less plastic than 0.5L bottles, hence more environmental-friendly, but is still not optimal (like 2.0L bottles).</td>
      </tr>

      <tr style="padding: 10px;">
        <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/aluminiumCanIcon.jpg" width="40"></td><td><b>If the item is in an aluminium can container</b>, we reward the user 5 extra points (earns 15 points in total). The reason for granting these 5 extra points is that aluminum cans are significantly more environmentally friendly than plastic bottles. Aluminium is the most environmental container for soft drinks, as recycling an aluminium can saves 95% of the energy used to make a new can and no new material needs to be mined or transported. In fact, they are the most recycled and have the lowest carbon footprint. </td>
      </tr>

      <tr style="padding: 10px;">
        <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/glassBottleIcon.jpg" width="30"></td><td><b>If the item is in a glass container</b>, we deduct 1 point from the initial 10 points the user should have received. The reason for this deduction is that glass bottles are considered the most harmful soft drink container, even more than plastic. In fact, glass bottles have about a 95% bigger contribution to global warming than aluminium cans. Therefore, the user earns only 9 points for recycling such product. </td>
      </tr>

      <tr style="padding: 10px;">
        <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/cardboardIcon.jpg" width="55"></td><td><b>If the item is a cardboard</b>, we reward the user 5 extra points. The reason for granting these 5 extra points is that cardboards use 25% less energy and up to 99% less water than producing a new cardboard from non-recycled materials. </td>
      </tr>
    </table>

<br/><br/>

- The app presents each user with different information calculated according to the recycling history of the user, including:

<table align="center">
  <tr>
    <td colspan="2" align="center">
      <h3>Different Statistics calculated and presented by the app:</h3>
    </td>
  </tr>
  <tr style="padding: 10px;">
        <td>
          <ul>
            <li>
              <b>The total amount of <i>money</i> which the user has saved so far</b> (by recycling refundable products, such as plastic bottles etc.); emphasizing the nonnegligible amount of money saved by regularly recycling.
            </li>
            <br/><br/>
            <li>
              <b>The app shows the user an <i>estimation of the amount of plastic (in grams) which the user has saved so far</i></b> (by referring only to items made of plastic, that the user has recycled).
            </li>
          </ul>
        </td>
        <td align="center" width="50%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/statsWithFrame.jpg" height="400"></td>
  </tr>
</table>

  
<br/><br/>
- The app enables users to add other app users as their friends, to compete in recycling with their friends, motivating users to recycle.
<br/><br/>
- Based on the data saved in the DB regarding the number of points each app user has earned, the app generates three <i>personal</i> different leaderboards <i>for each user</i>:
<table align="center">
  <tr>
    <td colspan="2" align="center">
      <h3>Three Leaderboards</h3>
    </td>
  </tr>
  <tr style="padding: 10px;">
    <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/worldIcon.jpg" width="50"></td><td>Leaderboard including <b>all app users.</b></td>
  </tr>
  <tr style="padding: 10px;">
    <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/cityIcon.jpg" width="50"></td><td>Leaderboard including <b>all app users in current user's city.</b></td>
  </tr>
  <tr style="padding: 10px;">
    <td align="center" width="20%" ><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/friendsIcon.jpg" width="50"></td><td>Leaderboard including <b>all users who have been added as the user's friends</b>.</td>
  </tr>
</table>
<br>
<div align="center">
  <img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/leaderboardWithFrame.jpg" height="400">
</div>


<br/><br/>

- <b>To make the recycling experience more enjoyable, <i>each user has a rank</i></b> :trophy:; users start as “Novice Recyclers”, and advance in stages as they recycle more products.
<div align="center">
  <img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/levelProgressWithFrame.jpg" height="400">
</div>

<br>

- In case a user forgets his password, he may submit through the app a <b><i>Password Reset Request</i></b>; the app will generate a token which the user receives by email, and using that token, the user may change his password to a new one.
<img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/passwordReset.jpg" height="200">

<br/><br/>

<hr/>

### Web Client (Admin User Management Tool) Features:
 - The admins have a User Management Web App, enabling them to track user activity, features demonstration:

<table align="center">
  <tr>
    <td colspan="2" align="center">
      <h3><i>Real App Screenshots</i>: Web Client (Admin User Management Tool) Features:</h3>
    </td>
  </tr>
  <tr style="padding: 10px;">
    <td width="20%"><b><i>Admin Authentication</i></b></td><td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/adminLoginWithFrame.jpg" height="400"></td>
  </tr>
  <tr style="padding: 10px;">
    <td width="40%">
      <b>The admin user management tool includes an automatic flagging system, alerting the admins about users suspected of submitting misleading recycling reports; There are 3<i>"outlier criteria"</i>:</b>
      <ol>
        <li>A user with total quantity (of all items he has recycled), which is <b>2 <i>standard deviations</i> greater than the mean of all users</b>.</li>
        <li>A user who has recycled a <b>specific item with quantity greater than 100, whereas the sum of quantities of all other items (excluding that item) is less than 100</b>.</li>
        <li>A user who has <b>recycled at least 100 items, but hasn't recycled any of the <i>"top 10" most recycled items</i> by Bottle Battle app users</b>.</li>
      </ol>
    </td>
    <td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/outlierCriteriaWithFrame.jpg" height="400"></td>
  </tr>
  <tr style="padding: 10px;">
    <td width="20%">
      <b>The user management tool identifies the users who meet at least one of the "outlier criteria".</b>
    </td>
    <td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/outlierUsersDetectedWithFrame.jpg" height="400"></td>
  </tr>
  <tr style="padding: 10px;">
    <td width="20%">
      <b>The user management tool refers the admin to the <i>suspicious users</i> found (and marks them in red).</b>
    </td>
    <td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/redMarkedUserWithFrame.jpg" height="400"></td>
  </tr>
  <tr style="padding: 10px;">
    <td width="20%">
      <b>The user management tool presents the <i>top 10 items - most recycled items</i> by Bottle Battle app users.</b>
    </td>
    <td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/top10itemsWithFrame.jpg" height="400"></td>
  </tr>
  <tr style="padding: 10px;">
    <td width="20%">
      <b>The user management tool also presents the <i>top 10 users -</i> users who have achieved the highest number of points.</b>
    </td>
    <td align="center"><img src="https://raw.githubusercontent.com/YoavYoscov/mediaForReadme/main/Architecture/top10usersWithFrame.jpg" height="400"></td>
  </tr>
</table>

- In a nutshell, as demonstrated above, the User Management Web App enables the admins to track user activity; the automatic flagging system, alerting the admins about users suspected of submitting misleading recycling reports, enables the admins to delete such users from the app, ensuring fair and fun competition experience for all users!

<br/><br/>

<hr/>

## Instructions for running BottleBattle (Server, Web & Android Clients):**
### Running the Server:
> Note that the server is deployed (https://bottlebattle.onrender.com), so there is no need to run it locally; however, to run it locally, you may follow the steps detailed below.
1. Open in the terminal the directory where the server project is cloned, by:
```
cd [LOCATION]
```
2. To install the required dependencies (defined in the 'package.json' file), write in the terminal:
```
npm install
```
3. To run the server, write in the terminal:
```
npm start
```
4. Now the local Server will be available to handle requests from BottleBattle Clients (both Android & Web clients)!
   <br>

<br/><br/>

<hr/>

### Running the Web Client:
> Note that the web client is deployed (https://bottlebattlebuild.netlify.app), so there is no need to run it locally; however, to run it locally, you may follow the steps detailed below.
1. Open Clone this Git Repository (using VS Code or simply using GitHub CLI), using the URL:
```
https://github.com/YoavYoscov/FinalProjectTeam005
```

2. Switch to the branch named ```"WebClientBranch"```.

3. Open in the terminal the directory where the project was cloned, by:
```
cd [LOCATION]
```
2. To install the required dependencies (defined in the 'package.json' file), write in the terminal:
```
npm install
```

3. To run the server, write in the terminal:
```
npm start
```
4. Now the Web Client will be accessible in URL ```http://localhost:3000/```.
   <br>

> <i><b><u>OPTIONAL</u></b>: If you wish to integrate the Web Client with a local server, rather than the deployed online server, you can do so by accessing the file 'config.js' (located in '\src\config'), and simply changing the 'BASE_URL' defined there; As also instructed in the comments in 'config.js' file, comment out the line ```const BASE_URL = "https://bottlebattle.onrender.com";```, and uncomment the line below it: ```const BASE_URL = "http://localhost:5000";```. After you've run the server locally, you can also run the Web Client, using steps 1-4 detailed above. <br> The Bottle Battle Web Client will integrate with the local server.</i>

<br/><br/>

<hr/>

### Running the Android Client (on emulator/phone conncted to the computer, using Android Studio):
1. Open Android Studio, and clone this Git Repository, using the URL:
```
https://github.com/YoavYoscov/FinalProjectTeam005
```

2. Switch to the branch named ```"ClientBranch"```.

3. Make sure that "Developer Options" setting in your phone is enabled, and that "USB debugging" option is enabled as well.

4. Click the "Run" button in Android Studio (after installing an emulator/connecting your phone to the computer). <br> The Bottle Battle Android Client will integrate with the deployed online server.

> <i><b><u>OPTIONAL</u></b>: If you wish to integrate the Android Client with a local server, rather than the deployed online server, you can do so by simply changing the 'baseServerDomain' variable in file 'LoginActivity.java'; As also instructed in the comments in 'LoginActivity.java' file, comment out the line ```private final String baseServerDomain = "https://bottlebattle.onrender.com/";```, and uncomment the line below it: ```private final String baseServerDomain = "http://[YOUR_IP_ADDR]:5000";```, and change ```[YOUR_IP_ADDR]``` to your IP address (instructions for how to find your IP address are detailed below). After you've run the server locally, you can also run the Android Client, by clicking on the "Run" button in Android Studio (after installing an emulator/connecting your phone to the computer). <br> The Bottle Battle Android Client will integrate with the local server.</i>
   <br>

> <i><b>How to find your IP address?</b><ul><li><b>On Windows:</b> Open ```cmd``` and run the command ```ipconfig```. You can find your IP address in the IPv4 Address field of the output of the command. </li><li><b>On Linux:</b> Open the ```terminal``` and run the command ```ifconfig```. You can find your IP address in the IPv4 Address field of the output of the command. </li></ul></i>

<br/><br/>

<hr/>

## 3rd Party Libraries used in the project:
### Server Side:
<ul>
  <li><b>NodeJS</b>: JS runtime for server-side. https://github.com/nodejs</li>
  <li><b>Express</b>: minimalist web framework for NodeJS. https://github.com/expressjs/express?tab=readme-ov-file#Examples</li>
  <li><b>MongoDB</b>: NoSQL DB. https://github.com/mongodb/mongo</li>
  <li><b>MongoDB Atlas</b>: managed cloud DB for MongoDB. https://www.mongodb.com/atlas</li>
  <li><b>Mongoose</b>: ODM for MongoDB (documents ↔ objects). https://github.com/Automattic/mongoose</li>
  <li><b>Bcrypt</b>: password hashing. https://github.com/pyca/bcrypt</li>
  <li><b>Crypto</b>: built-in Node. https://github.com/brix/crypto-js</li>
  <li><b>JWT</b>: (JSON Web Tokens). https://github.com/jwtk/jjwt</li>
  <li><b>Nodemailer</b>: email sending service. https://github.com/nodemailer/nodemailer</li>
  <li><b>Moment-timezone</b>: “timezone-aware” time calculations. https://github.com/moment/moment-timezone</li>
</ul>

<hr/>

### Android Client Side:
<ul>
  <li><b>Retrofit</b>: HTTP client for Android. https://github.com/square/retrofit</li>
  <li><b>Okhttp</b>: sending & receiving HTTP requests. https://github.com/square/okhttp</li>
  <li><b>Gson</b>: serialization/deserialization library; Java Objects ↔ JSON. https://github.com/google/gson</li>
  <li><b>CircleImageView</b>: circular ImageView. https://github.com/hdodenhof/CircleImageView</li>
  <li><b>Glide</b>: for loading images from URL to view in XML. https://github.com/bumptech/glide</li>
  <li><b>Firebase Messaging</b>: cloud messaging for push notifications (Legacy FCM ↔ HTTP v1). https://firebase.google.com/products/cloud-messaging</li>
  <li><b>Firebase Machine Learning (ML-Kit)</b>: for detecting barcode from picture. https://firebase.google.com/docs/ml-kit</li>
  <li><b>Google Credentials</b>: handles OAuth2, for Google Sign-in. https://developers.google.com/identity/protocols/oauth2?hl=he</li>
</ul>

<hr/>

### Web Client Side:
<ul>
  <li><b>React</b>: JS library; building interactive and reusable user interfaces. https://react.dev/</li>

  <li>
    <b>Bootstrap</b>: CSS Framework. https://getbootstrap.com/ <br/>
    In particular, "Sign-In" and "Album" example templates for customization from Bootstrap's website were used:
    <ul>
      <li><i>"Album"</i>: https://getbootstrap.com/docs/5.3/examples/album/ </li>
      <li><i>"Sign-In"</i>: https://getbootstrap.com/docs/5.3/examples/sign-in/ </li>
    </ul>
  </li>

  <li><b>Chart.js</b>: JS library for creating interactive graphs in web applications using HTML5 canvas. https://github.com/chartjs/Chart.js</li>


  <li><b>HTML + CSS + JS</b>: structure + content; styling + visual design; interactive, dynamic functionality for web apps.</li>
</ul>
