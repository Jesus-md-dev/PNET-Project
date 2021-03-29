'use strict';

const express = require('express');
const router = express.Router();
const bookingsService = require('./bookings-service');

router.get('/', function (req, res) {
    bookingsService.getAll((err, bookings) => {
        if (err) {
            res.status(500).send({
                msg: err
            });
        } else if (bookings === null) {
            res.status(500).send({
                msg: "Bookings null"
            });
        } else {
            res.status(200).send(bookings);
        }
    }
    );
});

router.post('/', function (req, res) {
    let booking = req.body;
    bookingsService.add(booking, (err, booking) => {
        if (err) {
            res.status(500).send({
                msg: err
            });
        } else if (booking !== null) {
            res.send({
                msg: 'Booking created!'
            });
        }
    }
    );
});


router.delete('/', function (req, res) {
    bookingsService.removeAll((err) => {
        if (err) {
            res.status(500).send({
                msg: err
            });
        } else {
            res.status(200).send({
                msg: 'Bookings deleted!'
            });
        }
    });
});


router.get('/:_id', function (req, res) {
    let _id = req.params._id;
    bookingsService.get(_id, (err, booking) => {
        if (err) {
            res.status(500).send({
                msg: err
            });
        } else if (booking === null) {
            res.status(500).send({
                msg: "Booking null"
            });
        } else {
            res.status(200).send(booking);
        }
    }
    );
});


router.put('/:_id', function (req, res) {
    const _id = req.params._id;
    const updatedBooking = req.body;
    bookingsService.update(_id, updatedBooking, (err, numUpdates) => {
        if (err || numUpdates === 0) {
            res.status(500).send({
                msg: err
            });
        } else {
            res.status(200).send({
                msg: 'Booking updated!'
            });
        }
    });
});


router.delete('/:_id', function (req, res) {
    let _id = req.params._id;
    bookingsService.remove(_id, (err) => {
        if (err) {
            res.status(500).send({
                msg: err
            });
        } else {
            res.status(200).send({
                msg: 'Booking deleted!'
            });
        }
    });
});


module.exports = router;
