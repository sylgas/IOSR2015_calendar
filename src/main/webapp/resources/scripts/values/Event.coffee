Event =
  PANEL_TOGGLE: 'panelToggle'
  EVENT_SAVED: 'eventSaved'
  EVENT_SELECTION: 'eventSelection'
  POINTER_POSITION_CHANGED: 'pointerPositionChanged'
  DURATION_CHANGED: 'durationChanged'
  COLOR_CHANGE: 'colorChange'
  FORM_COLLAPSED: 'formCollapsed'

angular.module('calendar').value('Event', Object.freeze(Event))
