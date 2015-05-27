ResponseStatus =
  ATTENDING:
    label: 'ATTENDING'
    description: 'Going'
    icon: 'fa-thumbs-up'
    color: 'green'
  DECLINED:
    label: 'DECLINED'
    description: 'Not going'
    icon: 'fa-thumbs-down'
    color: 'red'
  MAYBE:
    label: 'MAYBE'
    description: 'Maybe'
    icon: 'fa-question-circle'
  NOT_REPLIED:
    label: 'NOT_REPLIED'


angular.module('calendar').value('ResponseStatus', Object.freeze(ResponseStatus))
