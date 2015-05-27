angular.module('calendar').service 'EventService', (Restangular, $q, $rootScope) ->
  Events = Restangular.service('event')

  fromBackend = (event) ->
    event.startDate = new Date(event.startDate) if event.startDate
    event.endDate = new Date(event.endDate) if event.endDate
    event.color = '#000000' if not event.color
    event.invited = [] if not event.invited
    event

  toBackend = (event) ->
    owner = $rootScope.AuthorizationService.user
    event.owner = "#{owner.firstName} #{owner.lastName}"
    event

  new class
    getAll: ->
      promise = $q.defer()
      Events.getList().then (events) ->
        for event in events
          fromBackend(event)
        promise.resolve(events)
      promise.promise

    getByStatus: (status) ->
      promise = $q.defer()
      Events.one(status).getList().then (events) ->
        for event in events
          fromBackend(event)
        promise.resolve(events)
      promise.promise

    getAllFacebook: ->
      promise = $q.defer()
      Events.one('facebook').getList().then (events) ->
        for event in events
          fromBackend(event)
        promise.resolve(events)
      promise.promise

    changeResponseStatus: (eventId, response) ->
      Events.one(eventId).one(response).put()

    save: (event) ->
      promise = $q.defer()
      Events.post(toBackend(event)).then (saved) ->
        promise.resolve(fromBackend(saved))
      promise.promise

    remove: (event) ->
      Events.one(event.id).remove()