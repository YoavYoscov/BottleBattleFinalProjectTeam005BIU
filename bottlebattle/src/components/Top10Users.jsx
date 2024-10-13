import LoadingSpinner from "./LoadingSpinner";

const goldColorForLeaderboard = 'gold';
const silverColorForLeaderboard = 'silver';
const bronzeColorForLeaderboard = '#CD7F32';

// This component is showing the top 10 users, as a table.
export default function Top10Users({top10Users, loadingTop10Users}) {

    // We want to add a nice indicative color to the top 3 users (gold, silver and bronze). So we use this function- checking the index and changing the backgroundColor accordingly.
    const getBackgroundColorAccordingToPosition = (index) => {
        switch(index) {
            case 0:
                return goldColorForLeaderboard;
            case 1:
                return silverColorForLeaderboard;
            case 2:
                return bronzeColorForLeaderboard;
            default:
                return 'white';
        }
    };

    return (
        <div>
            <LoadingSpinner loading={loadingTop10Users} />
            {!loadingTop10Users &&  (
                <table className="table">
                    <thead>
                        <tr>
                            <th>Position</th>
                            <th>Username</th>
                            <th>Points</th>
                        </tr>
                    </thead>
                    <tbody>
                        {top10Users.map((user, index) => (
                            <tr key={user.username} style={{backgroundColor: getBackgroundColorAccordingToPosition(index)}}>
                                <td>{index + 1}</td>
                                <td>{user.username}</td>
                                <td>{user.userPoints}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
