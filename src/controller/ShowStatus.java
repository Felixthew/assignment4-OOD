package controller;

import model.Calendar;

public class ShowStatus implements CalendarCommand {
  private String specifications;

  public ShowStatus(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {

  }
}
