import { useCallback, useEffect, useState } from "react";
import LoadingSpinner from "./LoadingSpinner";

import BASE_URL from '../config/config';

// We use this function to avoid errors and checks if the image exists.
function checkIfImageExists(url, callback) {
    var img = new Image();
    img.onload = function() { callback(true); };
    img.onerror = function() { callback(false); };
    img.src = `data:image/png;base64,${url}`;
}

// This component is a card that displays the user's information - it is used iin many pages of the BottleBattle website :)
export default function UserCard({user, suspiciousUsers, userRecycleData, fallbackProfilePicture, loading,removeUser}) {
    // We use the 'useCallback' hook to define a function that deletes a user from the database:
    // The useCallback is necessary becuase when we tried to write the function without it, it caused an infinite loop.
    const deleteUser = useCallback((username) => {
        if(!window.confirm(`Are you sure you want to delete user ${username}?`)) { return }
        fetch(`${BASE_URL}/api/Users/DeleteUser/${username}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${process.env.REACT_APP_TOKEN}`
            },
        })
        .then(response => {
            if(response.ok) {
                console.log(`User ${username} deleted successfully`);
                alert(`User ${username} deleted successfully`);
                removeUser(username);
            } else {
                console.error(`Failed to delete user ${username}`);
            }
        })
        .catch(error => {
            console.error(`Failed to delete user ${username}`);
        });
    }, []);

    // We use the 'useState' hook to store the user's profile picture (we update the state accordingly).
    // The problematic part was that some users don't have a profile picture (since it is optional), so we use a "fallback" picture.
    const [image, setImage] = useState(fallbackProfilePicture);
    useEffect(() => {
        // We check whether the profile picture field of the user is a URL (we want to avoid errors when user doesn't have a profile picture).
        const isUrl = user.profilePic?.includes("https://");
        if(user.profilePic && !isUrl) {
            checkIfImageExists(user.profilePic, (exists) => {
                if(exists) {
                    setImage(`data:image/png;base64,${user.profilePic}`);
                    return;
                }
            });
        }    
    }, [user.profilePic]);


    return (
        <div id={user.username} key={user._id} className="col">
        <div  className={`card shadow-sm ${suspiciousUsers.includes(user.username) ? 'bg-danger text-white' : ''}`}>
          <img 
            className="bd-placeholder-img card-img-top"
            src={image}
            alt={`${user.username}'s profile`}
            height="230"
          />
          <div className="card-body">
            <p className="card-text"><strong>{user.username}</strong></p>
            <p className="card-text">{user.city} | Level: {user.userLevel}</p>
            <div className="d-flex justify-content-between align-items-center mb-3">
              <div className="btn-group">
                <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => deleteUser(user.username)}>Delete</button>
              </div>
              <small className="text-body-secondary">
                {
                  (user.moneySaved !== undefined && user.moneySaved !== null) 
                  ? `${user.moneySaved.toFixed(2)} $ saved` : 'No money data available'
                }
              </small>
              <small className="text-body-secondary">
                {user.userPoints ? `${user.userPoints} points` : 'No points data available'}
              </small>
            </div>
            
            <h6>Recycled Items:</h6>
            <LoadingSpinner isLoading={loading} />
            {!loading && <>
                {userRecycleData[user.username] && userRecycleData[user.username].length > 0 ? (
              <table className="table table-striped">
                <thead>
                  <tr>
                    <th>Item</th>
                    <th>Quantity</th>
                  </tr>
                </thead>
                <tbody>
                  {userRecycleData[user.username].map(item => (
                    <tr key={item._id}>
                      <td>{item.itemId.productName}</td>
                      <td>{item.quantity}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            ) : (
              <p>No recycled items available</p>
            )}
            </>}
          </div>
        </div>
      </div>
    )
}