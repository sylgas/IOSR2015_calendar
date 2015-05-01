angular.module('calendar').controller 'RootController', ($scope) ->

  $scope.panel = {}
  $scope.events = []

  $scope.collapse = -> $scope.expandEventForm = false
  $scope.expand = -> $scope.expandEventForm = true

  clearEventForm = ->
    $scope.form =
      event: {}

  createEvent = (event) ->
    if $scope.events.indexOf(event) < 0
      $scope.events.push event
    $scope.collapse()
    clearEventForm()

  editEvent = (event) ->
    $scope.form.event = event
    $scope.expand()

  $scope.createEvent = createEvent
  $scope.editEvent = editEvent
  clearEventForm()



