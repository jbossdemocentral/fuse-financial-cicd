module.exports = {
    plugin: function () { /*noop*/ },
    'plugin:name': 'Fullscreen Messages',
    hooks: {
        "client:js": require('fs').readFileSync(__dirname + '/client.js', 'utf8')
    }
};
