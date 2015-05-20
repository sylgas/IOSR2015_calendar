angular.module('calendar').controller 'MapController', ($rootScope, $scope, Event) ->
  $rootScope.title = "Map"

  $scope.markers = []

  buildMarker = (position, icon, event) ->
    new google.maps.Marker(
      map: $scope.map
      position: position
      icon: icon
      event: event
    )

  hide = (marker) ->
    if marker
      marker.setMap(null)

  updateMarker = (position) ->
    hide($scope.currentMarker)
    event = $scope.currentMarker.event
    $scope.currentMarker = buildMarker(position, buildIcon(event.color), event)

  updatePointer = (position) ->
    hide($scope.pointer)
    $scope.pointer = buildMarker(position)

  invalidateView = ->
    console.log "invalidating"
    hide(marker) for marker in $scope.markers
    $scope.markers = []
    addMarker(event) for event in $rootScope.events

  buildIcon = (color) ->
    new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + color.substring(1),
      new google.maps.Size(21, 34),
      new google.maps.Point(0, 0),
      new google.maps.Point(10, 34)
    )

  addMarker = (event) ->
    if event.location
      position =
        lat: event.location.latitude
        lng: event.location.longitude

      $scope.markers.push buildMarker(position, buildIcon(event.color), event)

  updatePosition = (position) ->
    if $scope.currentMarker
      console.log "current"
      updateMarker(position)
    else updatePointer(position)

  $scope.markerClicked = (marker) ->
    $rootScope.$emit(Event.EVENT_SELECTION, marker.event)

  $scope.pointTo = (event, params) ->
    if params && $scope.expandEventForm
      latLng = params[0].latLng
      position =
        lat: latLng.lat()
        lng: latLng.lng()

      updatePosition(position)
      $rootScope.$emit(Event.POINTER_POSITION_CHANGED, position)

  $scope.mapOptions =
    center: new google.maps.LatLng(50.061389, 19.938333)
    zoom: 15
    mapTypeId: google.maps.MapTypeId.ROADMAP

  $rootScope.$on(Event.POSITION_CHANGED, (e, position) -> updatePosition(position))
  $rootScope.$on(Event.FORM_COLLAPSED, ->
    delete $scope.currentMarker
    hide($scope.pointer)
  )
  $rootScope.$on(Event.FORM_EXPANDED, (e, event) ->
    marker = finded for finded in $scope.markers when finded.event is event
    $scope.currentMarker = marker
  )
  $rootScope.$on(Event.EVENT_SAVED, invalidateView)
  $scope.$watch('map', invalidateView)
