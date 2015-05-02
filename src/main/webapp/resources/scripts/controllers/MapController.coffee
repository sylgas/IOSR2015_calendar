angular.module('calendar').controller 'MapController', ($rootScope, $scope) ->
  $rootScope.title = "Map"
  $scope.mapOptions = {
    center: new google.maps.LatLng(50.061389, 19.938333),
    zoom: 15,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  }
