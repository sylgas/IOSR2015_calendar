angular.module('calendar').controller 'RootController', ($rootScope, $scope, EventService, Event) ->

  $scope.panel = {}
  $scope.events = []

  clearEventForm = ->
    $scope.form =
      event:
        color: '#000000'

  $scope.collapseForm = ->
    $scope.expandEventForm = false
    $rootScope.$emit(Event.FORM_COLLAPSED)
    clearEventForm()

  $scope.expandForm = -> $scope.expandEventForm = true

  saveEvent = (event) ->
    EventService.save(event).then (saved) ->
      if event.id
        event = saved
      else
        $scope.events.push saved
      $scope.collapseForm()

  openEditFrom = (event) ->
    $scope.form.event = event
    $scope.expandForm()

  updatePosition = ->
    position =
      lat: $scope.form.event.location.longitude
      lng: $scope.form.event.location.latitude
    if position.lat and position.lng
      $rootScope.$emit(Event.POINTER_POSITION_CHANGED, position)

  setCoordinates = (position) ->
    $scope.form.event.location =
      longitude: parseFloat(position.lat.toFixed(7))
      latitude: parseFloat(position.lng.toFixed(7))

  EventService.getAll().then (events) ->
    $scope.events = events

  $scope.saveEvent = saveEvent
  $scope.openEditForm = openEditFrom
  $scope.updatePosition = updatePosition
  clearEventForm()
  $rootScope.$on(Event.POINTER_POSITION_CHANGED, (event, position) -> setCoordinates(position))



