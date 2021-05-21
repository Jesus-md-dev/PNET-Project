'use strict';

const express = require('express');
const router = express.Router();
const smartphonesService = require('./smartphones-service');

//Recuperar un smartphone existente por ID.
router.get('/:_id', function (req, res) {
    let _id = req.params._id;
    smartphonesService.get(_id, (err, smartphone) => {
            if (err) {
                res.status(500).send({
                	msg: err
            	});
            } else if (smartphone === null){
            	res.status(500).send({
                    msg: "No existe ningún smartphone con ese ID"
                });
            } else {
                res.status(200).send(smartphone);
            }
        }
    );
});

//Añadir un nueva smartphone.
router.post('/', function (req, res) {
    let smartphone = req.body;
    smartphonesService.add(smartphone, (err, smartphone) => {
            if (err) {
                res.status(500).send({
                    msg: err
                });
            } else if (smartphone !== null) {
                res.send({
                    msg: 'Smartphone creado'
                });
            }
        }
    );
});

module.exports = router;