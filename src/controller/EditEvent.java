package controller;

import java.util.ArrayList;

import model.Calendar;

public class EditEvent implements CalendarCommand {
  String specifications;

  public EditEvent(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {
    // edit event <property> <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> with <NewPropertyValue>
    String[] specs = this.specifications.split(" ");

    String property = specs[0];

    ArrayList<String> identifiers = new ArrayList<>();
    identifiers.add(specs[1]);
    identifiers.add(specs[3]);
    identifiers.add(specs[5]);

    String newProperty = specs[7];

    calendar.findEvent(identifiers).edit(property, newProperty);
  }
}
