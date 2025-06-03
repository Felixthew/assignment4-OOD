package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import model.Calendar;
import model.CalendarApp;

public class CreateEventSeries implements CalendarCommand {
  String[] specifications;

  public CreateEventSeries(String[] specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {
    String[] seriesSpecificationsRawString =
            String.join(" ", specifications).split(" repeats ");
    String[] seriesSpecifications =
            seriesSpecificationsRawString[seriesSpecificationsRawString.length - 1].split(" ");
    String[] eventSpecifications =
            seriesSpecificationsRawString[0].split(" ");
    // make a bunch of events and add them to an Even Series object
    if (seriesSpecifications.length < 1) {
      throw new IllegalArgumentException("Not enough specifications");
    }
    if (seriesSpecifications[1].equals("for")) {

    } else if (seriesSpecifications[1].equals("until")) {
      LocalDate endDate = CalendarApp.parseDate(seriesSpecifications[2]);
      while (endDate > )
    }
  }
}
