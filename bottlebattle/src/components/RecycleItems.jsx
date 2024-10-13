import { useEffect, useState } from "react";
import LoadingSpinner from "./LoadingSpinner";

import BASE_URL from '../config/config';


export default function RecycleItems({recycleItems}) {
    // We use the 'useState' hook to store the recycle items that we get from the server (we update the state accordingly).
    const [items, setItems] = useState([]);
    // Like in the other pages, here as well, we use the spinner to indicate that the page is loading (so it also needs a state).
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // This is getting the recycle items from the server.
        const getRecycleItemsFromServer = async () => {
            const res = await fetch(`${BASE_URL}/api/RecycleItems/`, {
                'method': 'GET',
                'headers': {
                    'Content-Type': 'application/json',
                    // Note that this request DOES require a JWT token, so we attach it to the headers, as 'Authorization':
                    'Authorization': `Bearer ${process.env.REACT_APP_TOKEN}`
                },
            });
      
            if (!res.status === 200) {
                throw new Error(`Error in trying to get all recycle items data from the server!, the error status is: ${res.status}`);
            }
            
            const resAsJson = await res.json();
      
            var dataOfAllItemsExtracted;
            if (resAsJson.data) {
                dataOfAllItemsExtracted = resAsJson.data;
            } else {
                dataOfAllItemsExtracted = [];
            }

            // Here, after retrieving all the recycle items from the server, we save them by updating the 'items' state variable:
            setItems(dataOfAllItemsExtracted);
        }
        getRecycleItemsFromServer().catch((e) => {
            console.error(e);
        }).finally(() => setLoading(false));
    }, [recycleItems]);


    // The "return" is mostly simple, the only "tricky" thing we have used here is items.map, this way we easily iterate over all the items we have in the 'items' state variable.
    return (
        <div >
            <LoadingSpinner isLoading={loading} />
            <h1>
                All Recycle Items
            </h1>
            <br/>
            <div className="row row-cols-1 row-cols-md-3 g-4">
            {items.map((item) => (
                <div key={item._id} className="col">
                    <div className="card h-100">
                        <div className="card-body">
                            <h5 className="card-title w-full text-center">{item.productName}</h5>
                            <p className="card-text">Brand: {item.brand}</p>
                            <p className="card-text">Manufacturer: {item.manufacturer}</p>
                            <p className="card-text">Suitable Recycle Bin: {item.suitableRecycleBin}</p>
                            <p className="card-text">Notes And Suggestions: {item.notesAndSuggestions ?? "None"}</p>
                            <p className="card-text">Is for single use (חד פעמי): {item.isHadap}</p>
                        </div>
                    </div>
                </div>
            ))}
            </div>

           
        </div>
    );
}