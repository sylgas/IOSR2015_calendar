angular.module('calendar').controller 'CalendarController', ($rootScope, $scope, $modal) ->
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

      $scope.createEvent = @createEvent

      #example of adding event
      event =
        title: 'All Day Event'
        start: new Date(2015, 3, 15)
      $scope.eventSources = [[event]]

    createEvent: =>
      @editEvent(null)

    editEvent: (event) =>
      modalInstance = $modal.open
        templateUrl: 'resources/views/modals/event.html'
        controller: 'EventController'
        resolve:
          event: -> event
      modalInstance.result.then(@onEventEdited)

    onEventEdited: (event) ->
      $scope.eventSources[0].push(event)