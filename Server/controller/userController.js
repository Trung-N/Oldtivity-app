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
        events = snapshotToArray(snapshot);
        res.send(events);
    });

};

function snapshotToArray(snapshot) {
    var returnArr = [];

    snapshot.forEach(function(childSnapshot) {
        var item = childSnapshot.val();
        item.key = childSnapshot.key;

        returnArr.push(item);
    });

    return returnArr;
};