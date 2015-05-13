describe "Authorization", ->

  user =
    id: 1

  beforeEach ->
    module 'calendar'
    inject (@$httpBackend, @AuthorizationService, @$rootScope) ->
      @$httpBackend.whenGET("/authorization/user").respond(user)

  it 'should set authorized user', ->
    expect(@AuthorizationService.user).toBeUndefined()
    @$httpBackend.expectGET("/authorization/user")
    @$httpBackend.flush()
    expect(@AuthorizationService.user).toBeDefined()
    expect(@AuthorizationService.user.id).toBe(1)

  it 'should delete user after logout', ->
    @$httpBackend.expectGET("/authorization/user")
    @$httpBackend.flush()
    expect(@AuthorizationService.user).toBeDefined()
    @AuthorizationService.logout()
    expect(@AuthorizationService.user).toBeUndefined()
