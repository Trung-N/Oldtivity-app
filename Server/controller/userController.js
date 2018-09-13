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
        res.send(events);
    });

};

function eventToArray(snapshot) {
    var events = [];

    snapshot.forEach(function(childSnapshot) {
        var event = [];
        event.push(childSnapshot.key);
        var eventDetail = [];
        eventDetail.push(childSnapshot.child('date'));
        eventDetail.push(childSnapshot.child('description'));
        eventDetail.push(childSnapshot.child('location'));
        eventDetail.push(childSnapshot.child('title'));
        event.push(eventDetail);

        events.push(event);
    });

    return events;
};