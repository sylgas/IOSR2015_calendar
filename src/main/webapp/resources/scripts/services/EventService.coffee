angular.module('calendar').service 'EventService', (Restangular) ->
  Events = Restangular.service('event')

  new class

    getAll: ->
      Events.getList()

    save: (event) ->
      Events.post(event)