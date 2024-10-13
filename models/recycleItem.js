// We're going to use the 'mongoose' library to interact with our MongoDB database:
const mongoose = require('mongoose');

// We're going to use the Schema class from mongoose:
// Our schema will define the structure of documents in our RecycleItems collection:
const Schema = mongoose.Schema;

// We're going to create a new schema for our RecycleItems collection:
const RecycleItem = new Schema({
    // For the new DB:
    barcode: {
        type: [String],
        required: true,
    },
    brand: {
        type: String,
        required: true,
    },
    manufacturer: {
        type: String,
        required: true,
    },
    productName: {
        type: String,
        required: true,
    },
    suitableRecycleBin: {
        type: String,
        required: true,
    },
    notesAndSuggestions: {
        type: String,
        required: false,
    },
    isHadap: {
        type: Number,
        required: true,
    },


    // For the old DB:
    /*mmitzrach: {
      type: Number,
      required: true,
    },
    mida: {
        type: Number,
      required: true,
    },
    mida_name: {
        type: String,
      required: true,
    },
    mishkal: {
        type: Number,
      required: true,
    },
    mitzrah_name: {
        type: String,
      required: true,
    },*/
      
});

// We're going to export our schema so that we can use it in other files:
// We're going to use the 'mongoose.model()' method to create a new model for our Recycle Items collection, named 'RecycleItems':
//const RecycleItemModel = mongoose.model('RecycleItems', RecycleItem);
//module.exports = { RecycleItemModel };
module.exports = mongoose.model('RecycleItem', RecycleItem);

// IMPORTANT!!!!
// Code below inserts the initial data into the RecycleItems collection, from groceries.json file.:
// Note: this code should be executed only once, when the collection is empty.
/*
const data = require("../data/groceries2.json");

async function start() {
    await RecycleItem.insertMany(data)
    .then(() => {
        console.log("Data inserted");
    })
    .catch((err) => {
        console.log(err);
    });
}

start();
*/
