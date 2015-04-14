MainController = ($scope) ->
  $scope.hello = "Hello!"

angular.module("calendar", [])
.controller("MainController", MainController)
