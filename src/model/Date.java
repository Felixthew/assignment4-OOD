package model;

public class Date {
  private int day;
  private int month;
  private int year;
  private int hour;
  private int minute;

  /**
   * Constructs a {@code Date} object.
   *
   * @param day   the day of the date
   * @param month the month of the date
   * @param year  the year of the date
   * @throws IllegalArgumentException if any of the values are invalid
   */
  public Date(int day, int month, int year, int hour, int minute) throws IllegalArgumentException {
    // test for valid year
    if (year < 0) {
      throw new IllegalArgumentException("Year cannot be a negative number");
    }

    this.year = year;

    // test for valid month
    if (month < 1 || month > 12) {
      throw new IllegalArgumentException("Month is out of range");
    }

    // test for valid day
    switch (month) {
      // jan, march, may, july, aug, oct, dec: 31 days
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        if (day < 1 || day > 31) {
          throw new IllegalArgumentException("Day is out of range");
        }
        this.month = month;
        this.day = day;
        break;

      // feb: 28 or 29 days
      case 2:
        // if leap year
        if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))) {
          if (day < 1 || day > 29) {
            throw new IllegalArgumentException("Day is out of range");
          }
        }
        // if regular year
        else {
          if (day < 1 || day > 28) {
            throw new IllegalArgumentException("Day is out of range");
          }
        }
        this.month = month;
        this.day = day;
        break;

      // april, june, september, november: 30 days
      case 4:
      case 6:
      case 9:
      case 11:
        if (day < 1 || day > 30) {
          throw new IllegalArgumentException("Day is out of range");
        }
        this.month = month;
        this.day = day;
        break;

      default:
        // does nothing, because months can only be integers from 1-12
        // we already check for out of range months above
    }
  }

  /**
   * Advances the current date by the given amount of days (positive or negative).
   *
   * @param days days to advance by
   */
  public void advance(int days) {
    this.day += days;
    this.checkDays();
  }

  // checks the number of days to make sure it is valid
  // changes the day, month, and year accordingly
  private void checkDays() {

    if (this.day < 1) {
      this.month = this.month - 1;
      this.checkMonth();
      this.day = this.daysInMonth() + this.day;
      this.checkDays(); // recursively call until days is a valid number
    }

    if (this.day > this.daysInMonth()) {
      this.day = this.day - this.daysInMonth();
      this.month = this.month + 1;
      this.checkMonth();
      this.checkDays(); // recursively call until days is a valid number
    }

    this.checkYear();
  }

  // checks the month number to make sure it is valid
  // changes the month, and year accordingly
  private void checkMonth() {
    if (this.month == 0) {
      this.month = 12;
      this.year = this.year - 1;
    }

    if (this.month == 13) {
      this.month = 1;
      this.year = this.year + 1;
    }
  }

  // checks the year number to make sure it is valid
  // cap at Jan 1 0000
  private void checkYear() {
    if (this.year < 0) {
      this.day = 1;
      this.month = 1;
      this.year = 0;
    }
  }

  // returns the number of days in the current month
  private int daysInMonth() {
    switch (this.month) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        return 31;
      case 2:
        // if leap year
        if (year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))) {
          return 29;
        }
        // if regular year
        else {
          return 28;
        }
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      default:
        return 0;
    }
  }

  /**
   * Turns the current date into a string in the format YYYY-MM-DD.
   *
   * @return the current date as a string
   */
  public String toString() {
    String dateAsString = "%04d-%02d-%02d";
    String result = String.format(dateAsString, this.year, this.month, this.day);
    return result;
  }
}
