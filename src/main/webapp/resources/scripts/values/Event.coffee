Event =
  POINTER_POSITION_CHANGED: 'pointerPositionChanged'
  FORM_COLLAPSED: 'formCollapsed'

angular.module('calendar').value('Event', Object.freeze(Event))
