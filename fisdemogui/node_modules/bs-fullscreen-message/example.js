var browserSync = require('browser-sync').create();

browserSync.init({
    server: 'test/fixtures',
    plugins: [require('./')]
});

setTimeout(function () {
    browserSync.sockets.emit('fullscreen:message', {
        title: "Hello from Example",
        body:  '10 seconds have elapsed!'
    });
}, 5000);
