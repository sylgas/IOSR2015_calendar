angular.module('calendar').service 'AuthorizationService', (Restangular, $location) ->
  Authorization = Restangular.service("authorization")

  new class
    constructor: ->
      Authorization.one("user").get().then (user) =>
        @user = user

    logout: ->
      delete @user
