// This component is a loading spinner that is displayed when the page is loading.
// We use this to indicate the user that the page is still loading (meanwhile show partial data, alongside the spinning loading spinner).

export default function LoadingSpinner({isLoading}) {

    if(!isLoading) return null

    // We return here a div element with the class name "loading-spinner", of Bootstrap.
    return (
        <div className="loading-spinner"/>
    )
}