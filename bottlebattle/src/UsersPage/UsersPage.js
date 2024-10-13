import React, {useEffect, useState} from 'react';
import UserCardGrid from '../components/UserCardGrid';
import SuspiciousActivity from '../components/SuspiciousActivity';
import {Navigate} from 'react-router-dom';
import Top10Recyclerd from '../components/Top10Recycled';

import BASE_URL from '../config/config';


// This is the first page the admin sees after logging in. It shows all the users and their recycling history.
function UsersPage({isLoggedIn}) {
    const [loading, setLoading] = useState(true);
    // We use the 'useState' hook to define 3 state variables: 'users', 'userRecycleData' and 'suspiciousUsers':
    // 1. 'users': list of all users, which we will retrieve from the DB, by requesting it from the server.
    const [users, setUsers] = useState([]);
    // 2. 'userRecycleData': the recycling data FOR EACH user, which we will retrieve from the DB, by requesting it from the server.
    const [userRecycleData, setUserRecycleData] = useState({});
    // 3. 'suspiciousUsers': this is the "interesting part" - it will contain a list of users who our algorithm detects as suspicious (not credible), based on their recycling data!
    const [suspiciousUsers, setSuspiciousUsers] = useState([]);

    const [mean, setMean] = useState(0);
    const [variance, setVariance] = useState(0);
    const [standardDeviation, setStandardDeviation] = useState(0);

    const [top10Items, setTop10Items] = useState([]);


    const removeUser = (userName) => {
        const newUsers = users.filter(user => user.username !== userName);
        setUsers(newUsers);
    }
    // We use the 'useEffect' hook to get the data of the users and their recycling data from the server immediately, once the component is loaded:
    useEffect(() => {
        const getUsersDataFromServer = async () => {
            // We send a GET request to the server, to get the data regarding all the users (note that this request does NOT require a JWT token):
            const res = await fetch(`${BASE_URL}/api/Users/GetAllUsers/GetAllUsers`, {
                'method': 'GET',
                'headers': {
                    'Content-Type': 'application/json'
                },
            });

            if (!res.status === 200) {
                throw new Error(`Error in trying to get all users data from the server!, the error status is: ${res.status}`);
            }

            // If there was no error, we would like to process the data we have received from the server.
            // We want to "deal with" the data as json, hence the .json():
            const resAsJson = await res.json();

            // We want to take only the .data of the response (this is the actual data, and not status code etc.).
            // But we don't want to get an error if there is no data (so trying to access the .data will result in an error), so we define a default - an empty array:
            var dataOfAllUsersExtracted;

            if (resAsJson.data) {
                dataOfAllUsersExtracted = resAsJson.data;
            } else {
                // This means that there is no data. So this is the "default case":
                dataOfAllUsersExtracted = [];
            }

            // Now, in order to be able to use the data we worked hard to acheive, the data of all the users, we save it in the 'users' state variable:
            setUsers(dataOfAllUsersExtracted.filter(user => user.username));




            // Now, we want to get the recycling data of each user, and save it in the 'userRecycleData' state variable:
            // Fetch recycle data for each user
            const recycleData = {};
            // We go over all the users, and for each user, we send a GET request to the server asking for the recycling data of that user:
            for (const currentUser of dataOfAllUsersExtracted) {
                // To avoid errors, we make sure that the current user has a username (since afterwards we do currentUser.username so we want to make sure it exists):
                if (currentUser.username) {
                    const addressForRecycleDataRequestForCurrentUser = `${BASE_URL}/api/RecycleTransactions/${currentUser.username}`;
                    //console.log("The address is: " + addressForRecycleDataRequestForCurrentUser);

                    // Before sending the GET request to the server, we "wrap" it with try (we will handle the catch later):
                    try {
                        // We send a GET request to the server, asking for the recycling data of the current user:
                        const resRecycleDataForCurrentUser = await fetch(addressForRecycleDataRequestForCurrentUser, {
                        method: 'GET',
                        headers: {
                        'Content-Type': 'application/json',
                        // Note that this request DOES require a JWT token, so we attach it to the headers, as 'Authorization:
                        'Authorization': `Bearer ${process.env.REACT_APP_TOKEN}`
                    }});

                // Check for 404 and handle it without throwing an error
                if (resRecycleDataForCurrentUser.status === 404) {
                  //console.warn(`No recycle transactions found for ${currentUser.username}`);
                  continue; // Skip to the next user
                }

                if (!resRecycleDataForCurrentUser.ok) {
                  throw new Error(`HTTP error! status: ${resRecycleDataForCurrentUser.status}`);
                }

                const recycleResult = await resRecycleDataForCurrentUser.json();
                //console.log(`Recycle transactions for ${currentUser.username}:`, recycleResult);

                recycleData[currentUser.username] = recycleResult.data.recycleItems || [];
              } catch (recycleError) {
              }
            }
          }

          setUserRecycleData(recycleData);
          detectOutliers(recycleData);  // Call outlier detection function

      };

    getUsersDataFromServer()
    .catch((e) => {})
    .finally(() => setLoading(false));
    console.log('UsersPage.js: useEffect()');
  }, []);


  // This function makes the browser scroll to a specific section on the page - we use it to scroll to (each of the) "suspicious users":
  const scrollToSection = (uname) => {
    const section = document.getElementById(uname);
    if (section) {
      section.scrollIntoView({ behavior: 'smooth' });
    }
  };

    const getTop10Items = async () => {
        const res = await fetch(`${BASE_URL}/api/RecycleTransactions/GetTop10RecycledItems`, {
            'method': 'GET',
            'headers': {
                'Content-Type': 'application/json',
                // Note that this request DOES require a JWT token, so we attach it to the headers, as 'Authorization':
                'Authorization': `Bearer ${process.env.REACT_APP_TOKEN}`
            },
        });

        if (!res.status === 200) {
            alert(`Error in trying to get top 10 recycle items data from the server!, the error status is: ${res.status}`);
            throw new Error(`Error in trying to get top 10 recycle items data from the server!, the error status is: ${res.status}`);
        }

        const resAsJson = await res.json();

        var dataOfTop10ItemsExtracted;
        if (resAsJson.data) {
            //alert(resAsJson.data[0].productName)
            dataOfTop10ItemsExtracted = resAsJson.data;
        } else {
            dataOfTop10ItemsExtracted = [];
        }

        // We save the top 10 items in the 'top10Items' state variable:
        setTop10Items(dataOfTop10ItemsExtracted);
    }

    const checkTop10Items = (userItems, outliers, username) => {
        // Now, we check if the current user has recycled any of the top 10 items:
        const top10ItemsNames = top10Items.map(item => item.itemDetails.productName);
        const userRecycledItems = userItems.map(item => item.itemDetails.productName);
        const hasRecycledTop10Item = top10ItemsNames.some(item => userRecycledItems.includes(item));
        if (userRecycledItems.length >= 100 && !hasRecycledTop10Item) {
            outliers.push(username);
        }
    }

  // This is the function that detects outliers - suspicious users, based on the recycling data of the users:
  const detectOutliers = (recycleData) => {
    // The first indicator for an outlier, will be a common method we learned in Statistics course - 2 standard deviations greater than the mean:
    const totalRecycledItems = Object.values(recycleData).flat();
    const quantities = totalRecycledItems.map(item => item.quantity);

    // This is just an "edge case" (no recycle data at all)- it won't happen, but just in case...:
    if (quantities.length === 0) {
      return;
    }

    // In order to check that, we need to calculate the mean and the standard deviation of the quantities of the recycled items.
    // To calculate the mean, we sum all the quantities and divide by the number of quantities:
    const mean = quantities.reduce((acc, val) => acc + val, 0) / quantities.length;
    setMean(mean);

    // Calculating the variance is similar - we just have to square the difference between each quantity and the mean:
    const variance = quantities.reduce((acc, val) => acc + Math.pow(val - mean, 2), 0) / quantities.length;
    setVariance(variance);

    // Now, we can compute (by definition) the standard deviation:
    const standardDeviation = Math.sqrt(variance);
    setStandardDeviation(standardDeviation);

    const outliers = [];
    // Now, we go over all the users, and for each user, we check if he is an outlier!
    for (const username in recycleData) {
      // For the first criterion for an outlier, we check if the total quantity of recycled items is 2 standard deviations greater than the mean:
      const userItems = recycleData[username];
      const totalQuantity = userItems.reduce((acc, item) => acc + item.quantity, 0);
      if (totalQuantity > mean + 2 * standardDeviation) {
        outliers.push(username);
      }

      // Additional check that may mark a user as suspicious - if they have recycled one item with quantity > 100, whereas the sum of quantities of all items excluding that item is less than 100:
      if (userItems.some(item => item.quantity > 100) && totalQuantity - userItems.find(item => item.quantity > 100).quantity < 100) {
        outliers.push(username);
      }

            // Additional check that may mark a user as suspicious - if they have recycled 100 items and more, but haven't recycled any of the top 10 recycled items:
            getTop10Items().catch(e => {
                console.error(e);
            }).then(() => checkTop10Items(userItems, outliers, username)).catch(e => {
                console.error(e);
            })
        }
        setSuspiciousUsers(outliers); // update suspicious users state var only after finding all outliers
    };

    // This 'if' is very important - it checks whether the user is logged in, and if not, it redirects them to the login page:
    // Note that this check is even more crucial here, since this page allows user deletion - we want to ensure that only the admin can access it.
    if (!isLoggedIn) {
        return <Navigate to="/"/>;
    }

  return (
    <main>
      <section className="py-5 text-center container">
        <div className="row py-lg-5">
          <div className="col-lg-6 col-md-8 mx-auto">
            <h1 className="fw-light">User Management</h1>
            <p className="lead text-body-secondary">This page includes all users in the "Bottle Battle" app, with all of their details stored in the DB.</p>
            <SuspiciousActivity
            suspiciousUsers={suspiciousUsers}
            scrollToSection={scrollToSection}/>
          </div>
        </div>
      </section>


      <div className="py-5 px-5 row row-cols-1 row-cols-md-3 g-4">
        <div key="guidlines" className="col">
          <div className="card h-100">
            <div className="card-body">
              <h5 className="card-title w-full text-center display-3">Outlier criterion #1</h5>
              <br></br>
              <p className="card-text fw-bold">A user with total quantity (of all items he has recycled), which is 2 standard deviations greater than the mean of all users. </p>              <p className="card-text">Mean: {mean.toFixed(2)}</p>
              <p className="card-text">Variance: {variance.toFixed(2)}</p>
              <p className="card-text">Standard Deviation: {standardDeviation.toFixed(2)}</p>
              <br></br><br></br>
              <img src="https://static.s4be.cochrane.org/wp-content/uploads/2018/09/Image-1-Standard-deviation-Standard-error-.jpg" alt="Standard Deviation" width="70%" class="mx-auto d-block"></img>
            </div>
          </div>
        </div>
        <div key="guidlines" className="col">
          <div className="card h-100">
            <div className="card-body">
              <h5 className="card-title w-full text-center display-3">Outlier criterion #2</h5>
              <br></br>
              <p className="card-text fw-bold">A user who has recycled a specific item with quantity greater than 100, whereas the sum of quantities of all other items (excluding that item) is less than 100.</p>
              <br></br><br></br><br></br><br></br><br></br><br></br>
              <img src="./outlierGraph.png" alt="Standard Deviation" width="70%" class="mx-auto d-block justify-content-end"></img>
            </div>
          </div>
        </div>
        <div key="guidlines" className="col">
          <div className="card h-100">
            <div className="card-body">
              <h5 className="card-title w-full text-center display-3">Outlier criterion #3</h5>
              <br></br>
              <p className="card-text fw-bold">A user who has recycled at least 100 items, but hasn't recycled any of the "top 10" most recycled items by Bottle Battle app users.</p>
              <p className="card-text">Top 10 items are: </p>
              <Top10Recyclerd top10Recycled={top10Items} loadingTop10Recycled={loading} />


                    </div>
                </div>
            </div>
        </div>


        <UserCardGrid users={users}
                      removeUser={removeUser}
                      loading={loading}
                      suspiciousUsers={suspiciousUsers}
                      userRecycleData={userRecycleData}/>
    </main>);
}

export default UsersPage;
