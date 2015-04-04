/*jslint node: true */
'use strict';

var express = require('express');
var mongo = require('mongoose');

var router = express.Router();
var bodyParser = require('body-parser');

// Connect to mongo db
mongo.connect('mongodb://ec2-52-4-224-221.compute-1.amazonaws.com/oop');

var Account = require('../../models/Account');
var Picture = require('../../models/Picture');

var authController = require('./auth');
var picturesAPI = require('./pictures');
var accountsAPI = require('./accounts');


router.use(bodyParser.urlencoded({ extended: true }));
router.use( bodyParser.json() ); // to support JSON-encoded bodies


router.use('/accounts', accountsAPI);
router.use('/pictures', picturesAPI);

router.all('/', function(req, res, next){
    res.send("Welcome to Zef, soon to be on the High tek Interwebs");
});

router.all('*', function(req, res, next){
    res.send("404");
});

module.exports = router;
