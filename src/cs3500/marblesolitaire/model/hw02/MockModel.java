package cs3500.marblesolitaire.model.hw02;

import java.util.Objects;

/**
 * This mock is used for testing inputs passed by controller to the model.
 */
public class MockModel implements MarbleSolitaireModel {
  StringBuilder log;

  /**
   * Initializes the log needed for recording values of inputs passed by controller to the model.
   * @param log will keep track of the inputs passed by controller to the model, and can be
   *            accessed by the test which creates it.
   */
  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Appends the values of the rows and columns passed to the mockModel to the log it stores,
   * in order to test inputs passed by controller to the model.
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    log.append("move(inputs): fromRow = " + fromRow + ", " + "fromCol = " + fromCol + ", "
            + "toRow = " + toRow + ", " +  "toCol = " + toCol + ".\n");
  }

  /**
   * Always returns false. Output is irrelevant for a mock used for testing.
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * Always returns 0. Output is irrelevant for a mock used for testing.
   */
  @Override
  public int getBoardSize() {
    return 0;
  }

  /**
   * Appends the values of the row and column passed to the mockModel to the log it stores,
   * in order to test inputs passed by controller to the model.
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    log.append("getSlotAt(inputs): row = " + row + ", " + "col = " + col + ".\n");
    return null;
  }

  /**
   * Always returns 0. Output is irrelevant for a mock used for testing.
   */
  @Override
  public int getScore() {
    return 0;
  }
}
