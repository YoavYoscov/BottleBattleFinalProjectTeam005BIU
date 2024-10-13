import './LoginPage.css'
import { useRef, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import InputFieldLogin from './InputFieldLogin/InputFieldLogin';
import UsersPage from '../UsersPage/UsersPage';
import BASE_URL from '../config/config';


function LoginPage({setIsLoggedIn}) {
    
    useEffect(() => {
        // Load Bootstrap JavaScript
        const script = document.createElement('script');
        script.src = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js';
        script.async = true;
        document.body.appendChild(script);
        
        return () => {
          document.body.removeChild(script); // Cleanup the script on unmount
        };
      }, []);


    // Using the useNavigate hook to redirect (without refreshing the page!) the user to the users page after the user has successfully logged in (with the correct username and password):
    const navigate = useNavigate();


    // Now, we also use the useRef hook (like getElementById) to have a way to relate to out input fields:
    const usernameRef = useRef(null);
    const passwordRef = useRef(null);

    // Using the useState hook to indicate whether a mismatch error has occurred:
    const [credentialsMismatchError, setCredentialsMismatchError] = useState('');

    async function handleFormSubmission(event) {
        // Preventing page refresh, which is the default action when submitting a form:
        event.preventDefault();

        var currUsername = usernameRef.current.value;
        var currPassword = passwordRef.current.value;

        const data = {
            "username": currUsername,
            "password": currPassword
        }
        // send the data (username & password) to the server via a post request:
        // fetch is async and only returns a promise, so await is used to make sure res does contain the server's response.
        var res = await fetch(`${BASE_URL}/api/Users/LoginAdmin`, {
            'method': 'post',
            'headers': {
                'Content-Type': 'application/json'
            },
            'body': JSON.stringify(data)
        })
        if (res.status === 200) { //server response status is 200 (means username & password are correct)
            // We unblock the access to the rest of the website by setting the 'isLoggedIn' state variable to true:
            setIsLoggedIn(true);
            // We redirect to the users page (since the user has successfully logged in as the Admin!):
            navigate('/users');
        } else {
            setCredentialsMismatchError("The username and password you have provided do not match the Admin's username and password. Please try again.");
        }
    }

    function returnToRegister(event) {
        event.preventDefault();
        navigate('/');
    }


    return (
        <>
            <div className="full-screen d-flex flex-column align-items-center justify-content-center py-4 bg-body-tertiary">
            
            <h1 className="title display-4 text-center">Admin Management Page</h1><br></br>
                <main className="form-signin w-100 m-auto">
                    <form onSubmit={handleFormSubmission}>
                        <div className="d-flex justify-content-center"><img className="mb-4" src="/BottleBattleLogo.jpg" alt="Bottle Battle Logo"  height="100" /></div>
                        <h1 className="h3 mb-3 fw-normal">Please sign in</h1>

                        <InputFieldLogin myDescription="Username" myType="username" myPlaceHolder="Enter your username here" myRef={usernameRef} />
                        <InputFieldLogin myDescription="Password" myType="password" myPlaceHolder="Enter your password here" myRef={passwordRef} />

                        <button className="btn btn-primary w-100 py-2" type="submit">Sign in</button>
                        <div id="divOfMismatch">{credentialsMismatchError}</div>
                        <p className="mt-5 mb-3 text-body-secondary">&copy; Some style (graphical design) of the login page and the "card" design by: Mark Otto, Jacob Thornton, and Bootstrap contributors</p>
                    </form>
                </main>
            </div>
        </>
    )


}

export default LoginPage;