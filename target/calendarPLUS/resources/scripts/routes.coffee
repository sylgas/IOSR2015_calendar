app = angular.module('calendar', ['ngRoute'])

app.config ($routeProvider,$locationProvider) ->
  $routeProvider
  .when '/home',
      url: '/'

  .when '/calendar',
      templateUrl: '/resources/views/calendar.html'
      controller: 'CalendarController'


