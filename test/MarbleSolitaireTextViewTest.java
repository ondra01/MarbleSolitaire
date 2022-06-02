import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of MarbleSolitaireTextView.
 */
public class MarbleSolitaireTextViewTest {
  //Models
  MarbleSolitaireModel model1;
  MarbleSolitaireModel model2;
  MarbleSolitaireModel model3;
  MarbleSolitaireModel model4;
  //MarbleSolitaireModel modelWithArmThickOnlyOne;

  MarbleSolitaireView view;
  MarbleSolitaireView view2;

  MarbleSolitaireView viewWithStringbuilderDestination;
  StringBuilder output;

  @Before
  public void setUp() {
    //Models
    model1 = new EnglishSolitaireModel();
    model2 = new EnglishSolitaireModel(2, 2);
    model3 = new EnglishSolitaireModel(5);
    model4 = new EnglishSolitaireModel(7, 8, 8);
    //modelWithArmThickOnlyOne = new EnglishSolitaireModel(1);

    //StringBuilder output
    output = new StringBuilder();

    //View
    view = new MarbleSolitaireTextView(model1);
    viewWithStringbuilderDestination = new MarbleSolitaireTextView(model1, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void viewConstructorThrowsIllegalArgumentExceptionForNull() {
    view2 = new MarbleSolitaireTextView(null);
  }

  @Test
  public void testInitialToString() {
    assertEquals("    O O O\n" +
            "    O O O\n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "O O O O O O O\n" +
            "    O O O\n" +
            "    O O O", view.toString());
  }

  @Test
  public void testToStringAfterMove() {
    model1.move(5, 3, 3, 3);
    assertEquals("    O O O\n" +
            "    O O O\n" +
            "O O O O O O O\n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "    O _ O\n" +
            "    O O O", view.toString());
  }

  //New Tests From HW2

  @Test
  public void testRenderBoard() throws IOException {
    viewWithStringbuilderDestination.renderBoard();
    assertEquals("    O O O\n" +
            "    O O O\n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "O O O O O O O\n" +
            "    O O O\n" +
            "    O O O", output.toString());
    assertEquals(this.viewWithStringbuilderDestination.toString(), output.toString());
  }

  @Test
  public void testRenderMessage() throws IOException {
    viewWithStringbuilderDestination.renderMessage("TEST MESSAGE 123");
    assertEquals("TEST MESSAGE 123", output.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void viewInvalidNullAppendableConstructor() {
    viewWithStringbuilderDestination = new MarbleSolitaireTextView(model1, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void viewInvalid2ParameterNullConstructor() {
    viewWithStringbuilderDestination = new MarbleSolitaireTextView(null, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void viewAlsoInvalid2ParameterNullConstructor() {
    viewWithStringbuilderDestination = new MarbleSolitaireTextView(null, null);
  }

}