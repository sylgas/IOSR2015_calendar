angular.module('calendar').controller 'EventController', ($scope, $modalInstance, event) ->
  new class
    constructor: ->
      $scope.event = event
