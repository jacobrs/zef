var express = require('express');
var hbs = require('express-hbs');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

var router = express.Router();

router.all('/', function(req, res){
    res.render('index', {
        layout: false
    });
});

router.all('*', function(req, res){
    res.send("404");
});

module.exports = router;