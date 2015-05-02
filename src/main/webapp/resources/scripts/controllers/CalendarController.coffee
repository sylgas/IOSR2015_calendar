angular.module('calendar').controller 'CalendarController', ($rootScope, $scope) ->
  new class
    constructor: ->
      $rootScope.title = "Calendar"
      $scope.uiConfig =
        calendar:
          height: 450
          editable: true
          header:
            left: 'month agendaWeek agendaDay'
            center: 'title'
            right: 'today prev,next'

      #example of adding event
      event =
        title: 'All Day Event'
        start: new Date(2015, 3, 15)
      $scope.eventSources = [[event]]