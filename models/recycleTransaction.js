// We're going to use the 'mongoose' library to interact with our MongoDB database:
const mongoose = require('mongoose');

// We're going to use the Schema class from mongoose:
// Our schema will define the structure of documents in our RecycleTransactions collection:
const Schema = mongoose.Schema;

// We're going to create a new schema for our RecycleTransactions collection:
const RecycleTransaction = new Schema({
    username: {
      //type: mongoose.Schema.Types.ObjectId,
      //ref: "Users",
      // We identify user by its username:
      type: String,
      required: true,
    },
    recycleItems: {
      type: [
        {
          itemId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: "RecycleItem",
            required: true,
          },
          quantity: {
            type: Number,
            required: true,
          },
        },
      ],
      required: true,
    },
  },
  {
    timestamps: true,
  }
);

// We're going to export our schema so that we can use it in other files:
// We're going to use the 'mongoose.model()' method to create a new model for our Recycle Transactions collection, named 'RecycleTransactions':
module.exports = mongoose.model('RecycleTransactions', RecycleTransaction);