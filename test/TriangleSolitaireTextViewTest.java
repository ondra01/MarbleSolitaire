import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of MarbleSolitaireTextView.
 */
public class TriangleSolitaireTextViewTest {
  //Needs to change
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

  //HW3 Code:
  MarbleSolitaireModel triangleModel1;
  MarbleSolitaireView triangleView1;
  MarbleSolitaireModel triangleModel3;
  MarbleSolitaireView triangleView3;

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

    //HW3 Code:
    triangleModel1 = new TriangleSolitaireModel();
    triangleView1 = new TriangleSolitaireTextView(triangleModel1);
    triangleModel3 = new TriangleSolitaireModel(9);
    triangleView3 = new TriangleSolitaireTextView(triangleModel3);
  }

  @Test
  public void testEuropeanInitialToString() {
    assertEquals("    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O", triangleView1.toString());
  }

  @Test
  public void testEuropeanWithArmThickness5InitialToString() {
    assertEquals("        _\n" +
            "       O O\n" +
            "      O O O\n" +
            "     O O O O\n" +
            "    O O O O O\n" +
            "   O O O O O O\n" +
            "  O O O O O O O\n" +
            " O O O O O O O O\n" +
            "O O O O O O O O O", triangleView3.toString());
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