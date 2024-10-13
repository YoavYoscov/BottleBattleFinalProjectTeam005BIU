// This component is showing the suspicious users.
// To make it convenient to the admin, we have added a click event to each user, so when the admin clicks on a user, the page will scroll to the section of that user.

export default function SuspiciousActivity({suspiciousUsers, scrollToSection}) {

    // First, we check how many times each user appears in the suspiciousUsers array.
    // We write next to each user how many times they appear in the list.
    // This way, the admin can see how many times each user has been suspicious, and decide whether to delete him accordingly.
    let usersCounter = {};
    suspiciousUsers.forEach(user => {
        if(usersCounter[user]) {
            usersCounter[user]++;
        } else {
            usersCounter[user] = 1;
        }
    });

    suspiciousUsers = suspiciousUsers.map(user => {
            return `${user} (meets ${usersCounter[user]} suspicious criteria)`;
        }
    );
    // We use a Set to avoid duplicate users in the list.
    suspiciousUsers = [...new Set(suspiciousUsers)];

    // If there are no suspicious users, we return null, there are no suspicious users to show...
    if(!suspiciousUsers.length)
         return null


    // It took time to find this bug, note that now the list of suspicious users contains not only usernames, but also, for example: "danielUsername (meets 1 suspicious criteria)",
    // so when we want to refer to a specific user (in the scrollToSection in the onClick()), we need to split the string and take only the username part.

    return ( 
        <div>
            <p className="text-danger fs-4 fw-bold">
                Suspicious activity detected for {suspiciousUsers.length} user(s)! <br/> Admin is advised to check the following users:
            </p>
            
            {suspiciousUsers.map((username, index) => (
                <p key={index} className="text-danger fs-7 fw-bold" onClick={() => scrollToSection(username.split(' (')[0])} style={{cursor:'pointer'}}>
                    {index + 1}) {username} 
                </p>
            ))}
        </div>
    )
}