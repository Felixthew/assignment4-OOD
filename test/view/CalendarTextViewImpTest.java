package view;

import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class CalendarTextViewImpTest {

  private CalendarTextViewImp view;
  private ByteArrayOutputStream outContent;

  @Before
  public void setUp() {
    view = new CalendarTextViewImp();
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testPrompt() {
    view.promptForInput();

    assertTrue(outContent.toString().contains("Enter your input:"));
  }

}