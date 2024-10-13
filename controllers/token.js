
const tokenService = require('../services/token');
const googleService = require('../services/googleSignIn');
const login = async (req, res) => {
    const { username, password, fireBaseToken } = req.body;
    const serviceResponse = await tokenService.login(username, password, fireBaseToken);
    return res.status(serviceResponse.status).json(serviceResponse);
};

const googleSignIn = async (req, res) => {
    const { token } = req.body;
    //const serviceResponse = await googleService.googleSignIn(token);
    const serviceResponse = await googleService.googleSignIn(token, req);
    return res.status(serviceResponse.status).json(serviceResponse);
}



module.exports = { login, googleSignIn };