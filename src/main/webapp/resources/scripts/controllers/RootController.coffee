angular.module('calendar').controller 'RootController', ($scope, EventService) ->

  $scope.panel = {}
  $scope.events = []

  clearEventForm = ->
    $scope.form =
      event: {}

  $scope.collapseForm = ->
    $scope.expandEventForm = false
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

  EventService.getAll().then (events) ->
    $scope.events = events

  $scope.saveEvent = saveEvent
  $scope.openEditForm = openEditFrom
  clearEventForm()



