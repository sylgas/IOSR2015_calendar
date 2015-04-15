app = angular.module('calendar', ['ngRoute', 'ui.map'])

app.config ($routeProvider) ->
  $routeProvider
  .when '/home',
      url: '/'

  .when '/calendar',
      templateUrl: '/resources/views/calendar.html'
      controller: 'CalendarController'

  .when '/map',
    templateUrl: '/resources/views/map.html'
    controller: 'MapController'


