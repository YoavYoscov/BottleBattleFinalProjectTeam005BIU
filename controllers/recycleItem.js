
const recycleItemService = require('../services/recycleItem');

const getAllRecycleItems = async (req, res) => {
    const serviceResponse = await recycleItemService.getAllRecycleItems();

 return res.status(serviceResponse.status).json(serviceResponse);
};

// getRecycleItemById, isHadap, getSuitableRecycleBin, getNotesAndSuggestions, getBetterAlternativeIfSuchExists

const getRecycleItemById = async (req, res) => {
    const id = req.params.id;
    const serviceResponse = await recycleItemService.getRecycleItemById(id);

return res.status(serviceResponse.status).json(serviceResponse);
}

const isHadap = async (req, res) => {
    const id = req.params.id;
    const serviceResponse = await recycleItemService.isHadap(id);

return res.status(serviceResponse.status).json(serviceResponse);
}

const getSuitableRecycleBin = async (req, res) => {
    const id = req.params.id;
    const serviceResponse = await recycleItemService.getSuitableRecycleBin(id);

return res.status(serviceResponse.status).json(serviceResponse);
}

const getNotesAndSuggestions = async (req, res) => {
    const id = req.params.id;
    const serviceResponse = await recycleItemService.getNotesAndSuggestions(id);

return res.status(serviceResponse.status).json(serviceResponse);
}

const getBetterAlternativeIfSuchExists = async (req, res) => {
    const id = req.params.id;
    const serviceResponse = await recycleItemService.getBetterAlternativeIfSuchExists(id);

return res.status(serviceResponse.status).json(serviceResponse);
}

const getItemFullNameWithManufacturerBrand = async (req, res) => {
    const id = req.params.id;
    const serviceResponse = await recycleItemService.getItemFullNameWithManufacturerBrand(id);

return res.status(serviceResponse.status).json(serviceResponse);
}



/*
const getRecycleItemById = async (req, res) => {
    const id = req.params.id;
    const serviceResponse = await recycleItemService.getRecycleItemById(id);

return res.status(serviceResponse.status).json(serviceResponse);
};

const createRecycleItem = async (req, res) => {
    const {name, description, points, image} = req.body;
    const serviceResponse = await recycleItemService.createRecycleItem(name, description, points, image);

return res.status(serviceResponse.status).json(serviceResponse);
};
*/


module.exports = { getAllRecycleItems, getRecycleItemById, isHadap, getSuitableRecycleBin, getNotesAndSuggestions, getBetterAlternativeIfSuchExists, getItemFullNameWithManufacturerBrand };