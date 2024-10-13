import { Navigate } from "react-router-dom";
import RecycleItems from "../components/RecycleItems";


export default function ProductsPage({isLoggedIn}) {

    // This 'if' is very important - that's how we prevent users that are not logged in from accessing this page!
    if(!isLoggedIn) {
        return <Navigate to="/" />
    }
    return <div>
       <section className="py-5 text-center container">
        <div className="row py-lg-5">
          <div className="col-lg-6 col-md-8 mx-auto">
            <h1 className="fw-light">Items available to recycle</h1>
            <p className="lead text-body-secondary">In this page, all items available to recycle are presented.</p>
            <div className="d-flex justify-content-center"><img className="mb-4" src="/BottleBattleLogo.jpg" alt="Bottle Battle Logo"  height="100" /></div>

        </div>
        </div>
      </section>
       <div className="px-5">
            <RecycleItems />
       </div>
    </div>
}