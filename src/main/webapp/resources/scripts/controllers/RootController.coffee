angular.module('calendar').controller 'RootController', ($scope, EventService) ->

  $scope.panel = {}
  $scope.events = []

  $scope.collapseForm = -> $scope.expandEventForm = false
  $scope.expandForm = -> $scope.expandEventForm = true

  EventService.getAll().then (events) ->
    $scope.events = events

  clearEventForm = ->
    $scope.form =
      event: {}

  closeEventForm = ->
    $scope.collapseForm()
    clearEventForm()

  saveEvent = (event) ->
    EventService.save(event).then (saved) ->
      if event.id
        event = saved
      else
        $scope.events.push saved
      closeEventForm()

  openEditFrom = (event) ->
    $scope.form.event = event
    $scope.expandForm()

  $scope.saveEvent = saveEvent
  $scope.openEditForm = openEditFrom
  clearEventForm()



