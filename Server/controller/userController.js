var admin = require('firebase-admin');
var serviceAccount = require('./oldivity-cbf15-firebase-adminsdk-t1dur-d81fb8262c.json');
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://oldivity-cbf15.firebaseio.com/'
});

var NodeGeocoder = require('node-geocoder');
var geocoder = NodeGeocoder({
    provider: 'opencage',
    apiKey: '761238e07064497cb24af3499d16709b'
});

//Find an event by its id and returns its details
module.exports.event = function(req,res){
    var db = admin.database();
    var ref = db.ref().child('events');
    var events = [];
    ref.once("value", function(snapshot){
        events = snapshot.val();
        var id = req.body.id;
        console.log(events);
        res.send(200, events[id]);
    });

};

//Sends back future available events sort by date
module.exports.events = function(req,res){
    var db = admin.database();
    var ref = db.ref().child('events');
    var events = [];
    ref.once("value", function(snapshot){
        events = Object.entries(snapshot.val());
        eventDateFilter(events);
        eventGeocodes = eventGeocode(events,function(geocodes){
            var lat = req.body.latitude;
            var lng = req.body.longitude;
            events = distanceSort(events, geocodes, lat, lng);
            events = dateSort(events, geocodes, lat, lng);
            res.send(200, arrayToJson(events));
        });
    });

};

//Sends back future available events sort by distance
module.exports.eventsearch = function(req,res){
    var db = admin.database();
    var ref = db.ref().child('events');
    var events = [];
    ref.once("value", function(snapshot){
        events = Object.entries(snapshot.val());
        eventDateFilter(events);
        eventGeocodes = eventGeocode(events,function(geocodes){
            var lat = req.body.latitude;
            var lng = req.body.longitude;
            events = distanceSort(events, geocodes, lat, lng);
            res.send(200, arrayToJson(events));
        });
    });

};

//Filter events to the ones that occur in the future
function eventDateFilter(events){
    var currentDate = new Date();
    var eventSize = events.length;
    for (var i = eventSize-1; i >= 0; i--){
        if(events[i][1].hasOwnProperty("date") && events[i][1].hasOwnProperty("location")){
            eventDate = events[i][1]['date'].split('/');
            eventDate[1]--;
            eventDate[0]++;
            eventDate = new Date(eventDate[2], eventDate[1], (eventDate[0]));
            if (eventDate<currentDate){
                events.splice(i, 1);
            }
        }
        else{
            events.splice(i, 1);
        }
    }
    return events;
}

//Helper function to format results
function arrayToJson(events){
    eventsJson = {};
    for(i = 0; i < events.length; i++){
        eventsJson[events[i][0]] = events[i][1];
    }
    return eventsJson;
}

//finds the geocodes for all events
function eventGeocode(events, callback){
    addresses = [];
    for (var i = 0; i < events.length; i++){
        addresses.push(events[i][1]['location']);
    }
    console.log(addresses);
    geocoder.batchGeocode(addresses, function (err, results) {
        // Return an array of type {error: false, value: []}
        var geocodes = [];
        for (var j = 0; j < events.length; j++){
            if(Object.entries(results[j])[1][1].length>0){
                geo = Object.entries(Object.entries(results[j])[1][1][0]);
                lat = geo[0][1];
                lng = geo[1][1];
                geocodes.push([lat, lng]);
            }
            else{
                geocodes.push([0, 0]);
            }

        }
        console.log(geocodes);
        callback(geocodes) ;
    });
}

//Sorts events based on distance to user
function distanceSort(events, geocodes, lat, lng){
    for (var i = 0; i< events.length; i++){
        events[i][1]['distance'] = distance(lat, lng, geocodes[i][0], geocodes[i][1]);
        function Comparator(a, b) {
            if (a[1]['distance'] < b[1]['distance']) return -1;
            if (a[1]['distance'] > b[1]['distance']) return 1;
            return 0;
        }
        events = events.sort(Comparator);
    }
    return events;
}

//Sorts events based on date
function dateSort(events){
    for (var i = 0; i< events.length; i++){
        function Comparator(a, b) {
            eventDate = a[1]['date'].split('/');
            eventDate[1]--;
            eventDate[0]++;
            eventDate = new Date(eventDate[2], eventDate[1], (eventDate[0]));
            if (a[1]['date'].split('/')[2] < b[1]['date'].split('/')[2]) return -1;
            if (a[1]['date'].split('/')[2] > b[1]['date'].split('/')[2]) return 1;
            if (a[1]['date'].split('/')[1] < b[1]['date'].split('/')[1]) return -1;
            if (a[1]['date'].split('/')[1] > b[1]['date'].split('/')[1]) return 1;
            if (a[1]['date'].split('/')[0] < b[1]['date'].split('/')[0]) return -1;
            if (a[1]['date'].split('/')[0] > b[1]['date'].split('/')[0]) return 1;
            return 0;
        }
        events = events.sort(Comparator);
    }
    return events;
}

//function to calculate distance from https://snipplr.com/view/25479/calculate-distance-between-two-points-with-latitude-and-longitude-coordinates/
function distance(lat1,lon1,lat2,lon2) {
    var R = 6371;
    var dLat = (lat2-lat1) * Math.PI / 180;
    var dLon = (lon2-lon1) * Math.PI / 180;
    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(lat1 * Math.PI / 180 ) * Math.cos(lat2 * Math.PI / 180 ) *
        Math.sin(dLon/2) * Math.sin(dLon/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c;
    return Math.round(d*100)/100;
}