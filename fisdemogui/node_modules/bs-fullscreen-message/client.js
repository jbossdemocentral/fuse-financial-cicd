(function (socket) {

    var MSG_EVENT   = 'fullscreen:message';
    var CLEAR_EVENT = 'fullscreen:message:clear';
    var TIMEOUT = 5000;

    var styles = {
        position: 'fixed',
        width: '100%',
        background: 'white',
        bottom: 0,
        zIndex: 999999,
        border: '2px solid black',
        overflow: 'auto',
        color: '#444',
    };

    var elem = document.createElement('div');
    var body = document.getElementsByTagName('body')[0];
    var int;
    var key;

    for (key in styles) {
        elem.style[key] = styles[key];
    }

    socket.on(CLEAR_EVENT, function () {
        if (int) {
            clearTimeout(int);
        }
        if (elem.parentNode) {
            body.removeChild(elem);
        }
    });
    socket.on(MSG_EVENT, function (data) {

        if (int) {
            clearTimeout(int);
        }

        elem.innerHTML = '<h1 style="%s">%s</h1><div style="%s"><pre style="%s">%s</pre></div>'
            .replace('%s', data.titleStyles   || 'padding: 1em;background: black;color: white;font-size: 1em;font-family: sans-serif;font-weight: normal;')
            .replace('%s', data.title         || 'Message from Browsersync')
            .replace('%s', data.wrapperStyles || '')
            .replace('%s', data.preStyles     || 'white-space:pre')
            .replace('%s', data.body          || 'No msg provided, please check the console')

        body.appendChild(elem);

        int = setTimeout(function () {
            if (elem.parentNode) {
                body.removeChild(elem);
            }
            clearTimeout(int);
            int = undefined;
        }, data.timeout || TIMEOUT);
    });
})(window.___browserSync___.socket);
