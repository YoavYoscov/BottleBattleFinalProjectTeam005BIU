import LoadingSpinner from "./LoadingSpinner";

// This component is showing the top 10 recycled items, as "cards".
export default function Top10Recyclerd({top10Recycled, loadingTop10Recycled}) {
   return (
   <div className="row row-cols-1 row-cols-md-3 g-4">
                <LoadingSpinner isLoading={loadingTop10Recycled} />
                {top10Recycled.map((item) => (

                    <div key={item._id} className="col">
                        <div className="card h-100">
                            <div className="card-body">
                                <h5 className="card-title w-full text-center">{item.itemDetails.productName}</h5>
                                <p className="card-text">Total Quantity: {item.totalQuantity}</p>
                                </div>
                        </div>
                    </div>
                ))}
    </div>
   )
}