ResponseStatus =
  ACCEPTED:
    label: 'ACCEPTED'
    icon: 'fa-thumbs-up'
    color: 'green'
  DENIED:
    label: 'DENIED'
    icon: 'fa-thumbs-down'
    color: 'red'
  MAYBE:
    label: 'MAYBE'
    icon: 'fa-question-circle'
  NONE:
    label: 'NONE'


angular.module('calendar').value('ResponseStatus', Object.freeze(ResponseStatus))
