var admin = require('firebase-admin');

var serviceAccount = require('./oldivity-cbf15-firebase-adminsdk-t1dur-d81fb8262c.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://oldivity-cbf15.firebaseio.com/'
});

module.exports.login = function(req,res){
    var db = admin.database();
    var ref = db.ref().child('events');
    var events = [];
    ref.on("value", function(snapshot){
        events = eventToArray(snapshot);
        events = eventDateFilter(events);
        res.send(events);
    });

};

function eventToArray(snapshot) {
    var events = [];

    snapshot.forEach(function(childSnapshot) {
        var event = [];
        event.push(childSnapshot.key);
        var eventDetail = [];
        eventDetail.push(childSnapshot.child('date').val());
        eventDetail.push(childSnapshot.child('description').val());
        eventDetail.push(childSnapshot.child('location').val());
        eventDetail.push(childSnapshot.child('title').val());
        event.push(eventDetail);
        events.push(event);
    });

    return events;
};

function eventDateFilter(events){
    var currentDate = new Date();
    console.log(currentDate);

    var eventSize = events.length;
    for (var i = eventSize-1; i >= 0; i--){
        eventDate = events[i][1][0].split('/');
        eventDate[1]--;
        eventDate[0]++;
        eventDate = new Date(eventDate[2], eventDate[1], (eventDate[0]));
        if (eventDate<currentDate){
            events.splice(i, 1);
        }

    }
    return events;

}