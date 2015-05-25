angular.module('calendar').service 'UserService', ($q, Restangular) ->
  new class
    search: (phrase, filter) ->
      promise = $q.defer()
      Restangular.one('user').one('search').all(phrase).getList().then (users) ->
        promise.resolve(filter(users))
      promise.promise
