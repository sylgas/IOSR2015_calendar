angular.module('calendar').service 'AuthorizationService', (Restangular) ->
  new class
    isAuthorized: ->
      return Restangular.one("user").get()
