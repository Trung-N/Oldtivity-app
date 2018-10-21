var controller = require('../controller/eventController.js');
var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../app');
var should = chai.should();

chai.use(chaiHttp);

//Test the /POST route for explore
describe('/POST explore events', () => {
    it('it should POST location and receive events sorted by distance', (done) => {
        var location = {
            "latitude":-37.7,
            "longitude": 144.8
        }
        chai.request(server)
            .post('/explore')
            .send(location)
            .end((err, res) => {
                res.should.have.status(200);
                res.body.should.be.a('object');
                var array = Object.entries(res.body);
                if (array.length > 0){
                    var i =0;
                    //Check if all events have all the correct fields
                    for(i; i < array.length; i++){
                        array[i][1].should.be.a('object');
                        array[i][1].should.have.property("title");
                        array[i][1].should.have.property("distance");
                        array[i][1].should.have.property("phoneNumber");
                        array[i][1].should.have.property("location");
                        array[i][1].should.have.property("description");
                        array[i][1].should.have.property("date");
                        array[i][1].should.have.property("host");
                        array[i][1].should.have.property("membersCount");
                        if (array[i][1]["membersCount"]>0){
                            array[i][1].should.have.property("members");
                        }
                        //Check if events are sorted correctly
                        if (i<array.length-1){
                            array[i][1]["distance"].should.be.at.most(array[i+1][1]["distance"]);
                        }
                    }
                }
                done();
            });
    });
});

//Test the /POST route for myevents
describe('/POST my events', () => {
    it('it should POST location and receive events sorted by date', (done) => {
        var location = {
            "latitude":-37.7,
            "longitude": 144.8
        }
        chai.request(server)
            .post('/myevents')
            .send(location)
            .end((err, res) => {
                res.should.have.status(200);
                res.body.should.be.a('object');
                var array = Object.entries(res.body);

                if (array.length > 0){
                    var i =0;
                    //Check if all events have all the correct fields
                    for(i; i < array.length; i++){
                        array[i][1].should.be.a('object');
                        array[i][1].should.have.property("title");
                        array[i][1].should.have.property("distance");
                        array[i][1].should.have.property("phoneNumber");
                        array[i][1].should.have.property("location");
                        array[i][1].should.have.property("description");
                        array[i][1].should.have.property("date");
                        array[i][1].should.have.property("host");
                        array[i][1].should.have.property("membersCount");
                        if (array[i][1]["membersCount"]>0){
                            array[i][1].should.have.property("members");
                        }
                        //Check if events are sorted correctly
                        if (i<array.length-1){
                            date1 = array[i][1]["date"].split('/');
                            date1[1]--;
                            date1[0]++;
                            date1 = new Date(date1[2], date1[1], date1[0]);
                            date2 = array[i+1][1]["date"].split('/');
                            date2[1]--;
                            date2[0]++;
                            date2 = new Date(date2[2], date2[1], date2[0]);
                            date1.should.be.at.most(date2);
                        }
                    }
                }
                done();
            });
    });
});

//Test the /POST route for event
describe('/POST my events', () => {
    it('it should POST event id and receives its details', (done) => {
        var id = {
            "id":"-LP-PIspPA-1dhBErdhl",
        }
        chai.request(server)
            .post('/event')
            .send(id)
            .end((err, res) => {
                res.should.have.status(200);
                res.body.should.be.a('object');
                //Check if event has all the correct fields
                res.body.should.have.property("title");
                res.body.should.have.property("phoneNumber");
                res.body.should.have.property("location");
                res.body.should.have.property("description");
                res.body.should.have.property("date");
                res.body.should.have.property("host");
                done();
            });
    });
});

//Test removing past events from a list of events
describe('Date filtering', () => {
    it('it should check correct date filtering', (done) => {
        testEvents = {
            "-LP-J0oCJIduM7AlGNxl": {
                "date": "17/12/2028",
                "location": " "
            },
            "-LP-PIspPA-1dhBErdhl": {
                "date": "17/12/2028",
                "location": " "
            },
            "-LP-Hn98AL7Dg8KyG2l2": {
                "date": "17/12/2028",
                "location": " "
            }
        }
        //check if function is keeping future events
        controller.eventDateFilter(Object.entries(testEvents)).should.have.lengthOf(3);
        testEvents = {
            "-LP-J0oCJIduM7AlGNxl": {
                "date": "17/12/1028",
                "location": " "
            },
            "-LP-PIspPA-1dhBErdhl": {
                "date": "17/12/1028",
                "location": " "
            },
            "-LP-Hn98AL7Dg8KyG2l2": {
                "date": "17/12/1028",
                "location": " "
            }
        }
        //Checks if function deletes past events
        controller.eventDateFilter(Object.entries(testEvents)).should.have.lengthOf(0);
        testEvents = {
            "-LP-J0oCJIduM7AlGNxl": {
                "date": "17/12/1028",
                "location": " "
            },
            "-LP-PIspPA-1dhBErdhl": {
                "date": "17/12/2028",
                "location": " "
            },
            "-LP-Hn98AL7Dg8KyG2l2": {
                "date": "17/12/2028",
                "location": " "
            }
        }
        //Checks if function deletes past events and keeps futures events at the same time
        controller.eventDateFilter(Object.entries(testEvents)).should.have.lengthOf(2);
        testEvents = {
            "-LP-J0oCJIduM7AlGNxl": {
                "date": "17/12/3028",
            },
            "-LP-PIspPA-1dhBErdhl": {
                "location": " "
            },
            "-LP-Hn98AL7Dg8KyG2l2": {
            }
        }
        //Check if function deletes events with invalid data
        controller.eventDateFilter(Object.entries(testEvents)).should.have.lengthOf(0);
        done();
    });
});

//Test converting a array to json
describe('arrayToJson function', () => {
    it('it should convert a events array to a json object', (done) => {
        testEvents = [['a', 'b'], ['c', 'd']];
        testEvents.should.be.a('array');
        controller.arrayToJson(testEvents).should.be.a('object');
        done();
    });
});

//Test that geople returns the correct number of geocodes from a list of events in the correct format
describe('Geocode function', () => {
    it('It should find geocodes for location strings', (done) => {
        testEvents = {
            "-LP-J0oCJIduM7AlGNxl": {
                "location": "22 Milton Street Elwood Victoria 3184"
            },
            "-LP-PIspPA-1dhBErdhl": {
                "location": "Birdwood Ave South Yarra Victoria 3141"
            },
            "-LP-Hn98AL7Dg8KyG2l2": {
                "location": "Royal Botanical Gardens Victoria Birdwood Ave South Yarra Victoria 3141"
            }
        }
        testEvents = Object.entries(testEvents);
        //Checks for correct number of geocode results and that all geocodes are valid
        controller.eventGeocode(testEvents,function(geocodes){
            geocodes.length.should.be.equal(3);
            for(i=0;i<geocodes.length;i++){
                geocodes[i].length.should.be.equal(2);
                geocodes[i][0].should.be.at.most(90);
                geocodes[i][1].should.be.at.most(180);
                geocodes[i][0].should.be.not.below(-90);
                geocodes[i][1].should.be.not.below(-180);
            }
            done();
        });

    });
});

//Test that the function is counting the number of members who have currently joined the event
describe('Count Members function', () => {
    it('It should count the number of members in an event', (done) => {
        testMembers = [["a", true],
            ["b", true],
            ["c", true],
            ["d", true],
            ["e", true]]
        //count with all members joined
            controller.countMembers(testMembers).should.be.equal(5);
        testMembers = [["a", true],
            ["b", true],
            ["c", false],
            ["d", false],
            ["e", true]]
        //check when some member that joined left
        controller.countMembers(testMembers).should.be.equal(3);
        testMembers = []
        //check when no members have joined
        controller.countMembers(testMembers).should.be.equal(0);
        testMembers = [["a", false],
            ["b", false]]
        //check when all members that joined left
        controller.countMembers(testMembers).should.be.equal(0);
        done();
    });
});

//Test that distance calculates the correct number between two opoints
describe('distance function', () => {
    it('It should calculate the distance between two points', (done) => {
        controller.distance(5,5,5,5).should.be.equal(0);
        controller.distance(50,50,0,0).should.be.equal(7293.89);
        controller.distance(0,0,0,0.01).should.be.equal(1.11);
        controller.distance(5,10,5,-10).should.be.equal(2215.35);
        done();
    });
});

//Test that function sorts array correctly according to distance
describe('distance sort function', () => {
    it('It should sort event based on distance to user', (done) => {
        testEvent =[['a',{}],['b',{}],['c',{}],['d',{}],['e',{}]]
        testGeocode = [[50,50],[10,20],[10,40], [10, 30], [10, 31]]
        expectedResults = ['d','e','b','c','a'];
        results = (testEvent,testGeocode,10,30);
        for (i=0;i<results.length;i++){
            results[i][0].should.be.equal(expectedResults[i]);
        }
        done();
    });
});

//Test that function sorts array correctly according to date
describe('date sort function', () => {
    it('It should sort event based on the date', (done) => {
        testEvent =[['a',{'date': "11/11/3000"}],['b',{'date': "11/11/2000"}],['c',{'date': "10/11/2000"}],['d',{'date': "11/11/1000"}],['e',{'date': "11/12/2000"}]]
        expectedResults = ['d','c','b','e','a'];
        results = controller.dateSort(testEvent);
        for (i=0;i<results.length;i++){
            results[i][0].should.be.equal(expectedResults[i]);
        }
        done();
    });
});