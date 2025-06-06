package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the fields and methods in common across many calendar commands.
 */
public abstract class AbstractCalendarCommandImpl implements CalendarCommand {
  String specifications;

  public AbstractCalendarCommandImpl(String specifications) {
    this.specifications = specifications;
  }

  /**
   * This is meant to handle a multi-word subject in "".
   *
   * @return the subject as a string.
   */
  protected String extractAndRemoveSubject() {
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
