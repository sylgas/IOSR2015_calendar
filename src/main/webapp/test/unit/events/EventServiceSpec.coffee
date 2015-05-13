describe "EventService", ->

  event1 =
    id: 1,
    startDate: 1431551460000
    endDate: 14315514600142

  event2 =
    id: 2,
    startDate: 1431551460000

  beforeEach ->
    module 'calendar'
    inject (@$httpBackend, @EventService, @$rootScope) ->
      @$httpBackend.whenGET("/event").respond([event1, event2])
      @$httpBackend.whenPOST("/event", event1).respond(event1)
      @$httpBackend.whenPOST("/event", event2).respond(event2)
      @$httpBackend.whenGET("/authorization/user").respond(true)
      @$httpBackend.expectGET("/authorization/user")

  it 'should get all events', ->
    @$httpBackend.expectGET("/event")
    @EventService.getAll().then (events) ->
      expect(events).toBeDefined()
      expect(events.length).toBe(2)
    @$httpBackend.flush()

  it 'should save event', ->
    @$httpBackend.expectPOST("/event", event1)
    @EventService.save(event1).then (savedEvent) ->
      expect(savedEvent).toBeDefined()
    @$httpBackend.flush()

  it 'should convert event date properly', ->
    @$httpBackend.expectPOST("/event", event1)
    @EventService.save(event1).then (savedEvent) ->
      expect(savedEvent.startDate.valueOf()).toBe(event1.startDate)
      expect(savedEvent.endDate.valueOf()).toBe(event1.endDate)
    @$httpBackend.flush()

  it 'should not convert empty event date', ->
    @$httpBackend.expectPOST("/event", event2)
    @EventService.save(event2).then (savedEvent) ->
      expect(savedEvent.startDate.valueOf()).toBe(event2.startDate)
      expect(savedEvent.endDate).toBeUndefined()
    @$httpBackend.flush()
