Instructions: run ExecutableCalendar's main method in the controller class with the command line
arguments you please.

Liberties we took:

- When creating an event series the day of the week of the first event is automatically a repeat
  day.
- When creating an event series if the event specification have the event spanning multiple days, a
  singular event will be created with the specifications.
- If you try to edit the start/end time of an event that is part of a series, an exception will
  be thrown.
- If you try to edit the start/end time of any event and the start time ends up after the end time,
  they will be swapped.
- The format of any event when printed is all lowercase.
- When using print events to find events in a timeframe, whether or not an event is included depends
  on the start time.

What each member has done:

Felix:

- Most model tests
- Headless test
- All View
- Model:
    - CalendarApp
    - EventSeriesImp
- Controller:
    - CalendarControllerImpl
    - AbstractCalendarCommandImpl
    - CreateEvent
    - PrintEvents
    - ShowStatus

Lyanne:

- View Tests
- Some model tests
- Controller Tests (Interactive mode)
- Model:
    - EventImp
- Controller:
    - Edit Event
    - Edit Events
    - Edit Series

Together:

- The interfaces
- Advising each other on implementation.
- Res folder
- Miscellaneous small edits