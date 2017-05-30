/**
 * Require Browsersync along with webpack and middleware for it
 */
var browserSync          = require('browser-sync').create();
var webpack              = require('webpack');
var webpackDevMiddleware = require('webpack-dev-middleware');
var webpackHotMiddleware = require('webpack-hot-middleware');
var stripAnsi            = require('strip-ansi');

/**
 * Require ./webpack.config.js and make a bundler from it
 */
var webpackConfig = require('../build/webpack.config.js');
var bundler       = webpack(webpackConfig);

/**
 * Reload all devices when bundle is complete
 * or send a fullscreen error message to the browser instead
 */
bundler.plugin('done', function (stats) {
    if (stats.hasErrors() || stats.hasWarnings()) {
        return browserSync.sockets.emit('fullscreen:message', {
            title: "Webpack Error:",
            body:  stripAnsi(stats.toString()),
            timeout: 100000
        });
    }
    browserSync.reload();
});

/**
 * Run Browsersync and use middleware for Hot Module Replacement
 */
browserSync.init({
    server: {
        baseDir: 'dist',
        logFileChanges: true,
        middleware: [
            webpackDevMiddleware(bundler, {
                // IMPORTANT: dev middleware can't access config, so we should
                // provide publicPath by ourselves
                publicPath: webpackConfig.output.publicPath,

                // pretty colored output
                stats: { colors: true }

                // for other settings see
                // http://webpack.github.io/docs/webpack-dev-middleware.html
            }),

            // bundler should be the same as above
            webpackHotMiddleware(bundler)
        ]
    },

    plugins: ['bs-fullscreen-message'],

    // no need to watch '*.js' here, webpack will take care of it for us,
    // including full page reloads if HMR won't work
    files: [
        './src/js/*.js',
        './src/less/*.less',
        './src/html/*.html',
        'dist/**/*'
    ]
});