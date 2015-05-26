ResponseStatus =
  ATTENDING:
    label: 'ATTENDING'
    icon: 'fa-thumbs-up'
    color: 'green'
  DECLINED:
    label: 'DECLINED'
    icon: 'fa-thumbs-down'
    color: 'red'
  MAYBE:
    label: 'MAYBE'
    icon: 'fa-question-circle'
  NOT_REPLIED:
    label: 'NOT_REPLIED'


angular.module('calendar').value('ResponseStatus', Object.freeze(ResponseStatus))
