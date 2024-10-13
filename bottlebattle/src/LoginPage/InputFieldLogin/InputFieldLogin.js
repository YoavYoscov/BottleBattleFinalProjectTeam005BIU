// This is used in the LoginPage, it simplifies the code for the input fields, since they all have common properties.

function InputFieldLogin({ myDescription, myType, myPlaceHolder, myRef }) {

    //alert("myType: " + myType);
    return (

        <div className="mb-3">
            <label className="form-label">{myDescription}:</label>
            <input
                type={myType}
                className="form-control"
                placeholder={myPlaceHolder}

                ref={myRef}
            />
        </div>
    );
}

export default InputFieldLogin;