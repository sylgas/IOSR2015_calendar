angular.module('calendar', ['ui.router']).config ($urlRouterProvider, $stateProvider) ->

  $urlRouterProvider.when('', '/')
  $urlRouterProvider.otherwise('/home')

  $stateProvider
  .state 'home',
      url: '/'

