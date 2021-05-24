const express = require('express');
const logger = require('morgan');
const bodyParser = require('body-parser');
const cors = require('cors');
const http = require('http');
const path = require('path');
const app = express();
const PORT = process.env.PORT || 3000;
const baseAPI = '/api/v1';
const bookingsService = require('./routes/bookings-service');
const bookings = require('./routes/bookings');

app.use(bodyParser.json());
app.use(logger('dev'));
app.use(bodyParser.urlencoded({
    extended: true
}));

app.use(cors());
app.use('/bookings', bookings);

const server = http.createServer(app);

bookingsService.connectDb(function (err) {
    if (err) {
        console.log('Could not connect with MongoDB – bookingsService');
        process.exit(1);
    }

    server.listen(PORT, "127.0.0.1" || 'localhost', function () {
        console.log('Server up and running on localhost:' + PORT);
    });
});