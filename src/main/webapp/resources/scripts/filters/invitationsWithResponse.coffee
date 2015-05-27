angular.module('calendar').filter 'userInvitationsWithResponse', ($rootScope) ->
  (invitations, response) ->
    filtered = []
    if $rootScope.AuthorizationService.user?
      for invitation in invitations
        for invited in invitation.invited
          if invited.user.id is $rootScope.AuthorizationService.user.id and invited.responseStatus is response
            filtered.push(invitation)
    filtered
