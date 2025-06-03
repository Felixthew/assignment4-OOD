package controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Calendar;
import model.CalendarApp;
import model.Event;
import model.EventImp;
import model.EventSeries;
import model.EventSeriesImp;

public class CreateEvent implements CalendarCommand {
  String specifications;

  private static final Map<Character, DayOfWeek> dayOfWeekMap = new HashMap<>(Map.of(
          'M', DayOfWeek.MONDAY,
          'T', DayOfWeek.TUESDAY,
          'W', DayOfWeek.WEDNESDAY,
          'R', DayOfWeek.THURSDAY,
          'F', DayOfWeek.FRIDAY,
          'S', DayOfWeek.SATURDAY,
          'U', DayOfWeek.SUNDAY
  ));

  public CreateEvent(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {
    Map<String, String> eventSpecs;
    Map<String, String> seriesSpecs;
    String subject;
    if (specifications.charAt(0) == '\"') {
      subject = specifications.substring(1).split("\" ")[0];
      specifications = specifications.substring(1).split("\" ")[1];
    } else {
      subject = specifications.split(" ")[0];
      specifications = specifications.replace("\"" + subject + "\" ", "");
    }
    if (specifications.contains(" repeats ")) {
      eventSpecs = specifications.split(" repeats ")[0].split(" ");
      seriesSpecs = specifications.split(" repeats ")[1].split(" ");
    } else {
      eventSpecs = specifications.split(" ");
      seriesSpecs = null;
    }
    // build the event
    Event startEvent = buildEvent(eventSpecs);
    calendar.add(startEvent);
    if (seriesSpecs != null) {
      EventSeries eventSeries = new EventSeriesImp();
      LocalDateTime startDate = startEvent.getStartDate();
      Set<DayOfWeek> repeatDays = new HashSet<>();
      if (seriesSpecs.length < 1) {
        throw new IllegalArgumentException("No series specifications found");
      }
      String repeatString = seriesSpecs[0];
      for (Character c : repeatString.toCharArray()) {
        repeatDays.add(dayOfWeekMap.get(c));
      }
      if (seriesSpecs[1].equals("for")) {
        int count = 0;
        int nTimes = Integer.parseInt(seriesSpecs[2]);
        LocalDateTime currentDate = startDate;
        while (count < nTimes) {
          currentDate = currentDate.plusDays(1);
          if (repeatDays.contains(currentDate.getDayOfWeek())) {
            buildEvent(eventSpecs);
          }
        }
      } else if (seriesSpecs[1].equals("until")) {

      }
    }
  }

  private static Event buildEvent(String[] eventSpecs) {
    EventImp.EventBuilder eventBuilder = EventImp.getBuilder();
    if (eventSpecs.length < 1) {
      throw new IllegalArgumentException("Expected specifications");
    }
    // subject must be first
    eventBuilder.subject(eventSpecs[0]);
    for (int i = 1; i < eventSpecs.length; i += 2) {
      if (i + 2 < eventSpecs.length) {
        throw new IllegalArgumentException("Incomplete specification");
      }
      String specification = eventSpecs[i];
      String value = eventSpecs[i + 1];
      switch (specification) {
        case "from":
          eventBuilder.startDateTime(LocalDateTime.parse(value));
          break;
        case "to":
          eventBuilder.endDateTime(LocalDateTime.parse(value));
          break;
        case "on":
          // need to make method
          // my guess is then the rest of the properties should follow this pattern
      }
    }
    return eventBuilder.build();
  }
}
