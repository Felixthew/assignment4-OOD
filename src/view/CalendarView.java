package view;

import model.Calendar;
import model.Event;

/**
 * Represents all the action of an interface for a calendar.
 */
public interface CalendarView {

  /**
   * Prompts the user for input.
   */
  void promptForInput();

  /**
   * Displays the provided error.
   *
   * @param message the error message to display
   */
  void displayError(String message);

  /**
   * Displays the contents of a calendar.
   *
   * @param calendar the calendar to display.
   */
  void displayCalendar(Calendar calendar);

  /**
   * Displays an event.
   *
   * @param event the event to display.
   */
  void displayEvent(Event event);

  /**
   * Displays a message.
   *
   * @param message the message to display.
   */
  void displayMessage(String message);

}
