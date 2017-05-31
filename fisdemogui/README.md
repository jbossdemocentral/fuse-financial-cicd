# patternfly-demo-app

The PatternFly demo app serves as a boiler for building your production app with [PatternFly](http://patternfly.org/), [Node.JS](https://nodejs.org/en/), and [Webpack](https://webpack.github.io/).

![Image of PatternFly](https://avatars2.githubusercontent.com/u/6391110?v=3&s=400)
![Image of NodeJS](http://www.devsensation.es/sites/default/files/styles/large/public/field/image/nodejs-logo.png?itok=URP6hUpT)
![Image of Webpack](https://avatars0.githubusercontent.com/webpack?&s=256)

## Demo
The main demo can be found [here](https://rawgit.com/patternfly/patternfly-demo-app/master/dist/index.html). There is a sample [login](https://rawgit.com/patternfly/patternfly-demo-app/master/dist/login.html) page as well.

### Installation

Install project node modules:
```
npm i
```

### Development
For development with [BrowserSync](https://www.browsersync.io/) run:
```
npm start
```

This will do two things:

1. It will build the static assets in the [dist](dist) directory. You can just click on the `dist/index.html` file to browse around.
2. It will automatically open up the running application in your default browser, which is located at `localhost:3000`.

### Production
For production, you will just want to compile your webpack bundle. You'd then have to choose how to run your app (e.g. with Node by running `node server/app.js`, setting up CI, a process monitor, etc.; serving static assets built in the `/dist` directory) depending on your needs.

```
npm run build:prod
```

The resulting build will be in the [dist](dist) folder.

### Extending the Demo App

#### JS
All js references loaded in `src/js/main.js` will be chunked by webpack and loaded in a single file, `dist/main.bundle.js`. Feel free to add your own JS scripts and `require` them in the bundle.
 
If you would like to add `d3.js` or `c3.js` charts to your page, include the `charts` bundle in `dist/charts.bundle.js`.

#### HTML
All HTML documents added to [src/html](src/html) are copied as-is to the `dist` folder.

#### CSS/LESS
Less/css is written to a file via the [extract-text plugin](https://github.com/webpack/extract-text-webpack-plugin). You can write any custom less in `src/less/custom.less` and it will be compiled to `dist/custom.css` which can be referenced in your HTML.

Note that images and fonts referenced in your custom css are automatically inlined via webpack [url-loader](https://github.com/webpack/url-loader).

#### Webpack dev notes
You will want to copy any html or images that are referenced in html *<img>* tags to your *dist* folder via the [copy-webpack plugin](https://github.com/kevlened/copy-webpack-plugin). An
alternative is to source images in your js/jsx templates and [html-loader](https://github.com/webpack/html-loader) can compress them.

```
        new CopyWebpackPlugin([
            {
                from: { glob:'./src/html/*.html'},
                to: './',
                flatten: true
            },
            {
                from: { glob: 'node_modules/patternfly/dist/img/*.*'},
                to: './img',
                flatten: true
            }
        ]),
```

#### WebpackDevMiddleware / Hot Module Replacement
While developing and making to changes to `src` files, you should see changes propagate immediately to the browser. Files are also updated in the `dist` folder via the [write-file-plugin](write-file-webpack-plugin).

**Note:** New files will not be included automatically - you must restart your server with `npm start`.

**Note:** you can gitignore webpack incremental updates. These are written to `dist/hot`. You can read more about this [here](http://code.fitness/post/2016/02/webpack-public-path-and-hot-reload.html).

#### Having trouble with Webpack?
Given it is a new technology, there is certainly room for error. You can sometimes trace more error info with the `--display-error-details` arg:
```
webpack -p --config build/webpack.config.js --display-error-details
```

Also, there is a wonderful collection of detailed examples in the webpack project [here](https://github.com/webpack/webpack/tree/master/examples). 

There are some more helpful debugging tips [here](http://webpack.github.io/docs/troubleshooting.html). 

If you are still having troubles, find us on [PatternFly Gitter](https://gitter.im/patternfly/patternfly) or ask someone in the [Webpack community](https://gitter.im/webpack/webpack).
