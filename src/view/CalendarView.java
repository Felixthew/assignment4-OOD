package view;

import model.Calendar;
import model.Event;

public interface CalendarView {

  void promptForInput();

  void displayError(String message);

  void displayCalendar(Calendar calendar);

  void displayEvent(Event event);

  void displayMessage(String message);

}
