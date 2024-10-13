import './DownloadPage.css'
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import UsersPage from '../UsersPage/UsersPage';

function DownloadPage() {
    
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


    return (
        <>
            <div className="full-screen d-flex flex-column align-items-center justify-content-center py-4 bg-body-tertiary">
                <div className="container">
                    <div className="row">
                        <div className="col-12 col-md-12 d-flex flex-column align-items-center">
                            <h1 className="title display-4 text-center">Bottle Battle - Download the Android app!</h1>
                            <br></br>
                            <img id="featureGraphic" className="img-fluid mb-4" src="/BottleBattleFeatureGraphicNew.png" alt="Bottle Battle" />
                            
                            <input type="button" className="py-2 w-20 btn btn-lg btn-primary" value="Download Bottle Battle App" onClick={() => window.location.href = "./app-release.apk"} />
                        </div>
                </div>
            </div>
            </div>
        </>
    )


}

export default DownloadPage;