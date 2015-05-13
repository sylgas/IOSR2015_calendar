angular.module('calendar').controller 'RootController', ($rootScope, $scope, EventService, Event) ->

  $scope.panel = {}
  $rootScope.events = []

  clearEventForm = ->
    $scope.form =
      event:
        color: '#000000'

  eventCache = null
  $scope.collapseForm = ->
    index = $rootScope.events.indexOf($scope.form.event)
    if index != -1
      $rootScope.events[index] = angular.copy(eventCache)
    eventCache = null

    $scope.expandEventForm = false
    $rootScope.$emit(Event.FORM_COLLAPSED)
    clearEventForm()

  $scope.expandForm = ->
    wasHide = !$scope.panel.show;
    $scope.panel.show = true
    $scope.expandEventForm = true
    if wasHide
      $rootScope.$emit(Event.PANEL_TOGGLE)

  $scope.togglePanel = ->
    $scope.panel.show = !$scope.panel.show
    $rootScope.$emit(Event.PANEL_TOGGLE)

  saveEvent = (event) ->
    EventService.save(event).then (saved) ->
      if event.id
        event = saved
      else
        $rootScope.events.push saved
        $rootScope.$emit(Event.EVENT_SAVED)
      $scope.collapseForm()

  openEditFrom = (event) ->
    if eventCache == null
      eventCache = angular.copy(event)
    $scope.form.event = event
    $scope.expandForm()

  updatePosition = ->
    position =
      lat: $scope.form.event.location.longitude
      lng: $scope.form.event.location.latitude
    if position.lat and position.lng
      $rootScope.$emit(Event.POINTER_POSITION_CHANGED, position)

  updateDuration = ->
    $rootScope.$emit(Event.DURATION_CHANGED, $scope.form.event.startDate, $scope.form.event.endDate)

  setCoordinates = (position) ->
    $scope.form.event.location =
      longitude: parseFloat(position.lat.toFixed(7))
      latitude: parseFloat(position.lng.toFixed(7))

  setDuration = (startDate, endDate) ->
    $scope.expandForm()
    $scope.form.event.startDate = new Date(startDate)
    if (endDate != null)
      $scope.form.event.endDate = new Date(endDate)
    else
      $scope.form.event.endDate = null

  EventService.getAll().then (events) ->
    $rootScope.events = events

  $scope.saveEvent = saveEvent
  $scope.openEditForm = openEditFrom
  $scope.updatePosition = updatePosition
  $scope.updateDuration = updateDuration
  $scope.updateColor = ->
    $rootScope.$emit(Event.COLOR_CHANGE)

  clearEventForm()

  $rootScope.$on(Event.POINTER_POSITION_CHANGED, (event, position) -> setCoordinates(position))
  $rootScope.$on(Event.DURATION_CHANGED, (e, startDate, endDate) -> setDuration(startDate, endDate))
  $rootScope.$on(Event.EVENT_SELECTION, (e, event) -> openEditFrom(event))


