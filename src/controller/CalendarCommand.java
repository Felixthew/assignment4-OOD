package controller;

import model.Calendar;

public interface CalendarCommand {
  void execute(Calendar calendar);
}
