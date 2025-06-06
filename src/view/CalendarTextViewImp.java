package view;

import model.Calendar;
import model.Event;

/**
 * Represents a text view for a calendar.
 */
public class CalendarTextViewImp implements CalendarView {

  @Override
  public void promptForInput() {
    System.out.println("Enter your input:");
  }

  @Override
  public void displayError(String message) {
    System.out.println("Error: " + message);
  }

  @Override
  public void displayCalendar(Calendar calendar) {
    System.out.println(calendar);
  }

  @Override
  public void displayEvent(Event event) {
    System.out.println("• " + event.toString());
  }

  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }


}
