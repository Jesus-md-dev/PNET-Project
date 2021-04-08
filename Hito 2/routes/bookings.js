'use strict';

const express = require('express');
const router = express.Router();
const bookingsService = require('./bookings-service');

//Recuperar todas las reservas.
router.get('/', function (req, res) {
    bookingsService.getAll((err, bookings) => {
            if (err) {
                res.status(500).send({
                    msg: err
                });
            } else if (bookings === null){
            	res.status(500).send({
                    msg: "bookings null"
                });
            } else {
                res.status(200).send(bookings);
            }
        }
    );
});

//Recuperar una única reserva existente por ID.
router.get('/:_id', function (req, res) {
    let _id = req.params._id;
    bookingsService.get(_id, (err, booking) => {
            if (err) {
                res.status(500).send({
                	msg: err
            	});
            } else if (booking === null){
            	res.status(500).send({
                    msg: "bookings null"
                });
            } else {
                res.status(200).send(booking);
            }
        }
    );
});

//Añadir una nueva reserva.
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

//Actualizar una reserva existente por ID.
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

//Eliminar todas las reservas.
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

//Eliminar una ́unica reserva existente por ID.
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