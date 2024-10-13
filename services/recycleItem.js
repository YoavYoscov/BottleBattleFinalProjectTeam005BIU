const RecycleItem = require('../models/recycleItem');

const getAllRecycleItems = async () => {
    const recycleItems = await RecycleItem.find();
    const response = {
        success: true,
        message: `All recycle transactions have been found successfully`,
        status: 200,
        data: recycleItems
    }
    return response;
}

const getRecycleItemById = async (id) => {
    var recycleItem;
    try {
        recycleItem = await RecycleItem.findById(id);
    } catch (error) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }
    
    /*if (recycleItem == null) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }*/

    const response = {
        success: true,
        message: `Recycle item with id ${id} retrieved successfully`,
        status: 200,
        data: recycleItem
    }
    return response;
}

const isHadap = async (id) => {
    var recycleItem;
    try {
        recycleItem = await RecycleItem.findById(id);
    } catch (error) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }
    /*if (recycleItem == null) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }*/

    const isThisItemHadap = recycleItem.isHadap;
    const response = {
        success: true,
        message: `Recycle item with id ${id} retrieved successfully`,
        status: 200,
        data: isThisItemHadap
    }
    return response;
}

const getSuitableRecycleBin = async (id) => {
    var recycleItem;
    try {
        recycleItem = await RecycleItem.findById(id);
    } catch (error) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }
    /*
    if (recycleItem == null) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }*/

    const suitableRecycleBin = recycleItem.suitableRecycleBin;
    const response = {
        success: true,
        message: `Recycle item with id ${id} retrieved successfully`,
        status: 200,
        data: suitableRecycleBin
    }
    return response;
}


const getNotesAndSuggestions = async (id) => {
    var recycleItem;
    try {
        recycleItem = await RecycleItem.findById(id);
    } catch (error) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }

    /*
    if (recycleItem == null) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }*/
    
    if (recycleItem.notesAndSuggestions == null) {
        const response = {
            success: true,
            message: `No notes and suggestions found for recycle item with id ${id}`,
            status: 200,
            data: "No notes and suggestions found"
        }
        return response;
    }
    

    var notesAndSuggestions = recycleItem.notesAndSuggestions;

    const glassNote = "Did you know? Although many think otherwise, glass bottles are considered as the most harmful soft drink container, even more than plastic. In fact, glass bottles have about a 95% bigger contribution to global warming than aluminium cans. You get only 9 points for recycling glass.";
    const twoLiterNote = "Well done for choosing a bottle with volume of 2.0L. This saves money and consumes less plastic. You are awarded 10 more points (20 in total) for recycling this item!";
    const oneAndHalfLiterNote = "Well done for choosing a 1.5L bottle over bottles with smaller capacities, saving you money and consuming less plastic. You are awarded 5 more points (15 in total) for recycling this item!";
    const aluminiumCanNote = "Well done for choosing an aluminum can over a plastic bottle. Aluminium is the most environmental container for soft drinks! Recycling an aluminium can saves 95% of the energy used to make a new can and no new material needs to be mined or transported. In fact, they are the most recycled and have the lowest carbon footprint. You are awarded 5 more points (15 in total) for recycling this item!";
    const cardboardNote = "Well done for recycling cardboards. It uses 25% less energy and up to 99% less water than producing a new cardboard from non-recycled materials."

    // we use switch case:
    switch (notesAndSuggestions) {
        case "glassNote":
            notesAndSuggestions = glassNote;
            break;
        case "2.0LiterNote":
            notesAndSuggestions = twoLiterNote;
            break;
        case "1.5LiterNote":
            notesAndSuggestions = oneAndHalfLiterNote;
            break;
        case "aluminiumCanNote":
            notesAndSuggestions = aluminiumCanNote;
            break;
        case "cartonBoxNote":
            notesAndSuggestions = cardboardNote;
            break;
        default:
            break;
    }

    if (notesAndSuggestions.startsWith("thisIs")) {
        // This is the structure of the note: thisIs: currentItemVolume;barcodeOfSuggestedProduct: ###
        // We'll separate it into different variables:
        const note = notesAndSuggestions.split(";");
        var currentVolume = note[0].split(":")[1];
        var barcodeOfBetterProduct = note[1].split(":")[1];

        // Now we remove spaces before and after the number of the current volume and the barcode of the suggested product:
        currentVolume = currentVolume.trim();
        barcodeOfBetterProduct = barcodeOfBetterProduct.trim();

        // We can't just compare the barcode, we need to check whether the barcode field in the DB CONTAINS the barcodeOfBetterProduct (in case there are multiple barcodes for the same item):
        const suggestedProduct = await RecycleItem.findOne({ barcode: { $regex: barcodeOfBetterProduct } });

        //console.log("suggestedProduct", suggestedProduct);
        //console.log("currentVolume", currentVolume);
        //console.log("suggestedProduct.productName", suggestedProduct.productName);
        //console.log("barcodeOfBetterProduct", barcodeOfBetterProduct);


        notesAndSuggestions = `This bottle contains only ${currentVolume}ml. Consider using the same product, but in a bigger container, to save plastic & money. In particular, we suggest you to use the following item: ${suggestedProduct.productName} which is a more environmentally friendly alternative. By recycling this item, you get only 7 points`;
    }

    const response = {
        success: true,
        message: `Found notes and suggestions for recycle item with id ${id} successfully`,
        status: 200,
        data: notesAndSuggestions
    }
    return response;
}

// getBetterAlternativeIfSuchExists
const getBetterAlternativeIfSuchExists = async (id) => {
    var recycleItem;
    try {
        recycleItem = await RecycleItem.findById(id);
    } catch (error) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }
    /*
    if (recycleItem == null) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }*/

    const notesAndSuggestions = recycleItem.notesAndSuggestions;

    // We check if notesAndSuggestions is null before checking if it starts with "thisIs" because if it is null, we'll get an error...
    if (notesAndSuggestions == null) {
        const response = {
            success: true,
            message: `No better alternative found for recycle item with id ${id}`,
            status: 200,
            data: "No better alternative found"
        }
        return response;
    }

    if (notesAndSuggestions.startsWith("thisIs")) {
        // This is the structure of the note: thisIs: currentItemVolume;barcodeOfSuggestedProduct: ###
        // We'll separate it into different variables:
        const note = notesAndSuggestions.split(";");
        var currentVolume = note[0].split(":")[1];
        var barcodeOfBetterProduct = note[1].split(":")[1];

        // Now we remove spaces before and after the number of the current volume and the barcode of the suggested product:
        currentVolume = currentVolume.trim();
        barcodeOfBetterProduct = barcodeOfBetterProduct.trim();

        // We can't just compare the barcode, we need to check whether the barcode field in the DB CONTAINS the barcodeOfBetterProduct (in case there are multiple barcodes for the same item):
        const suggestedProduct = await RecycleItem.findOne({ barcode: { $regex: barcodeOfBetterProduct } });

        //console.log("suggestedProduct", suggestedProduct);
        //console.log("currentVolume", currentVolume);
        //console.log("suggestedProduct.productName", suggestedProduct.productName);
        //console.log("barcodeOfBetterProduct", barcodeOfBetterProduct);


        const response = {
            success: true,
            message: `Better alternative found for recycle item with id ${id}`,
            status: 200,
            data: suggestedProduct
        }
        return response;

    }

    // If we reach here, it means that there is no better alternative for the current item:
    const response = {
        success: true,
        message: `No better alternative found for recycle item with id ${id}`,
        status: 200,
        data: "No better alternative found"
    }
    return response;
}

const getItemFullNameWithManufacturerBrand = async (id) => {
    var recycleItem;
    try {
        recycleItem = await RecycleItem.findById(id);
    } catch (error) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }

    /*
    if (recycleItem == null) {
        const response = {
            success: false,
            message: `Recycle item with id ${id} not found`,
            status: 404
        }
        return response;
    }*/

    const manufacturer = recycleItem.manufacturer;
    const brand = recycleItem.brand;
    const productName = recycleItem.productName;

    var fullItemName;

    if (manufacturer === brand) {
        // If the manufacturer is the same as the brand, we don't want to display the brand twice:
        fullItemName = `${productName} ${manufacturer}`;
    } else {
        // 
        fullItemName = `${productName} ${manufacturer} ${brand}`;
    }

    //console.log("fullItemName", fullItemName);
    // If, in fullItemName, the same word appears more than once, we'll remove the duplicates:
    const wordsInFullItemNameSeparated = fullItemName.split(' ');
    const uniqueWords = [];
    for (let i = 0; i < wordsInFullItemNameSeparated.length; i++) {
        // We check whether the word we are currently going over is already in the array 'uniqueWords', and if not, we add it:
        if (!uniqueWords.includes(wordsInFullItemNameSeparated[i])) {
            uniqueWords.push(wordsInFullItemNameSeparated[i]);
        }
    }
    // Finally, we have the unique words in the right order, so we just need to join them, with ' ' between them:
    fullItemName = uniqueWords.join(' ');
    //console.log("fullItemName", fullItemName);

    

    const response = {
        success: true,
        message: `Full name of recycle item with id ${id} retrieved successfully`,
        status: 200,
        data: fullItemName
    }

    return response;

}

module.exports = { getAllRecycleItems, getRecycleItemById, isHadap, getSuitableRecycleBin, getNotesAndSuggestions, getBetterAlternativeIfSuchExists, getItemFullNameWithManufacturerBrand };