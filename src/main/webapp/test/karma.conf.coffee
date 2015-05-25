module.exports = (config) ->
  config.set
    basePath: '../'

    frameworks: ['jasmine']

    files: [
      'bower_components/jquery/dist/jquery.min.js',
      'bower_components/jquery-ui/ui/jquery-ui.js',
      'bower_components/angularjs/angular.min.js',
      'bower_components/angular-mocks/angular-mocks.js',
      'bower_components/bootstrap/dist/js/bootstrap.min.js',
      'bower_components/angular-bootstrap/ui-bootstrap.min.js',
      'bower_components/angular-route/angular-route.min.js',
      'bower_components/restangular/dist/restangular.min.js',
      'bower_components/lodash/lodash.min.js',
      'bower_components/angular-ui-calendar/src/calendar.js',
      'bower_components/fullcalendar/fullcalendar.js',
      'bower_components/fullcalendar/gcal.js',
      'bower_components/angular-ui-utils/ui-utils.min.js',
      'bower_components/angular-ui-map/ui-map.min.js',
      'resources/scripts/main.js',
      'resources/scripts/**/*.js',
      'test/unit/**/*.js'
    ]

    reporters: ['progress']

    port: 9876

    colors: true

    logLevel: config.LOG_INFO

    autoWatch: false

    browsers: ['Chrome', 'Firefox']

    singleRun: false
