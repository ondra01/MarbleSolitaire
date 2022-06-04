import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of EuropeanSolitaireModel.
 */
public class TriangleSolitaireModelTest {
  MarbleSolitaireModel model1;
  MarbleSolitaireModel model2;
  MarbleSolitaireModel modelBaseDimIs4;
  MarbleSolitaireModel model4;
  //MarbleSolitaireModel modelWithArmThickOnlyOne;

  @Before
  public void setUp() {
    model1 = new TriangleSolitaireModel();
    model2 = new TriangleSolitaireModel(3, 1);
    modelBaseDimIs4 = new TriangleSolitaireModel(4);
    model4 = new TriangleSolitaireModel(7, 6, 6);
    //modelWithArmThickOnlyOne = new EnglishSolitaireModel(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidEmptySlotSelection() {
    model2 = new TriangleSolitaireModel(0, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNegativeEmptySlotSelection() {
    model2 = new TriangleSolitaireModel(-1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoInvalidEmptySlotSelection() {
    model4 = new TriangleSolitaireModel(7, 8, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void dimensionsCannotBeNegative() {
    modelBaseDimIs4 = new TriangleSolitaireModel(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void dimensionsCannotBeZero() {
    modelBaseDimIs4 = new TriangleSolitaireModel(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoDimensionsCannotBeNegative() {
    model4 = new TriangleSolitaireModel(-11, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoDimensionsCannotBeNegative2() {
    model4 = new TriangleSolitaireModel(-11, 2, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void alsoDimensionsCannotBeZero() {
    model4 = new TriangleSolitaireModel(0, 3, 1);
  }

  @Test
  public void defaultConstructorInitializesBoardCorrectly() {
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(0, 0));
    for (int r = 0; r < this.model1.getBoardSize(); r++) {
      for (int c = 0; c < this.model1.getBoardSize(); c++) {
        if (r == 0 && c >= 1) {
          assertEquals(MarbleSolitaireModelState.SlotState.Invalid, this.model1.getSlotAt(r, c));
        }
        if (r == 1 && c < 2) {
          assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r, c));
        }
        if (r == 1 && c >= 2) {
          assertEquals(MarbleSolitaireModelState.SlotState.Invalid, this.model1.getSlotAt(r, c));
        }
        if (r == 2 && c < 3) {
          assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r, c));
        }
        if (r == 2 && c >= 3) {
          assertEquals(MarbleSolitaireModelState.SlotState.Invalid, this.model1.getSlotAt(r, c));
        }
        if (r == 3 && c < 4) {
          assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r, c));
        }
        if (r == 3 && c >= 4) {
          assertEquals(MarbleSolitaireModelState.SlotState.Invalid, this.model1.getSlotAt(r, c));
        }
        if (r == 4) {
          assertEquals(MarbleSolitaireModelState.SlotState.Marble, this.model1.getSlotAt(r, c));
        }
      }
    }
  }

  @Test
  public void getScore() {
    this.setUp();
    assertEquals(14, model1.getScore());
    assertEquals(14, model2.getScore());
    assertEquals(9, modelBaseDimIs4.getScore());
    assertEquals(27, model4.getScore());
  }

  @Test
  public void move() {
    this.setUp();
    assertEquals(14, model1.getScore());
    Assert.assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(3, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(0, 0));
    this.model1.move(2, 0, 0, 0);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(2, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(1, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(0, 0));
    assertEquals(13, model1.getScore());
    this.model1.move(4, 2, 2, 0);
    assertEquals(12, model1.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidFromOutOfBoundsMove() {
    this.setUp();
    assertEquals(14, model1.getScore());
    this.model1.move(8, 1, 8, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDestinationIsMarbleMove() {
    this.setUp();
    assertEquals(14, model1.getScore());
    this.model1.move(2, 0, 0, 0);
    this.model1.move(4, 2, 2, 0);
    this.model1.move(1, 1, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidJumpOverEmptyMove() {
    this.setUp();
    this.model1.move(2, 0, 0, 0);
    this.model1.move(4, 2, 2, 0);
    assertEquals(12, model1.getScore());
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(2, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(3, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(4, 2));
    //Move should not be possible
    this.model1.move(8, 0, 8, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDiagonalJump() {
    this.setUp();
    this.model1.move(5, 3, 3, 3);
    assertEquals(35, model1.getScore());
    this.model1.move(2, 3, 4, 3);
    assertEquals(34, model1.getScore());
    //Creating an empty slot for attempted diagonal jump
    this.model1.move(3, 5, 3, 3);
    assertEquals(33, model1.getScore());
    this.model1.move(5, 2, 3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTooFarJump() {
    this.setUp();
    this.model1.move(3, 0, 0, 0);
  }

  @Test
  public void isGameOver() {
    this.setUp();
    assertEquals(false, model1.isGameOver());
    this.model1.move(2, 0, 0, 0);
    assertEquals(false, model1.isGameOver());
    this.model1.move(4, 2, 2, 0);
    assertEquals(false, model1.isGameOver());
    //assertEquals(true, modelWithArmThickOnlyOne.isGameOver());
  }

  @Test
  public void getBoardSize() {
    this.setUp();
    assertEquals(5, model1.getBoardSize());
    assertEquals(5, model2.getBoardSize());
    assertEquals(4, modelBaseDimIs4.getBoardSize());
    assertEquals(7, model4.getBoardSize());
  }

  @Test
  public void getSlotAt() {
    this.setUp();
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model1.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model1.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model1.getSlotAt(0, 3));
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