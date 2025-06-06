package view;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class CalendarTextViewImpTest {

  CalendarTextViewImp view;

  @Before
  public void setUp() {
    view = new CalendarTextViewImp();
  }

  @Test
  public void testPrompt() {
    view.promptForInput();

    assertEquals("Enter your input:", System.out.toString());
  }

}