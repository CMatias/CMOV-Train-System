module.exports = function(server) {

    server.route({
        method: 'GET',
        path: '/',
        config: {
            handler: function(request, reply) {
                reply('Hi there');
            }
        }
    });

};