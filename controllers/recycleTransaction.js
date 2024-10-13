
const recycleTransactionService = require('../services/recycleTransaction');

const addRecycleTransaction = async (req, res) => {    
    const {username, recycleItemId, quantity} = req.body;
    //console.log(username, recycleItemId, quantity);
    const serviceResponse = await recycleTransactionService.addRecycleTransaction(username, recycleItemId, quantity);

 return res.status(serviceResponse.status).json(serviceResponse);
}

const getTop10RecycledItems = async (req, res) => {
    try {
        const serviceResponse = await recycleTransactionService.getTop10RecycledItems();
        return res.status(serviceResponse.status).json(serviceResponse);
    }
    catch (error) {
        return res.status(500).json({ message: error.message });
    }
}

const getUserRecycleTransactions = async (req, res) => {
    //console.log("here2");
    const username = req.params.username;
    const serviceResponse = await recycleTransactionService.getUserRecycleTransactions(username);

 return res.status(serviceResponse.status).json(serviceResponse);
}

const getDateOfLastTransaction = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await recycleTransactionService.getDateOfLastTransaction(username);

    // We can't do toString to undefined, so we need to check if it's undefined first
    if (serviceResponse.data === undefined) {
        return res.status(serviceResponse.status).json(serviceResponse);
    }

    return res.status(serviceResponse.status).json(serviceResponse.data.toString());

}

module.exports = { addRecycleTransaction, getUserRecycleTransactions, getDateOfLastTransaction,getTop10RecycledItems };