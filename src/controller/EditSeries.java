package controller;

import java.util.HashMap;

import model.Calendar;
import model.Event;
import view.CalendarView;

public class EditSeries implements CalendarCommand {
  String specifications;

  public EditSeries(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar, CalendarView view) {
    // <property> <eventSubject> from <dateStringTtimeString> with <NewPropertyValue>
    HashMap<String, String> identifiers = new HashMap<>();

    String property = this.specifications.split(" ")[0];

    String specifications = this.specifications.substring(property.length() + 1);
    String[] specs = specifications.split(" ");

    identifiers.put("subject", specs[1]);
    identifiers.put("from", specs[3]);

    String newProperty = specs[5];

    Event event = calendar.findEvent(identifiers);

    event.getEventSeries().editAll(property, newProperty);
  }
}
