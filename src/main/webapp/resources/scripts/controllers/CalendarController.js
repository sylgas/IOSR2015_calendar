(function() {
  var CalendarController;

  CalendarController = function($scope) {
    return $scope.text = "This page will contain calendar view";
  };

  angular.module('calendar').controller('CalendarController', CalendarController);

}).call(this);
