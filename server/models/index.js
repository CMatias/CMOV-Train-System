var Sequelize = require('sequelize');
var config    = require('config').database;  // we use node-config to handle environments

// initialize database connection
var sequelize = new Sequelize(
    config.name,
    config.username,
    config.password,
    config.options
);

// load models
var models = [

];

models.forEach(function(model) {
    module.exports[model] = sequelize.import(__dirname + '/' + model);
});

// describe relationships
(function(m) {
})(module.exports);

// export connection
module.exports.sequelize = sequelize;