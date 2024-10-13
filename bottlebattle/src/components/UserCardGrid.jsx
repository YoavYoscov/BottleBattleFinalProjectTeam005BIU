import UserCard from "./UserCard";

// Since not all users have a profile picture, for users without one, we define a "fallback" anonymous-looking profile picture: 
const fallbackProfilePicture = 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png'; // Replace with your desired placeholder image URL

// This component is a grid of UserCard components, displaying all the users in the database,
// it gets easy when we already have the UserCard component, we should just use 'map' :)
export default function UserCardGrid({users, suspiciousUsers, userRecycleData, loading,removeUser}) {

   
    return (<div className="album py-5 bg-body-tertiary">
        <div className="container">
            <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                {users.map((user) => <UserCard 
                                        key={user._id} 
                                        loading={loading}
                                        removeUser={removeUser}
                                        user={user} suspiciousUsers={suspiciousUsers}
                                        userRecycleData={userRecycleData} 
                                        fallbackProfilePicture={fallbackProfilePicture} 
                                       />)}
            </div>
        </div>
    </div>)
}

