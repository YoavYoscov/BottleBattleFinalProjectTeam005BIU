
const statsService = require('../services/stats');

const getTotalPlasticSavedForUser = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await statsService.getTotalPlasticSavedForUser(username);

return res.status(serviceResponse.status).json(serviceResponse);
}

const getTotalMoneySavedForUser = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await statsService.getTotalMoneySavedForUser(username);

return res.status(serviceResponse.status).json(serviceResponse);
}

const getUserPoints = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await statsService.getUserPoints(username);

return res.status(serviceResponse.status).json(serviceResponse);
}

const getUserLevel = async (req, res) => {
    const username = req.params.username;
    const serviceResponse = await statsService.getUserLevel(username);

return res.status(serviceResponse.status).json(serviceResponse);
}

// Maybe we'll also want:
// const getTotalPlasticSavedForCity = async (req, res) => {
// Consider this later.


module.exports = { getTotalPlasticSavedForUser, getTotalMoneySavedForUser, getUserPoints, getUserLevel };