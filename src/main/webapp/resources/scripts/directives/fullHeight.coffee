angular.module('calendar').directive 'fullHeight', ($timeout) ->
  scope: no
  link: ($scope, element) ->
    setHeight = ->
      $timeout ->
        height = $(window).height() - 200
        element.css('height', height + 'px')

    $window = angular.element(window)
    $window.on('resize', setHeight())
    $scope.$on('$destroy', -> $window.off('resize', setHeight()))
    setHeight()