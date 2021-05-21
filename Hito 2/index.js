const express = require('express');
const logger = require('morgan');
const bodyParser = require('body-parser');
const cors = require('cors');
const http = require('http');
const path = require('path');
const app = express();
const PORT = process.env.PORT || 8080;
const baseAPI = '/api/v1';
const smartphonesService = require('./routes/smartphones-service');
const smartphones = require('./routes/smartphones');

app.use(bodyParser.json());
app.use(logger('dev'));
app.use(bodyParser.urlencoded({
    extended: true
}));

app.use(cors());
app.use('/smartphones', smartphones);

const server = http.createServer(app);

smartphonesService.connectDb(function (err) {
    if (err) {
        console.log('Could not connect with MongoDB – smartphonesService');
        process.exit(1);
    }

    server.listen(PORT, function () {
        console.log('Server up and running on localhost:' + PORT);
    });
});