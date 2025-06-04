package view;

import java.util.jar.JarOutputStream;

import model.Calendar;
import model.Event;

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
}
