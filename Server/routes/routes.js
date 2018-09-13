const controller = require('../controller/userController.js');

module.exports = function(app) {

    app.get('/',controller.login);
};
