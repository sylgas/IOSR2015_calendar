angular.module('calendar').directive 'fullHeight', ($timeout) ->
  scope: no
  link: ($scope, element) ->
    setHeight = ->
      height = $(window).height() - $('#breadcrumb').height() - $('#navigation').height() - 50
      element.css('min-height', height + 'px')
      if $scope.map
        $timeout ->
          google.maps.event.trigger($scope.map, 'resize')

    $window = angular.element(window)
    $window.on('resize', setHeight)
    $scope.$on('$destroy', -> $window.off('resize', setHeight))
    setHeight()