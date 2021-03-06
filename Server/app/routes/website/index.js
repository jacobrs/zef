var express = require('express');
var hbs = require('express-hbs');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

var router = express.Router();

hbs.registerHelper('raw-helper', function(options) {
    return options.fn();
});

router.all('/', function(req, res){
    console.log(req.isAuthenticated.toString());
    res.render('index', {
    });
});

router.all('*', function(req, res){
    res.send("404");
});

module.exports = router;