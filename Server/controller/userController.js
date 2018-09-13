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
            res.send(arrayToJson(events));
        });
    });

};

function eventDateFilter(events){
    var currentDate = new Date();
    var eventSize = events.length;
    for (var i = eventSize-1; i >= 0; i--){
        eventDate = events[i][1]['date'].split('/');
        eventDate[1]--;
        eventDate[0]++;
        eventDate = new Date(eventDate[2], eventDate[1], (eventDate[0]));
        if (eventDate<currentDate){
            events.splice(i, 1);
        }

    }
    return events;
}

function arrayToJson(events){
    eventsJson = {};
    for(i = 0; i < events.length; i++){
        eventsJson[events[i][0]] = events[i][1];
    }
    return eventsJson;
}

function eventGeocode(events, callback){
    addresses = [];
    for (var i = 0; i < events.length; i++){
        addresses.push(events[i][1]['location']);
    }
    geocoder.batchGeocode(addresses, function (err, results) {
        // Return an array of type {error: false, value: []}
        var geocodes = [];
        for (var j = 0; j < events.length; j++){
            geo = Object.entries(Object.entries(results[j])[1][1][0]);
            lat = geo[0][1];
            lng = geo[1][1];
            geocodes.push([lat, lng]);
        }
        callback(geocodes) ;
    });
}

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

//function from https://snipplr.com/view/25479/calculate-distance-between-two-points-with-latitude-and-longitude-coordinates/
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