angular.module('calendar').service 'EventService', ($http) ->
  new class
    baseUrl = '/event/'

    constructor: ->

    getBetweenDate: (startDate, endDate) ->
      console.log(startDate)
      console.log(endDate)

      #create fake data
      date = new Date()
      d = date.getDate()
      m = date.getMonth()
      y = date.getFullYear()
      return [
        {
          name: 'event1'
          baseData: {
            startDate: new Date(y, m, d - 3, 16, 0)
            endDate: new Date(y, m, d - 3, 23, 0)
          }
        },
        {
          name: 'event2'
          baseData: {
            startDate: new Date(y, m, d, 12, 0)
            endDate: new Date(y, m, d, 18, 30)
          }
        },
        {
          name: 'event3'
          baseData: {
            startDate: new Date(y, m, d + 2, 20, 0)
            endDate: new Date(y, m, d + 3, 8, 0)
          }
        },
        {
          name: 'event4'
          baseData: {
            startDate: new Date(y, m, d + 1, 12, 0)
          }
        }
      ]