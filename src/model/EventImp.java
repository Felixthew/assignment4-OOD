package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

public class EventImp implements Event, Comparable<Event> {
  // the event series this event is associated with, can be a series of 1
  private EventSeries series;

  private String subject;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String description;
  private String location;
  private String status;

  private EventImp(EventSeries series, String subject, LocalDateTime startDate, LocalDateTime endDate,
                   String description, String location, String status) {
    this.series = series;
    this.subject = subject;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.location = location;
    this.status = status;
  }

  public static EventBuilder getBuilder() {
    return new EventBuilder();
  }

  public static class EventBuilder {
    private EventSeries series;
    private String subject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private String location;
    private String status;

    private EventBuilder() {
      this.series = new EventSeriesImp();
      this.subject = "";
      this.startDate = LocalDateTime.of(0, 0, 0, 0, 0);
      this.endDate = LocalDateTime.of(0, 0, 0, 0, 0);
      this.description = "";
      this.location = "";
      this.status = "";
    }

    public EventBuilder series(EventSeries series) {
      this.series = series;
      return this;
    }

    public EventBuilder subject(String subject) {
      this.subject = subject;
      return this;
    }

    public EventBuilder startDateTime(LocalDateTime startDateTime) {
      this.startDate = startDateTime;
      return this;
    }

    public EventBuilder startDate(LocalDate startDate) {
      this.startDate = LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
              startDate.getDayOfMonth(), 8, 0);
      return this;
    }

    public EventBuilder endDateTime(LocalDateTime endDateTime) {
      this.endDate = endDateTime;
      return this;
    }

    public EventBuilder endDate(LocalDate endDate) {
      this.startDate = LocalDateTime.of(endDate.getYear(), endDate.getMonth(),
              endDate.getDayOfMonth(), 17, 0);
      return this;
    }

    public EventBuilder description(String description) {
      this.description = description;
      return this;
    }

    public EventBuilder location(String location) {
      this.location = location;
      return this;
    }

    public EventBuilder status(String status) {
      this.status = status;
      return this;
    }

    public EventImp build() {
      EventImp toReturn = new EventImp(this.series, this.subject, this.startDate, this.endDate, this.description,
              this.location, this.status);
      this.series.add(toReturn);
      return toReturn;
    }
  }

  @Override
  public int compareTo(Event o) {
    return this.startDate.compareTo(o.getStartDate());
  }

  public LocalDateTime getStartDate() {
    return this.startDate;
  }

  public EventSeries getEventSeries() {
    return this.series;
  }

  @Override
  public void edit(String property, String newPropertyValue) {
    switch (property) {
      case "subject":
        this.subject = newPropertyValue;
        break;

      case "start":
        int year = Integer.parseInt(newPropertyValue.substring(0, 4));
        int month = Integer.parseInt(newPropertyValue.substring(5, 7));
        int day = Integer.parseInt(newPropertyValue.substring(8, 10));
        int hour = Integer.parseInt(newPropertyValue.substring(11, 13));
        int minute = Integer.parseInt(newPropertyValue.substring(14));
        this.startDate = LocalDateTime.of(year, month, day, hour, minute);

      case "end":
        int year2 = Integer.parseInt(newPropertyValue.substring(0, 4));
        int month2 = Integer.parseInt(newPropertyValue.substring(5, 7));
        int day2 = Integer.parseInt(newPropertyValue.substring(8, 10));
        int hour2 = Integer.parseInt(newPropertyValue.substring(11, 13));
        int minute2 = Integer.parseInt(newPropertyValue.substring(14));
        this.endDate = LocalDateTime.of(year2, month2, day2, hour2, minute2);

      case "description":
        this.description = newPropertyValue;

      case "location":
        this.location = newPropertyValue;

      case "status":
        this.status = newPropertyValue;

    }
  }

}
