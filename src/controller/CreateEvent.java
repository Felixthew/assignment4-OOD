package controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Calendar;
import model.Event;
import model.EventImp;
import model.EventSeries;
import model.EventSeriesImp;
import view.CalendarView;

/**
 * The command responsible for creating an event or event series based on the user's
 * specifications. Command format should follow "create event (subject) (event specifications)"
 * or if creating a series, "create event (subject) (event specifications)
 * repeats (series specifications). Event specifications should be listed as keyword, value.
 * The keywords are location, from, to, on, status, and description. From and to dates
 * are date times required in the format YYYY-MM-DDThh:mm and if one is provided the other must
 * be provided as well. On is just a date in the format YYYY-MM-DD. Series specifications and
 * there values are, repeats (consecutive letters as seen below representing the days of the
 * week to repeat on, for (an int number of times to repeat) and until (date time to end at).
 */
public class CreateEvent extends AbstractCalendarCommandImpl {
  private static final Map<Character, DayOfWeek> dayOfWeekMap = new HashMap<>(Map.of(
          'm', DayOfWeek.MONDAY,
          't', DayOfWeek.TUESDAY,
          'w', DayOfWeek.WEDNESDAY,
          'r', DayOfWeek.THURSDAY,
          'f', DayOfWeek.FRIDAY,
          's', DayOfWeek.SATURDAY,
          'u', DayOfWeek.SUNDAY
  ));

  /**
   * Creates the command with the specifications.
   *
   * @param specifications the specifications for creating an event
   */
  public CreateEvent(String specifications) {
    super(specifications);
  }

  @Override
  public void execute(Calendar calendar, CalendarView view) {
    EventSeries eventSeries = new EventSeriesImp();
    Map<String, String> eventSpecs = new HashMap<>();
    Map<String, String> seriesSpecs = new HashMap<>();
    String subject = extractAndRemoveSubject();
    eventSpecs.put("subject", subject);

    List<String> eventSpecsList;
    List<String> seriesSpecsList;
    if (specifications.contains(" repeats ")) {
      eventSpecsList = Arrays.asList(specifications.split(" repeats ")[0].split(" "));
      seriesSpecsList = Arrays.asList(
              ("repeats " + specifications.split(" repeats ")[1]).split(" "));
    } else {
      eventSpecsList = Arrays.asList(specifications.split(" "));
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

  private void buildEventSeries(Calendar calendar, List<String> seriesSpecsList,
                                Map<String, String> seriesSpecs, Event startEvent,
                                EventSeries eventSeries, Map<String, String> eventSpecs) {
    if (startEvent.getStartDate().getDayOfMonth() != startEvent.getEndDate().getDayOfMonth()) {
      throw new IllegalArgumentException("Series events cannot span multiple days");
    }
    if (seriesSpecsList.isEmpty()) {
      throw new IllegalArgumentException("No series specifications found");
    }
    makeSpecifications(seriesSpecs, seriesSpecsList);

    LocalDateTime startDate = startEvent.getStartDate();
    Set<DayOfWeek> repeatDays = new HashSet<>();
    if (seriesSpecs.isEmpty()) {
      throw new IllegalArgumentException("No series specifications found");
    }
    repeatDays.add(startDate.getDayOfWeek());
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

  private void createSeriesEvent(Calendar calendar, EventSeries eventSeries,
                                 Map<String, String> eventSpecs, LocalDateTime currentDate) {
    if (eventSpecs.containsKey("from")) {
      eventSpecs.put("from", currentDate.toString());
      // put the end time on the current date
      String endTime = eventSpecs.get("to").split("[tT]")[1];
      eventSpecs.put("to", currentDate.toString().split("[tT]")[0] + "T" + endTime);
    } else if (eventSpecs.containsKey("on")) {
      // put the current date without the time
      eventSpecs.put("on", currentDate.toString().split("[tT]")[0]);
    } else {
      throw new IllegalArgumentException("No date specified");
    }
    Event newEvent = buildEvent(eventSpecs, eventSeries);
    calendar.add(newEvent);
  }

  private void makeSpecifications(Map<String, String> specMap, List<String> specList) {
    for (int i = 0; i < specList.size(); i += 2) {
      String specKey = specList.get(i);
      if (specKey.equals("times")) {
        return; // always at end of the command
      }
      if (i + 2 > specList.size()) {
        throw new IllegalArgumentException("Incomplete specification");
      }
      String specVal = specList.get(i + 1);
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
          eventBuilder.allDay(LocalDate.parse(value));
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
}
