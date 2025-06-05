package controller;

import java.time.LocalDateTime;

import model.Calendar;
import view.CalendarView;

public class ShowStatus extends CalendarCommandImpl {

  public ShowStatus(String specifications) {
    super(specifications);
  }

  @Override
  public void execute(Calendar calendar, CalendarView view) {
    String[] s = specifications.split(" ");
    String status = calendar.showStatus(LocalDateTime.parse(s[1]));
    view.displayMessage("Status: " + status);
  }
}
