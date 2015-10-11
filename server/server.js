var Hapi = require('hapi'),
    serverConfig = require('config').server;

// Create a server with a host and port
var server = new Hapi.Server();
server.connection(serverConfig);

// Routes
require('./routes')(server);

// Start the server
server.start(function () {
    console.log("Server started at:", server.info.uri);
});