angular.module('calendar').directive 'fullHeight', ->
  scope: no
  link: ($scope, element) ->
    setHeight = ->
      height = $(window).height() - $('#breadcrumb').height() - $('#navigation').height() - 50
      element.css('height', height + 'px')

    $window = angular.element(window)
    $window.on('resize', setHeight())
    $scope.$on('$destroy', -> $window.off('resize', setHeight()))
    setHeight()