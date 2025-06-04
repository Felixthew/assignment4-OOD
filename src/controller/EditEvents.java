package controller;

import java.util.ArrayList;
import java.util.List;

import model.Calendar;
import model.Event;

public class EditEvents implements CalendarCommand {
  String specifications;

  public EditEvents(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {
    // <property> <eventSubject> from <dateStringTtimeString> with <NewPropertyValue>
    String property = this.specifications.split(" ")[0];

    String specifications = this.specifications.substring(property.length() + 1);
    String[] specs = specifications.split(" ");

    ArrayList<String> identifiers = new ArrayList<>();
    identifiers.add(specs[1]);
    identifiers.add(specs[3]);

    String newProperty = specs[5];

    List<Event> events = calendar.findEvents(identifiers);

    for (Event event : events) {
      event.edit(property, newProperty);
    }

  }
}
