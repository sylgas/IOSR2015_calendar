angular.module('calendar').controller 'InvitationsController', ($rootScope, $scope, $modal) ->
  if $rootScope.AuthorizationService.user?
    $rootScope.title = "Invitations"

  $scope.openInvitationsDialog = ->
    $modal.open
      templateUrl: 'resources/views/invitations.html'
      controller: 'InvitationsDialogController'
      size: 'md'
    .result
