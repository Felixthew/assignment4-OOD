package controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Calendar;
import model.Event;
import model.EventImp;
import model.EventSeries;
import model.EventSeriesImp;

import static controller.CalendarControllerImpl.extractAndRemoveSubject;

public class CreateEvent implements CalendarCommand {
  String specifications;

  private static final Map<Character, DayOfWeek> dayOfWeekMap = new HashMap<>(Map.of(
          'm', DayOfWeek.MONDAY,
          't', DayOfWeek.TUESDAY,
          'w', DayOfWeek.WEDNESDAY,
          'r', DayOfWeek.THURSDAY,
          'f', DayOfWeek.FRIDAY,
          's', DayOfWeek.SATURDAY,
          'u', DayOfWeek.SUNDAY
  ));

  public CreateEvent(String specifications) {
    this.specifications = specifications;
  }

  @Override
  public void execute(Calendar calendar) {
    EventSeries eventSeries = new EventSeriesImp();
    Map<String, String> eventSpecs = new HashMap<>();
    Map<String, String> seriesSpecs = new HashMap<>();
    String subject = extractAndRemoveSubject();
    eventSpecs.put("subject", subject);

    String[] eventSpecsList;
    String[] seriesSpecsList;
    if (specifications.contains(" repeats ")) {
      eventSpecsList = specifications.split(" repeats ")[0].split(" ");
      seriesSpecsList = ("repeats " + specifications.split(" repeats ")[1]).split(" ");
    } else {
      eventSpecsList = specifications.split(" ");
      seriesSpecsList = null;
    }

    makeSpecifications(eventSpecs, eventSpecsList);

    // build the event
    Event startEvent = buildEvent(eventSpecs, eventSeries);
    // all events will be in series even if that is just a series of one
    calendar.add(startEvent);

    if (seriesSpecsList != null) {

      buildEventSeries(calendar, seriesSpecsList, seriesSpecs, startEvent, eventSeries, eventSpecs);
    }
  }

  private void buildEventSeries(Calendar calendar, String[] seriesSpecsList, Map<String,
                                        String> seriesSpecs, Event startEvent, EventSeries eventSeries,
                                Map<String, String> eventSpecs) {
    if (seriesSpecsList.length < 1) {
      throw new IllegalArgumentException("No series specifications found");
    }
    makeSpecifications(seriesSpecs, seriesSpecsList);

    LocalDateTime startDate = startEvent.getStartDate();
    Set<DayOfWeek> repeatDays = new HashSet<>();
    if (seriesSpecs.isEmpty()) {
      throw new IllegalArgumentException("No series specifications found");
    }
    String repeatString = seriesSpecs.get("repeats");
    for (Character c : repeatString.toCharArray()) {
      repeatDays.add(dayOfWeekMap.get(c));
    }

    if (seriesSpecs.containsKey("for")) {
      int count = 0;
      int nTimes = Integer.parseInt(seriesSpecs.get("for"));
      LocalDateTime currentDate = startDate;
      while (count < nTimes) {
        currentDate = currentDate.plusDays(1);
        if (repeatDays.contains(currentDate.getDayOfWeek())) {
          createSeriesEvent(calendar, eventSeries, eventSpecs, currentDate);
          count++;
        }
      }
    } else if (seriesSpecs.containsKey("until")) {
      // add a day so that it can be an inclusive bound
      LocalDate endDate = LocalDate.parse(seriesSpecs.get("until")).plusDays(1);
      LocalDateTime endDateTime = LocalDateTime.of(
              endDate.getYear(),
              endDate.getMonthValue(),
              endDate.getDayOfMonth(),
              0,
              0
      );
      LocalDateTime currentDate = startDate;
      while (currentDate.isBefore(endDateTime)) {
        currentDate = currentDate.plusDays(1);
        if (repeatDays.contains(currentDate.getDayOfWeek())) {
          createSeriesEvent(calendar, eventSeries, eventSpecs, currentDate);
        }
      }
    }
  }

  private void createSeriesEvent(Calendar calendar, EventSeries eventSeries, Map<String,
          String> eventSpecs, LocalDateTime currentDate) {
    if (eventSpecs.containsKey("from")) {
      eventSpecs.put("from", currentDate.toString());
      // put the end time on the current date
      String endTime = eventSpecs.get("to").split("T")[1];
      eventSpecs.put("to", currentDate.toString().split("T")[0] + endTime);
    } else if (eventSpecs.containsKey("on")) {
      // put the current date without the time
      eventSpecs.put("on", currentDate.toString().split("T")[0]);
    } else {
      throw new IllegalArgumentException("No date specified");
    }
    Event newEvent = buildEvent(eventSpecs, eventSeries);
    calendar.add(newEvent);
  }

  private void makeSpecifications(Map<String, String> specMap, String[] specList) {
    for (int i = 0; i < specList.length; i += 2) {
      String specKey = specList[i];
      if (specKey.equals("times")) {
        return; // always at end of the command
      }
      if (i + 2 > specList.length) {
        throw new IllegalArgumentException("Incomplete specification");
      }
      String specVal = specList[i + 1];
      specMap.put(specKey, specVal);
    }
  }

  private static Event buildEvent(Map<String, String> eventSpecs, EventSeries eventSeries) {
    EventImp.EventBuilder eventBuilder = EventImp.getBuilder();
    eventBuilder.series(eventSeries);

    if (eventSpecs.isEmpty()) {
      throw new IllegalArgumentException("Expected specifications");
    }

    for (Map.Entry<String, String> stringStringEntry : eventSpecs.entrySet()) {
      String key = stringStringEntry.getKey();
      String value = stringStringEntry.getValue();
      switch (key) {
        case "subject":
          eventBuilder.subject(value);
          break;
        case "from":
          eventBuilder.startDateTime(LocalDateTime.parse(value));
          break;
        case "to":
          eventBuilder.endDateTime(LocalDateTime.parse(value));
          break;
        case "on":
          // default all day needs to set both start and end time
          eventBuilder.startDate(LocalDate.parse(value));
          break;
        case "location":
          eventBuilder.location(value);
          break;
        case "status":
          eventBuilder.status(value);
          break;
        case "description":
          eventBuilder.description(value);
          break;
        default:
          throw new IllegalArgumentException("Event specification not recognized");
      }
    }
    return eventBuilder.build();
  }

  public String extractAndRemoveSubject() {
    String subject;
    if (specifications.charAt(0) == '\"') {
      subject = specifications.substring(1).split("\" ")[0];
      specifications = specifications.substring(1).split("\" ")[1];
    } else {
      subject = specifications.split(" ")[0];
      specifications = specifications.replace(subject + " ", "");
    }
    return subject;
  }
}
