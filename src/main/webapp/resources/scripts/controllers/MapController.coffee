angular.module('calendar').controller 'MapController', ($rootScope, $scope, Event) ->
  $rootScope.title = "Map"

  $scope.markers = []

  buildMarker = (position, icon, event, index) ->
    new google.maps.Marker(
      map: $scope.map
      position: position
      icon: icon
      event: event
      eventIndex: index
    )

  hide = (marker) ->
    if marker
      marker.setMap(null)

  updateMarker = (position) ->
    hide($scope.currentMarker)

    event = $scope.currentMarker.event
    index = $scope.currentMarker.eventIndex
    $scope.markers.splice($scope.markers.indexOf($scope.currentMarker), 1)
    $scope.currentMarker = buildMarker(position, buildIcon(event.color), event, index)
    $scope.markers.push $scope.currentMarker

  updatePointer = (position) ->
    hide($scope.pointer)
    $scope.pointer = buildMarker(position)

  invalidateView = ->
    hide(marker) for marker in $scope.markers
    $scope.markers = []
    addMarker(event, index) for event, index in $rootScope.events

  buildIcon = (color) ->
    new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + color.substring(1),
      new google.maps.Size(21, 34),
      new google.maps.Point(0, 0),
      new google.maps.Point(10, 34)
    )

  addMarker = (event, index) ->
    if event.location
      position =
        lat: event.location.latitude
        lng: event.location.longitude

      marker = buildMarker(position, buildIcon(event.color), event, index)

      if $scope.form.event?.id and $scope.form.event.id == event.id
        $scope.currentMarker = marker

      $scope.markers.push marker

  updatePosition = (position) ->
    if $scope.currentMarker
      updateMarker(position)
    else updatePointer(position)

  $scope.markerClicked = (marker) ->
    if not $rootScope.isFormExpanded()
      event = $rootScope.events[marker.eventIndex]
      $rootScope.$emit(Event.EVENT_SELECTION, event)

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

  $rootScope.$on(Event.POSITION_CHANGED, (e, position) ->
    updatePosition(position)
  )
  $rootScope.$on(Event.FORM_COLLAPSED, ->
    if $scope.currentMarker
      event = $rootScope.events[$scope.currentMarker.eventIndex]
      updateMarker($rootScope.getPosition(event))
      delete $scope.currentMarker
    hide($scope.pointer)
  )
  $rootScope.$on(Event.FORM_EXPANDED, (e, event) ->
    if event
      marker = finded for finded in $scope.markers when finded.event.id is event.id
      $scope.currentMarker = marker
  )
  $rootScope.$on(Event.EVENT_SAVED, invalidateView)
  $rootScope.$on(Event.EVENTS_LOAD, invalidateView)
  $scope.$watch('map', invalidateView)
