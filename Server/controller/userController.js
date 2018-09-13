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

module.exports.login = function(req,res){
    var db = admin.database();
    var ref = db.ref().child('events');
    var events = [];
    ref.once("value", function(snapshot){
        events = Object.entries(snapshot.val());
        eventDateFilter(events);
        eventGeocodes = eventGeocode(events,function(geocodes){
            console.log(geocodes);
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