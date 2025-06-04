package controller;

import model.Calendar;

public class EditSeries implements CalendarCommand {
  String specifications;

  public EditSeries(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {

  }
}
