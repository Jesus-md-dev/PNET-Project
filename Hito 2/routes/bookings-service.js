'use strict';

const MongoClient = require('mongodb').MongoClient;
let db;
let ObjectId = require('mongodb').ObjectID;
const Bookings = function () {
};

Bookings.prototype.connectDb = function (callback) {
    MongoClient.connect("mongodb+srv://testPNET:testPNET123@jmd-pnet-2020-2021.tejai.mongodb.net/myFirstDatabase?retryWrites=true&w=majority",
        {useNewUrlParser: true, useUnifiedTopology: true},
        function (err, database) {
            if (err) {
                callback(err);
            }

			db = database.db('jmd-pnet-2020-2021').collection('bookings');

            callback(err, database);
        });
};

Bookings.prototype.add = function (booking, callback) {
    return db.insertOne(booking, callback);
};

Bookings.prototype.get = function (_id, callback) {
    return db.find({_id: ObjectId(_id)}).toArray(callback);
};

Bookings.prototype.getAll = function (callback) {
    return db.find({}).toArray(callback);
};

Bookings.prototype.update = function (_id, updatedBooking, callback) {
    delete updatedBooking._id;
    return db.updateOne({_id: ObjectId(_id)}, {$set: updatedBooking}, callback);};

Bookings.prototype.remove = function (_id, callback) {
    return db.deleteOne({_id: ObjectId(_id)}, callback);
};

Bookings.prototype.removeAll = function (callback) {
    return db.deleteMany({}, callback);
};

module.exports = new Bookings();