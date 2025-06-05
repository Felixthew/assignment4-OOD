package controller;

import model.Calendar;
import view.CalendarView;

public interface CalendarCommand {
  void execute(Calendar calendar, CalendarView view);
}
