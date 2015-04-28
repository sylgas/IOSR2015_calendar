angular.module('calendar').controller 'EventController', ($scope, $modalInstance, event) ->
  new class
    constructor: ->
      $scope.event = event
      $scope.save = @save
      $scope.cancel = @cancel

    save: ->
      $modalInstance.close($scope.event)

    cancel: ->
      $modalInstance.dismiss();