//PatternFly JS Dependencies Entry

//jQuery
//execute files in global context with script-loader: https://github.com/webpack/script-loader
require('script!jquery/dist/jquery.min');

//Bootstrap JS
require('bootstrap/dist/js/bootstrap.min');

//Datatables, jQuery Grid Component
require('datatables/media/js/jquery.dataTables.min');
require('drmonty-datatables-colvis/js/dataTables.colVis');
require('datatables.net-colreorder/js/dataTables.colReorder');

//PatternFly Custom Componets -  Sidebar, Popovers and Datatables Customizations
//Note: jquery.dataTables.js must occur in the html source before patternfly*.js
require('patternfly/dist/js/patternfly.min.js');

//Moment
require('moment/min/moment.min');

//Bootstrap Combobox
require('patternfly-bootstrap-combobox/js/bootstrap-combobox');

//Bootstrap Date Picker
require('bootstrap-datepicker/dist/js/bootstrap-datepicker.min');

//Bootstrap Date Time Picker - requires Moment
require('eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min');

//Bootstrap Select
require('bootstrap-select/dist/js/bootstrap-select.min');

//Bootstrap Touchspin
require('bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min');

//Bootstrap Tree View
require('patternfly-bootstrap-treeview/dist/bootstrap-treeview.min');

//Google Code Prettify - Syntax highlighting of code snippets
require('google-code-prettify/bin/prettify.min');

//MatchHeight - Used to make sure dashboard cards are the same height
require('jquery-match-height/jquery.matchHeight-min');

//Angular Application? You May Want to Consider Pulling Angular-PatternFly And Angular-UI Bootstrap instead of bootstrap.js
//See https://github.com/patternfly/angular-patternfly for more information
