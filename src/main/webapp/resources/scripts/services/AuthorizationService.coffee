angular.module('calendar').service 'AuthorizationService', (Restangular, $location) ->
  Authorization = Restangular.service("autorization")

  new class
    constructor: ->
      Authorization.one("user").get().then (user) =>
        @user = user

    logout: ->
      $location.path("#home")
# todo: FILL THIS
#if @user
#Restangular.one('logout').XXX()  .then ->
#delete @user




