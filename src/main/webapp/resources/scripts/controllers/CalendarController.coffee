angular.module('calendar').controller 'CalendarController', ($rootScope, $scope, $modal, EventService) ->
  new class
    constructor: ->
      $scope.eventSources = [[]]
      $rootScope.title = "Calendar"
      $scope.uiConfig =
        calendar:
          height: 450
          editable: true
          selectable: true
          header:
            left: 'month agendaWeek agendaDay'
            center: 'title'
            right: 'today prev,next'
          eventDrop: (event) ->
            changeEventDuration(event)
          eventResize: (event) ->
            changeEventDuration(event)
          select: (start, end) ->
            createEvent(start, end)
          viewRender: (view) ->
            displayEvents(view.start, view.end)

    changeEventDuration = (event) ->
      #todo: change duration of event
      console.log(event)

    createEvent = (start, end) ->
      #todo: redirect to create event with chosen duration
      console.log("start: " + start)
      console.log("end: " + end)

    displayEvents = (start, end) ->
      #todo: change after finish of service implementation
      receiveEvent(EventService.getBetweenDate(start, end))

    receiveEvent = (events) =>
      $scope.eventSources[0] = []
      addEvent(event) for event in events

    addEvent = (event) ->
      $scope.eventSources[0].push({
        title: event.name
        start: event.baseData.startDate
        end: event.baseData.endDate
        allDay: !event.baseData.endDate?
        event: event
      })
