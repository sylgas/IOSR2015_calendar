angular.module('calendar').service 'WaitModalService', ($modal) ->
  new class
    show: ->
      @modalInstance = $modal.open
        templateUrl: 'resources/views/please-wait.html'
        size: 'md'
        backdrop: 'static'

    hide: ->
      @modalInstance.close()