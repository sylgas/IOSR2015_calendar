angular.module('calendar').service 'EventService', (Restangular, $q) ->
  Events = Restangular.service('event')

  fromBacked = (event) ->
    event.startDate = new Date(event.startDate) if event.startDate
    event.endDate = new Date(event.endDate) if event.endDate

  new class
    getAll: ->
      promise = $q.defer()
      Events.getList().then (events) ->
        for event in events
          fromBacked(event)
        promise.resolve(events)
      promise.promise

    save: (event) ->
      Events.post(event)