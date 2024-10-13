import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import Top10Users from "../components/Top10Users";
import Top10Recycled from "../components/Top10Recycled";

import BASE_URL from '../config/config';


// We use the Chart.js library to create a nice, styled graph with the data of the top 10 users.
import { Chart, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController } from 'chart.js';

const graphBarColors = [
    'rgba(200, 80, 115, 0.2)',
    'rgba(60, 150, 220, 0.2)',
    'rgba(260, 200, 75, 0.2)',
    'rgba(70, 190, 195, 0.2)',
    'rgba(154, 95, 260, 0.2)',
    'rgba(260, 150, 65, 0.2)',
    'rgba(260, 100, 130, 0.2)',
    'rgba(60, 160, 240, 0.2)',
    'rgba(260, 210, 90, 0.2)',
    'rgba(80, 190, 190, 0.2)'
];

const graphBarOutlineColors = [
    'rgba(200, 80, 115, 1)',
    'rgba(60, 150, 220, 1)',
    'rgba(260, 200, 75, 1)',
    'rgba(70, 190, 195, 1)',
    'rgba(154, 95, 260, 1)',
    'rgba(260, 150, 65, 1)',
    'rgba(260, 100, 130, 1)',
    'rgba(60, 160, 240, 1)',
    'rgba(260, 210, 90, 1)',
    'rgba(80, 190, 190, 1)'
];

// This is the StatsPage component. We will present the admin with the top 10 users and the top 10 recycled items.
export default function StatsPage({isLoggedIn}) {
    // We use the 'useState' hook to store the top 10 users and the top 10 items.
    // Note that we also store the loading state of each of them- thiis is to show the loading spinner in the meanwhile.
    const [top10Items, setTop10Items] = useState([]);
    const [loadingTop10Items, setLoadingTop10Items] = useState(true);
        
    const [top10Users, setTop10Users] = useState([]);
    const [loadingTop10Users, setLoadingTop10Users] = useState(true);

    useEffect(() => {
        const fetchTop10Users = async () => {
            // We send a GET request to the server, to get the data regarding the top 10 users (note that this request DOES require a JWT token):
            const res = await fetch(`${BASE_URL}/api/Leaderboard/top10/`, {
                'method': 'GET',
                'headers': {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${process.env.REACT_APP_TOKEN}`
             },
            });
  
            if (!res.status === 200) {
                throw new Error(`Error in trying to get top 10 users data from the server!, the error status is: ${res.status}`);
            }
            
            const resAsJson = await res.json();
  
            console.log(resAsJson)
            var dataOfTop10UsersExtracted;
            if (resAsJson.data) {
                dataOfTop10UsersExtracted = resAsJson.data;
            } else {
                dataOfTop10UsersExtracted = [];
            }

            setTop10Users(dataOfTop10UsersExtracted);
            //console.log("the top 10 users are: ", dataOfTop10UsersExtracted);

            // For some reason, when we just tried to use the Chart.js library, not all of its features worked, so we manually name them here:
            Chart.register(CategoryScale, LinearScale, BarElement, BarController, Title, Tooltip, Legend);


            // Now, we create the graph:
            const chartElementInPage = document.getElementById('myChart');
            const myChart = new Chart(chartElementInPage, {
                // We want a bar chart (a separate bar for each user):
                type: 'bar',
                data: {
                    labels: dataOfTop10UsersExtracted.map(user => user.username),
                    datasets: [{
                        label: 'Recycling Points',
                        data: dataOfTop10UsersExtracted.map(user => user.userPoints),
                        borderColor: graphBarOutlineColors,
                        backgroundColor: graphBarColors,
                        borderWidth: 1.5
                    }]
                },
                options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                            }
                        }
                    }
            });
        }

        fetchTop10Users().catch((e) => {
            console.error(e);
        }).finally(() => setLoadingTop10Users(false));
    }, []);

    useEffect(() => {
        // Now, we want to get the top 10 recycled items from the server:
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
                throw new Error(`Error in trying to get top 10 recycle items data from the server!, the error status is: ${res.status}`);
            }
            
            const resAsJson = await res.json();
      
            console.log(resAsJson)
            var dataOfTop10ItemsExtracted;
            if (resAsJson.data) {
                dataOfTop10ItemsExtracted = resAsJson.data;
            } else {
                dataOfTop10ItemsExtracted = [];
            }

            setTop10Items(dataOfTop10ItemsExtracted);
        }
        getTop10Items().catch((e) => {
            console.error(e);
        }).finally(() => setLoadingTop10Items(false));
    }, []);

    // Just like in the ProductsPage, this 'if' is very important - it prevents the user from accessing the page if he is not logged in (as the Admin).
    if(!isLoggedIn) {
        return <Navigate to="/" />
    }

    return (
        <div className="px-5 py-5">
            <section className="py-5 text-center container">
                <div className="row py-lg-5">
                    <div className="col-lg-6 col-md-8 mx-auto">
                        <h1 className="fw-light">Statistics Page</h1>
                        <p className="lead text-body-secondary">In this page, you can see different stats relevant to the users and the recycle items in the Bottle Battle database.</p>
                        <div className="d-flex justify-content-center"><img className="mb-4" src="/BottleBattleLogo.jpg" alt="Bottle Battle Logo"  height="100" /></div>
                    </div>
                </div>
            </section>

      <hr/>
      <br/>
                   
            <h1 class="text-center display-4">Top 10 Users</h1>
            <Top10Users top10Users={top10Users} loading={loadingTop10Users} />
            <br/><br/><br/><br/>
            <canvas id="myChart" style={{ border: '1px solid black' }}></canvas>

            <br/><br/><br/>
            <hr/>
            <br/><br/>
            <h1 class="text-center display-4">Top 10 Recycled Items</h1>
            <br/>
            <Top10Recycled top10Recycled={top10Items} loadingTop10Recycled={loadingTop10Items} />
            <br/><br/><br/><br/><br/>
        </div>
    );
}