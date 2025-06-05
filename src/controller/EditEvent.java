package controller;

import java.util.HashMap;

import model.Calendar;
import view.CalendarView;

public class EditEvent implements CalendarCommand {
  String specifications;

  public EditEvent(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar, CalendarView view) {
    // <property> <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> with <NewPropertyValue>
    HashMap<String, String> identifiers = new HashMap<>();

    String property = this.specifications.split(" ")[0];

    String specifications = this.specifications.substring(property.length() + 1);
    String[] specs = specifications.split(" ");

    identifiers.put("subject", specs[0]);
    identifiers.put("from", specs[2]);
    identifiers.put("to", specs[4]);

    String newProperty = specs[6];

    calendar.findEvent(identifiers).edit(property, newProperty);
  }
}
