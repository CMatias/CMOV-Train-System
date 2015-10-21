module.exports = function(fixtures) {
    fixtures.save('Station', {
        Station: [
            {

            },
            {

            },
            {

            }
        ]
    });

    fixtures('Trip', function(err, data) {
        if(err) {
            console.log(err);
            exit();
        } else {
            console.log('Loaded Trip fixtures.');
        }
    });
}