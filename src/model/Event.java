package model;

import java.time.LocalDateTime;

public interface Event {

  Event edit(String property, String newPropertyValue);

  LocalDateTime getStartDate();

}
