import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of EuropeanSolitaireModel.
 */
public class TriangleSolitaireModelTest {
  //This needs to be changed
  MarbleSolitaireModel model1;
  MarbleSolitaireModel model2;
  MarbleSolitaireModel model3;
  MarbleSolitaireModel model4;
  //MarbleSolitaireModel modelWithArmThickOnlyOne;

  @Before
  public void setUp() {
    model1 = new EuropeanSolitaireModel();
    model2 = new EuropeanSolitaireModel(1, 1);
    model3 = new EuropeanSolitaireModel(5);
    model4 = new EuropeanSolitaireModel(7, 8, 8);
    //modelWithArmThickOnlyOne = new EnglishSolitaireModel(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidEmptySlotSelection() {
    model2 = new EuropeanSolitaireModel(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNegativeEmptySlotSelection() {
    model2 = new EuropeanSolitaireModel(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoInvalidEmptySlotSelection() {
    model4 = new EuropeanSolitaireModel(7, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void armThicknessCannotBeNegative() {
    model3 = new EuropeanSolitaireModel(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void armThicknessCannotBeEven() {
    model3 = new EuropeanSolitaireModel(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoArmThicknessCannotBeNegative() {
    model4 = new EuropeanSolitaireModel(-11, 8, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoArmThicknessCannotBeNegative2() {
    model4 = new EuropeanSolitaireModel(-11, 8, -8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoArmThicknessCannotBeEven() {
    model4 = new EuropeanSolitaireModel(8, 8, 8);
  }

  @Test
  public void defaultConstructorInitializesBoardCorrectly() {
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(3,3));
    for (int r = 0; r < this.model1.getBoardSize(); r++) {
      for (int c = 0; c < this.model1.getBoardSize(); c++) {
        if ((r < 2 || r > 4) && (c < 2 || c > 4)) {
          if (r == 1 && c == 1) {
            assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r,c));
          } else if (r == 1 && c == 5) {
            assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r,c));
          } else if (r == 5 && c == 1) {
            assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r,c));
          } else if (r == 5 && c == 5) {
            assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r,c));
          } else {
            assertEquals(MarbleSolitaireModelState.SlotState.Invalid, this.model1.getSlotAt(r,c));
          }
        } else {
          if (r != 3 || c != 3) {
            assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r,c));
          }
          if (r == 3 && c == 3) {
            assertEquals(MarbleSolitaireModelState.SlotState.Empty, this.model1.getSlotAt(r,c));
          }
        }
      }
    }
  }

  @Test
  public void getScore() {
    this.setUp();
    assertEquals(36, model1.getScore());
    assertEquals(36, model2.getScore());
    assertEquals(128, model3.getScore());
    assertEquals(276, model4.getScore());
    //assertEquals(4, modelWithArmThickOnlyOne.getScore());
  }

  @Test
  public void move() {
    this.setUp();
    assertEquals(36, model1.getScore());
    Assert.assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(3, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(3, 3));
    this.model1.move(3, 1, 3, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(3, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(3, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(3, 3));
    assertEquals(35, model1.getScore());
    this.model1.move(3, 4, 3, 2);
    assertEquals(34, model1.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidFromOutOfBoundsMove() {
    this.setUp();
    assertEquals(36, model1.getScore());
    this.model1.move(8, 1, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDestinationIsMarbleMove() {
    this.setUp();
    assertEquals(36, model1.getScore());
    this.model1.move(6, 3, 4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidJumpOverEmptyMove() {
    this.setUp();
    this.model1.move(5,3,3,3);
    assertEquals(35, model1.getScore());
    this.model1.move(6, 3, 4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDiagonalJump() {
    this.setUp();
    this.model1.move(5,3,3,3);
    assertEquals(35, model1.getScore());
    this.model1.move(2,3,4,3);
    assertEquals(34, model1.getScore());
    //Creating an empty slot for attempted diagonal jump
    this.model1.move(3,5,3,3);
    assertEquals(33, model1.getScore());
    this.model1.move(5, 2, 3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTooFarJump() {
    this.setUp();
    this.model1.move(5,3,3,3);
    assertEquals(35, model1.getScore());
    this.model1.move(2,3,5,3);
  }

  @Test
  public void isGameOver() {
    this.setUp();
    assertEquals(false, model1.isGameOver());
    this.model1.move(3, 1, 3, 3);
    assertEquals(false, model1.isGameOver());
    this.model1.move(3, 4, 3, 2);
    assertEquals(false, model1.isGameOver());
    //assertEquals(true, modelWithArmThickOnlyOne.isGameOver());
  }

  @Test
  public void getBoardSize() {
    this.setUp();
    assertEquals(7, model1.getBoardSize());
    assertEquals(7, model2.getBoardSize());
    assertEquals(13, model3.getBoardSize());
    assertEquals(19, model4.getBoardSize());
  }

  @Test
  public void getSlotAt() {
    this.setUp();
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model1.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(0, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model4.getSlotAt(8, 8));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model4.getSlotAt(7, 7));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model4.getSlotAt(17, 17));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getSlotAtIllegalPosition() {
    this.setUp();
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model1.getSlotAt(-1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getSlotAtIllegalPosition2() {
    this.setUp();
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(7, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getSlotAtIllegalPosition3() {
    this.setUp();
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(3, 8));
  }

}