import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Routes, Route, Link} from "react-router-dom";
import {useState} from 'react';
import LoginPage from './LoginPage/LoginPage';
import ProductsPage from './ProductsPage/ProductsPage';
import UsersPage from './UsersPage/UsersPage';
import StatsPage from './StatsPage/StatsPage';
import DownloadPage from './DownloadPage/DownloadPage';


function App() {
  

  // Using the useState hook to save indication of whether the use has logged in successfully:
  const [isLoggedIn, setIsLoggedIn] = useState(false);


  const Navbar = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">Bottle Battle</Link>
                <button 
                    className="navbar-toggler" 
                    type="button" 
                    data-bs-toggle="collapse" 
                    data-bs-target="#navbarNav" 
                    aria-controls="navbarNav" 
                    aria-expanded="false" 
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link" to="/products">Products</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/users">Users</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/stats">Stats</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/download">Download</Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};


  return (<>
      <BrowserRouter>
      <Navbar/>
          <Routes>
              <Route path="/" element={<LoginPage setIsLoggedIn={setIsLoggedIn} />}/>
              <Route path="/products" 
                     element={<ProductsPage isLoggedIn={isLoggedIn} />}/>
              <Route path="/users" 
                     element={<UsersPage isLoggedIn={isLoggedIn} />}/>
              <Route path="/stats" 
                element={<StatsPage isLoggedIn={isLoggedIn} />}/> 
              <Route path="/download" 
                element={<DownloadPage />}/> 
          </Routes>
      </BrowserRouter>
  </>);
}


export default App;
