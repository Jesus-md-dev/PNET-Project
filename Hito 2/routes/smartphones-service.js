'use strict';

const MongoClient = require('mongodb').MongoClient;
let db;
let ObjectId = require('mongodb').ObjectID;
const Smartphones = function () {};

Smartphones.prototype.connectDb = function (callback) {
    MongoClient.connect("mongodb+srv://claudiasm:mongoPNET@csm-pnet-2020-2021.typll.mongodb.net/myFirstDatabase?retryWrites=true&w=majority",
        {useNewUrlParser: true, useUnifiedTopology: true},
        function (err, database) {
            if (err) {
                callback(err);
            }

			db = database.db('csm-pnet-2020-2021').collection('smartphones');

            callback(err, database);
        });
};

Smartphones.prototype.add = function (smartphone, callback) {
    return db.insertOne(smartphone, callback);
};

Smartphones.prototype.get = function (_id, callback) {
    return db.find({_id: ObjectId(_id)}).toArray(callback);
};

module.exports = new Smartphones();