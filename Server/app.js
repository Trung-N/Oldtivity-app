// set up ======================================================================
// get all the tools we need
var express  = require('express');
var app      = express();
var port     = process.env.PORT || 3000;
var morgan       = require('morgan');
var bodyParser   = require('body-parser');






// configuration ===============================================================

// set up our express application
app.use(morgan('dev')); // log every request to the console
app.use(bodyParser()); // get information from html forms

// routes ======================================================================
require('./routes/routes.js')(app); // load our routes and pass in our app and fully configured passport

// launch ======================================================================
app.listen(port);
console.log('Express listening on port ' + port);
