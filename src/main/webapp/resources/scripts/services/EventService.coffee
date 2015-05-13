angular.module('calendar').service 'EventService', (Restangular, $q) ->
  Events = Restangular.service('event')

  fromBackend = (event) ->
    event.startDate = new Date(event.startDate) if event.startDate
    event.endDate = new Date(event.endDate) if event.endDate
    event

  new class
    getAll: ->
      promise = $q.defer()
      Events.getList().then (events) ->
        for event in events
          fromBackend(event)
        promise.resolve(events)
      promise.promise

    save: (event) ->
      promise = $q.defer()
      Events.post(event).then (saved) ->
        promise.resolve(fromBackend(saved))
      promise.promise

