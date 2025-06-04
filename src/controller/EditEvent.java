package controller;

import java.util.ArrayList;

import model.Calendar;

import static controller.CalendarControllerImpl.extractAndRemoveSubject;

public class EditEvent implements CalendarCommand {
  String specifications;

  public EditEvent(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {
    // <property> <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> with <NewPropertyValue>
    String property = this.specifications.split(" ")[0];

    String specifications = this.specifications.substring(property.length() + 1);
    String[] specs = specifications.split(" ");

    ArrayList<String> identifiers = new ArrayList<>();
    identifiers.add(specs[1]);
    identifiers.add(specs[3]);
    identifiers.add(specs[5]);

    String newProperty = specs[7];

    calendar.findEvent(identifiers).edit(property, newProperty);
  }
}
