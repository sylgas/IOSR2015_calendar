angular.module('calendar').controller 'RootController', ($rootScope, $scope, $modal, EventService, Event, UserService, ResponseStatus, WaitModalService) ->
  $scope.authorized = $rootScope.AuthorizationService.user ?
    $scope.panel = {}
  $rootScope.ResponseStatus = ResponseStatus
  $rootScope.events = []

  getPosition = (event) ->
    lat: event.location.latitude
    lng: event.location.longitude

  clearEventForm = ->
    $scope.form =
      event:
        color: '#000000'
        invited: []

  eventCache = null
  $scope.cancelForm = ->
    index = $rootScope.events.indexOf($scope.form.event)
    if index != -1
      $rootScope.events[index] = angular.copy(eventCache)
    eventCache = null
    $scope.collapseForm()

  $scope.collapseForm = ->
    $scope.expandEventForm = false
    $rootScope.$emit(Event.FORM_COLLAPSED)
    clearEventForm()

  $scope.expandForm = ->
    wasHide = !$scope.panel.show;
    $scope.panel.show = true
    $scope.expandEventForm = true
    event = $scope.form.event if $scope.form.event.id
    $rootScope.$emit(Event.FORM_EXPANDED, event)
    if wasHide
      $rootScope.$emit(Event.PANEL_TOGGLE)

  $scope.togglePanel = ->
    $scope.panel.show = !$scope.panel.show
    $rootScope.$emit(Event.PANEL_TOGGLE)

  saveEvent = (event) ->
    EventService.save(event).then (saved) ->
      if event.id
        event = saved
      else
        $rootScope.events.push saved
      $rootScope.$emit(Event.EVENT_SAVED)
      $scope.collapseForm()

  openEditFrom = (event) ->
    if eventCache == null
      eventCache = angular.copy(event)
    $scope.form.event = event
    $scope.expandForm()

  updatePosition = ->
    position = getPosition($scope.form.event)
    if position.lat and position.lng
      $rootScope.$emit(Event.POSITION_CHANGED, position)

  updateDuration = ->
    $rootScope.$emit(Event.DURATION_CHANGED, $scope.form.event.startDate, $scope.form.event.endDate)

  setCoordinates = (position) ->
    $scope.form.event.location =
      longitude: parseFloat(position.lng.toFixed(7))
      latitude: parseFloat(position.lat.toFixed(7))

  setDuration = (startDate, endDate) ->
    $scope.expandForm()
    $scope.form.event.startDate = new Date(startDate)
    if (endDate != null)
      $scope.form.event.endDate = new Date(endDate)
    else
      $scope.form.event.endDate = null

  searchFilter = (users) ->
    users.filter (user) ->
      for invited in $scope.form.event.invited
        if user.id is invited.user.id
          return false
      true

  formatInputInvite = (invite) ->
    if not invite
      label = ""
    else
      label = invite.firstName + ' ' + invite.lastName
    label

  reloadEvents = (events) ->
    $rootScope.events = events
    events.forEach((event) ->
      for invitedUser in event.invited
        if invitedUser.user.id is $rootScope.AuthorizationService.user.id
          event.responseStatus = invitedUser.responseStatus
    )
    $rootScope.$emit(Event.EVENTS_LOAD)
    WaitModalService.hide()

  deleteEvent = (event) ->
    WaitModalService.show()
    EventService.remove(event).then ->
      EventService.getAll().then (events) ->
        $scope.collapseForm()
        reloadEvents(events)

  inviteUser = ->
    $scope.form.event.invited.push
      user: $scope.form.invited
    delete $scope.form.invited

  changeResponseStatus = (event, invitedUser, responseStatus) ->
    invitedUser.responseStatus = responseStatus
    event.responseStatus = responseStatus

  $scope.formatInputInvite = formatInputInvite
  $scope.saveEvent = saveEvent
  $scope.openEditForm = openEditFrom
  $scope.updatePosition = updatePosition
  $scope.updateDuration = updateDuration
  $scope.inviteUser = inviteUser
  $scope.deleteEvent = deleteEvent
  $scope.changeResponseStatus = changeResponseStatus
  $scope.ResponseStatus = ResponseStatus

  $scope.search = (phrase) ->
    UserService.search(phrase, searchFilter)

  $scope.updateColor = ->
    $rootScope.$emit(Event.COLOR_CHANGE)

  clearEventForm()
  WaitModalService.show()
  EventService.getAll().then (events) ->
    reloadEvents(events)

  $rootScope.getPosition = getPosition
  $rootScope.isFormExpanded = -> $scope.expandEventForm
  $rootScope.$on(Event.POINTER_POSITION_CHANGED, (event, position) -> setCoordinates(position))
  $rootScope.$on(Event.DURATION_CHANGED, (e, startDate, endDate) -> setDuration(startDate, endDate))
  $rootScope.$on(Event.EVENT_SELECTION, (e, event) -> openEditFrom(event))


