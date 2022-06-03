package cs3500.marblesolitaire.model.hw02;

import cs3500.marblesolitaire.model.hw04.AbstractCartesianSolitaireModel;

/**
 * EnglishSolitaireModel represents the data and operations that can be performed for a game of
 * English solitaire.
 */
public class EnglishSolitaireModel extends AbstractCartesianSolitaireModel
        implements MarbleSolitaireModel {

  /**
   * Initializes the game board with arm thickness 3 and the empty slot at the center.
   */
  public EnglishSolitaireModel() {
    super();
  }

  /**
   * Initializes the game board with the empty slot at the center. Throws an
   * IllegalArgumentException if the arm thickness is not a positive odd number.
   *
   * @param armThickness represents the number of marbles in the top row (or bottom row, or left
   *                     or right columns).
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number.
   */
  public EnglishSolitaireModel(int armThickness) throws IllegalArgumentException {
    super(armThickness);
  }

  /**
   * Initializes the game board so that the arm thickness is 3 and the empty slot is at the position
   * (sRow, sCol). If this specified position is invalid, it should throw an
   * IllegalArgumentException with a message "Invalid empty cell position (r,c)" with r and c
   * replaced with the row and column passed to it.
   *
   * @param sRow specifies the row position of the empty slot.
   * @param sCol specifies the column position of the empty slot.
   * @throws IllegalArgumentException if the specified position of the empty slot is invalid.
   */
  public EnglishSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    super(sRow, sCol);
  }

  /**
   * Initializes the game board with the arm thickness passed to it, as long as the arm thickness
   * is a positive odd number. The empty slot is created at the position
   * (sRow, sCol). If this specified position is invalid, it should throw an
   * IllegalArgumentException with a message "Invalid empty cell position (r,c)" with r and c
   * replaced with the row and column passed to it.
   *
   * @param armThickness represents the number of marbles in the top row (or bottom row, or left
   *                     or right columns).
   * @param sRow         specifies the row position of the empty slot.
   * @param sCol         specifies the column position of the empty slot.
   * @throws IllegalArgumentException if the specified position of the empty slot is invalid.
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol)
          throws IllegalArgumentException {
    super(armThickness, sRow, sCol);
  }

  /**
   * Initializes all Invalid and Marble SlotStates in board. NOTE no SlotState of the board is empty
   * yet!
   */
  @Override
  protected void setUpBoard() {
    boardSize = (3 * armThickness) - 2;
    board = new SlotState[boardSize][boardSize];
    int invalidDimension;
    if (armThickness == 1) {
      invalidDimension = 1;
    } else {
      invalidDimension = (this.armThickness - 1);
    }

    //Set the corner slots to invalid, and the rest of the board to marbles
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board.length; c++) {
        if ((r < invalidDimension || r >= armThickness + invalidDimension)
                && (c < invalidDimension || c >= armThickness + invalidDimension)) {
          board[r][c] = SlotState.Invalid;
        } else {
          board[r][c] = SlotState.Marble;

        }
      }
    }
  }

}
