const controller = require('../controller/eventController.js');

module.exports = function(app) {

    app.post('/explore',controller.eventsearch);
    app.post('/eventsearch',controller.eventsearch);
    app.post('/event',controller.event);
    app.post('/myevents',controller.events);
};
