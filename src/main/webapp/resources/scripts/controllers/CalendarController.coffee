angular.module('calendar').controller 'CalendarController', ($rootScope, $scope, $route, Event) ->
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
          eventClick: (event) ->
            selectEvent(event.event)
          eventDrop: (event) ->
            changeEventDuration(event.event, event.start, event.end)
          eventResize: (event) ->
            changeEventDuration(event.event, event.start, event.end)
          select: (start, end) ->
            createEvent(start, end)
          viewRender: (view) ->
            displayEventsInDateRange(view.start, view.end)

    selectEvent = (event) ->
      $rootScope.$emit(Event.EVENT_SELECTION, event)
      $scope.myCalendar.fullCalendar('render')

    changeEventDuration = (event, startDate, endDate) ->
      selectEvent(event)
      $rootScope.$emit(Event.DURATION_CHANGED, startDate, endDate)

    createEvent = (startDate, endDate) ->
      if (endDate - startDate == 0)
        endDate = null
      selectEvent(
        startDate: startDate,
        endDate: endDate
      )

    displayEventsInDateRange = (start, end) ->
      displayEvents($rootScope.events)

    displayEvents = (events) ->
      $scope.eventSources[0] = []
      addEvent(event) for event in events

    addEvent = (event) ->
      $scope.eventSources[0].push({
        title: event.name
        start: event.startDate
        end: event.endDate
        allDay: !event.endDate?
        color: event.color
        event: event
      })

    refreshView = ->
      displayEvents($rootScope.events)
      $scope.myCalendar.fullCalendar('render')

    $rootScope.$on(Event.PANEL_TOGGLE, () ->
      setTimeout((() ->
          $scope.myCalendar.fullCalendar('render')),
        100)
    )
    $rootScope.$on(Event.DURATION_CHANGED, refreshView)
    $rootScope.$on(Event.COLOR_CHANGE, refreshView)
    $rootScope.$on(Event.EVENT_SAVED, refreshView)