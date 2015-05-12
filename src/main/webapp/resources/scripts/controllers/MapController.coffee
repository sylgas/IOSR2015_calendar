angular.module('calendar').controller 'MapController', ($rootScope, $scope, Event) ->
  $rootScope.title = "Map"

  $scope.markers = []

  hidePointer = ->
    $scope.pointer.setMap(null)

  updatePointer = (position) ->
    if $scope.pointer
      hidePointer()
    $scope.pointer = new google.maps.Marker(
      map: $scope.map
      position: position
    )

  $scope.pointTo = (event, params) ->
    if params && $scope.expandEventForm
      latLng = params[0].latLng
      position =
        lat: latLng.lat()
        lng: latLng.lng()
      updatePointer(position)
      $rootScope.$emit(Event.POINTER_POSITION_CHANGED, position)

  $scope.mapOptions =
    center: new google.maps.LatLng(50.061389, 19.938333)
    zoom: 15
    mapTypeId: google.maps.MapTypeId.ROADMAP

  $rootScope.$on(Event.POINTER_POSITION_CHANGED, (event, position) -> updatePointer(position))
  $rootScope.$on(Event.FORM_COLLAPSED, hidePointer)
