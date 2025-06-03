package model;

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


  public EventImp() {

  }

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

    public EventBuilder startDate(LocalDateTime startDate) {
      this.startDate = startDate;
      return this;
    }

    public EventBuilder endDate(LocalDateTime endDate) {
      this.endDate = endDate;
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

    public Event build() {
      return new EventImp(this.series, this.subject, this.startDate, this.endDate, this.description,
              this.location, this.status);
    }
  }

  @Override
  public int compareTo(Event o) {
    return this.startDate.compareTo(o.getStartDate());
  }

  public LocalDateTime getStartDate() {
    return this.startDate;
  }

  @Override
  public Event edit(String property, String newPropertyValue) {
    return null;
  }

}
