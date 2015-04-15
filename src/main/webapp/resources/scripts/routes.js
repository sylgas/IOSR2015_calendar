(function() {
  var app;

  app = angular.module('calendar', ['ngRoute']);

  app.config(function($routeProvider, $locationProvider) {
    return $routeProvider.when('/home', {
      url: '/'
    }).when('/calendar', {
      templateUrl: '/resources/views/calendar.html',
      controller: 'CalendarController'
    });
  });

}).call(this);
