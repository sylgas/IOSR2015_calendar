angular.module('calendar').controller 'MapController', ($rootScope, $scope) ->
  new class
    constructor: ->
      $rootScope.title = "Map"
      $scope.mapOptions = {
        center: new google.maps.LatLng(35.784, -78.670),
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      };
