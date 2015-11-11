var NodeRSA = require('node-rsa');

module.exports = function(){
    var KeyManager = function(){};

    KeyManager.prototype._generateKey = function(){
        //512 bit key - 368 yields "digest too big for rsa key"
        return new NodeRSA("-----BEGIN RSA PRIVATE KEY-----\nMIIBOgIBAAJBAJpaSy5vuX1j6uJc7qq+9C3B5om+OZfikUasGIBzGfwE15jnwhu/\n6gpbsrJ+z6uGzoTxBdvBJXbrTgq6hWE6FwkCAwEAAQJALQilIMJGGzh4wlyc4cJe\n2a7WBWAzy4W2Hyl86JUsgqOuIR1Y3OQ6LydjVpyF/EL3P8q6ehMT9kkZ4Qt72QNU\nxQIhAOE5EN0quxFiwcpHPL4HkPntnnOUhxqhh1BJNKL521/DAiEAr3H83Tn/fWu/\nTvzYetYPdmFJhGkUIDXCdS3ACCAubUMCIF6QdFk4gNUa7x08be+oA6I8uAP1gAiZ\ne9Qk+JnZaNe7AiAo6yfznNRGtzuOO5BtzcEbdgNEe5FzFba9x6bS/lZ1qwIhAK9s\n+BM8qg+kFjI8qC16Zd+QTl+E+AWsD7YWHRS3sHGK\n-----END RSA PRIVATE KEY-----",
            {"signingScheme" : "sha1"});
    };

    KeyManager.prototype.getKeyPair = function(){
        var key = this._generateKey();
        return { public: key.exportKey("public"), private: key.exportKey("private")};
    };

    KeyManager.prototype.getSign = function(data){
        var key = this._generateKey();
        return key.sign(data,'base64');
    };

    return new KeyManager();
}();
