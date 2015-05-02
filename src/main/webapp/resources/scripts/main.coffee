app = angular.module 'calendar', [
  'ngRoute'
  'ui.bootstrap'
  'ui.calendar'
  'ui.map'
]

app.config ($routeProvider) ->
  $routeProvider

  .when '/calendar',
    templateUrl: '/resources/views/calendar.html'
    controller: 'CalendarController'

  .when '/map',
    templateUrl: '/resources/views/map.html'
    controller: 'MapController'

  .otherwise
      templateUrl: '/resources/views/home.html'
      controller: 'HomeController'
