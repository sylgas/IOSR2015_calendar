Event =
  PANEL_TOGGLE: 'panelToggle'
  EVENT_SAVED: 'eventSaved'
  EVENT_SELECTION: 'eventSelection'
  POINTER_POSITION_CHANGED: 'pointerPositionChanged'
  POSITION_CHANGED: 'positionChanged'
  DURATION_CHANGED: 'durationChanged'
  COLOR_CHANGE: 'colorChange'
  FORM_COLLAPSED: 'formCollapsed'
  FORM_EXPANDED: 'formExpanded'

angular.module('calendar').value('Event', Object.freeze(Event))
