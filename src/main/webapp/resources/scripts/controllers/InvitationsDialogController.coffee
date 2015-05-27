angular.module('calendar').controller 'InvitationsDialogController',
  ($scope, $modalInstance, ResponseStatus, EventService, AuthorizationService) ->
    $scope.ResponseStatus = ResponseStatus

    $scope.changeResponseStatus = (event, responseStatus) ->
      EventService.changeResponseStatus(event.id, responseStatus).then ->
        for invitedUser in event.invited
          if invitedUser.user.id is AuthorizationService.user.id
            invitedUser.responseStatus = responseStatus
