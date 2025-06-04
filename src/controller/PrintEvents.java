package controller;

import model.Calendar;

public class PrintEvents implements CalendarCommand {
  private String specifications;

  public PrintEvents(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {

  }
}
