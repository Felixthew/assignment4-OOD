package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Implementation of a calendar event with time and properties.
 */
public class EventImp implements Event {
  // the event series this event is associated with, can be a series of 1
  private EventSeries series;

  private String subject;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String description;
  private String location;
  private String status;

  private EventImp(EventSeries series, String subject, LocalDateTime startDate,
                   LocalDateTime endDate,
                   String description, String location, String status) {
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    this.series = series;
    this.subject = subject;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.location = location;
    this.status = status;
  }

  /**
   * Method to utilize builder to build event.
   *
   * @return builder to build event.
   */
  public static EventBuilder getBuilder() {
    return new EventBuilder();
  }

  /**
   * EventBuilder class to help set fields of Event.
   */
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
      this.subject = "untitled";
      this.startDate = LocalDateTime.of(0, 1, 1, 0, 0);
      this.endDate = LocalDateTime.of(0, 1, 1, 0, 0);
      this.description = "";
      this.location = "";
      this.status = "";
    }

    /**
     * Sets the event series of this event is associated with to the given series.
     *
     * @param series event series this event belongs to
     * @return the accumulated builder
     */
    public EventBuilder series(EventSeries series) {
      this.series = series;
      return this;
    }

    /**
     * Sets the event subject of this event to the given string.
     *
     * @param subject subject of this event
     * @return the accumulated builder
     */
    public EventBuilder subject(String subject) {
      this.subject = subject;
      return this;
    }

    /**
     * Sets the start time and date of this event to the given LocalDateTime.
     *
     * @param startDateTime start time of this event
     * @return the accumulated builder
     */
    public EventBuilder startDateTime(LocalDateTime startDateTime) {
      this.startDate = startDateTime;
      return this;
    }

    /**
     * Sets the start and end time & date of this event to the default times (8:00 AM
     * start, 5:00 PM end) on the given day.
     *
     * @param startDate day of this event
     * @return the accumulated builder
     */
    public EventBuilder allDay(LocalDate startDate) {
      this.startDate = LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
              startDate.getDayOfMonth(), 8, 0);
      this.endDate = LocalDateTime.of(startDate.getYear(), startDate.getMonth(),
              startDate.getDayOfMonth(), 17, 0);
      return this;
    }

    /**
     * Sets the end time and date of this event to the given LocalDateTime.
     *
     * @param endDateTime end time of this event
     * @return the accumulated builder
     */
    public EventBuilder endDateTime(LocalDateTime endDateTime) {
      this.endDate = endDateTime;
      return this;
    }

    /**
     * Sets the description of this event to the given string.
     *
     * @param description description of this event
     * @return the accumulated builder
     */
    public EventBuilder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets the location of this event to the given string.
     *
     * @param location location of this event
     * @return the accumulated builder
     */
    public EventBuilder location(String location) {
      this.location = location;
      return this;
    }

    /**
     * Sets the status of this event to the given string.
     *
     * @param status status of this event
     * @return the accumulated builder
     */
    public EventBuilder status(String status) {
      this.status = status;
      return this;
    }

    /**
     * Returns the final EventImp with all the desired fields & sets up the event series.
     *
     * @return EventImp with all its fields set to the fields of this builder instance.
     */
    public EventImp build() {
      EventImp toReturn = new EventImp(this.series, this.subject, this.startDate, this.endDate,
              this.description, this.location, this.status);
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

  public LocalDateTime getEndDate() {
    return this.endDate;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public void setSeries(EventSeries series) {
    this.series = series;
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
        LocalDateTime newStartDate = LocalDateTime.of(year, month, day, hour, minute);

        // should not be able to make a series event span multiple days
        if (this.getEventSeries().isFullSeries()) {
          if (newStartDate.getYear() != this.startDate.getYear() ||
                  newStartDate.getDayOfYear() != this.startDate.getDayOfYear()) {
            throw new IllegalArgumentException("Event must take place all on the same day!");
          }
        }

        // if start date is after end date, swap the two
        if (newStartDate.isAfter(this.endDate)) {
          this.startDate = this.endDate;
          this.endDate = newStartDate;
        } else {
          this.startDate = newStartDate;
        }

        break;

      case "end":
        int year2 = Integer.parseInt(newPropertyValue.substring(0, 4));
        int month2 = Integer.parseInt(newPropertyValue.substring(5, 7));
        int day2 = Integer.parseInt(newPropertyValue.substring(8, 10));
        int hour2 = Integer.parseInt(newPropertyValue.substring(11, 13));
        int minute2 = Integer.parseInt(newPropertyValue.substring(14));
        LocalDateTime newEndDate = LocalDateTime.of(year2, month2, day2, hour2, minute2);

        // should not be able to make a series event span multiple days
        if (this.getEventSeries().isFullSeries()) {
          if (newEndDate.getYear() != this.endDate.getYear() ||
                  newEndDate.getDayOfYear() != this.endDate.getDayOfYear()) {
            throw new IllegalArgumentException("Event must take place all on the same day!");
          }
        }

        // if end date is before start date, swap the two
        if (newEndDate.isBefore(this.startDate)) {
          this.endDate = this.startDate;
          this.startDate = newEndDate;
        } else {
          this.endDate = newEndDate;
        }

        break;

      case "description":
        this.description = newPropertyValue;
        break;

      case "location":
        this.location = newPropertyValue;
        break;

      case "status":
        this.status = newPropertyValue;
        break;

      // do nothing if property is invalid?
      default:
        break;
    }
  }

  @Override
  public String toString() {
    String result = this.subject + ": starts " + this.startDate.toString() + ", ends " +
            this.endDate.toString();

    // if there is a specified location
    if (!this.location.equals("")) {
      result = result + ", location: " + this.location;
    }

    if (!this.description.isEmpty()) {
      result += ", description: " + this.description;
    }

    if (!this.status.isEmpty()) {
      result += ", status: " + this.status;
    }

    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof EventImp) {
      EventImp other = (EventImp) obj;

      return other.getSubject().equals(this.subject) && other.getStartDate().equals(this.startDate)
              && other.getEndDate().equals(this.endDate);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.subject, this.startDate, this.endDate);
  }
}
