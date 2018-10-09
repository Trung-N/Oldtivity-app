const controller = require('../controller/userController.js');

module.exports = function(app) {

    app.post('/eventsearch',controller.eventsearch);

    app.post('/event',controller.event);
};
