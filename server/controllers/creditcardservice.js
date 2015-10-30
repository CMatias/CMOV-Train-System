exports.verifyCreditCard = function(err, data){
    if(err) {
        throw err;
    }

    var rand = Math.random();
    if(rand >= 0 && rand <= 0.95){
        return "Authorized";
    }
    else{
        return "Denied";
    }
};