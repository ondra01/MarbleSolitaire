package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

public abstract class AbstractCartesianSolitaireModel extends AbstractSolitaireModel
        implements MarbleSolitaireModel {

  /**
   * Initializes the game board with arm thickness 3 and the empty slot at the center.
   */
  protected AbstractCartesianSolitaireModel() {
    armThickness = 3;
    this.setUpBoard();

    //Set the center slot to empty
    board[armThickness][armThickness] = SlotState.Empty;
  }

  /**
   * Initializes the game board with the empty slot at the center. Throws an
   * IllegalArgumentException if the arm thickness is not a positive odd number.
   *
   * @param armThickness represents the number of marbles in the top row (or bottom row, or left
   *                     or right columns).
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number.
   */
  public AbstractCartesianSolitaireModel(int armThickness) throws IllegalArgumentException {
    //Checks if armThickness is a positive odd number
    if ((armThickness % 2) == 1) {
      this.armThickness = armThickness;
    } else {
      throw new IllegalArgumentException("The arm thickness must be a positive odd number!");
    }
    this.setUpBoard();

    //Set the center slot to empty
    this.setSlotToEmpty(boardSize / 2, boardSize / 2);
    //board[boardSize / 2][boardSize / 2] = SlotState.Empty;
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
  public AbstractCartesianSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    armThickness = 3;
    this.setUpBoard();
    this.setSlotToEmpty(sRow, sCol);
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
  public AbstractCartesianSolitaireModel(int armThickness, int sRow, int sCol)
          throws IllegalArgumentException {
    //Checks if armThickness is a positive odd number
    if ((armThickness % 2) == 1) {
      this.armThickness = armThickness;
    } else {
      throw new IllegalArgumentException("The arm thickness must be a positive odd number!");
    }
    this.setUpBoard();
    this.setSlotToEmpty(sRow, sCol);
  }

  protected boolean destIsTwoPositionsDown(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromCol == toCol) && (toRow == fromRow + 2);
  }

  protected boolean destIsTwoPositionsUp(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromCol == toCol) && (toRow == fromRow - 2);
  }

  /**
   * Move a single marble from a given position to another given position, and replace the jumped
   * marble with an empty SlotState, if the move is valid. A move is valid if all these conditions
   * are true:
   * 1) the ''from" and ''to" positions are valid.
   * 2) there is a marble at the specified ''from" position.
   * 3) the ''to" position is empty.
   * 4) the ''to" and ''from" positions are exactly two positions away (horizontally or vertically).
   * 5) there is a marble in the slot between the ''to" and ''from" positions.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!this.rowOrColIsInvalid(fromRow, fromCol) && !this.rowOrColIsInvalid(toRow, toCol)
            && moveIsValid(fromRow, fromCol, toRow, toCol)) {
      //Move the marble
      board[fromRow][fromCol] = SlotState.Empty;
      board[toRow][toCol] = SlotState.Marble;
      //replace the jumped marble with an empty
      if (this.destIsTwoPositionsLeft(fromRow, fromCol, toRow, toCol)) {
        board[fromRow][fromCol - 1] = SlotState.Empty;
      }
      if (this.destIsTwoPositionsRight(fromRow, fromCol, toRow, toCol)) {
        board[fromRow][fromCol + 1] = SlotState.Empty;
      }
      if (this.destIsTwoPositionsUp(fromRow, fromCol, toRow, toCol)) {
        board[fromRow - 1][fromCol] = SlotState.Empty;
      }
      if (this.destIsTwoPositionsDown(fromRow, fromCol, toRow, toCol)) {
        board[fromRow + 1][fromCol] = SlotState.Empty;
      }
    } else {
      //Move is invalid so throw IllegalArgumentException
      throw new IllegalArgumentException("The desired move is not possible");
    }
  }

  /**
   * Determines if the marble at the position specified can move down.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move down.
   */
  protected boolean marbleCanJumpDown(int r, int c) {
    return (((r + 2) < boardSize) && board[r + 2][c] == SlotState.Empty
            && board[r + 1][c] == SlotState.Marble);
  }

  /**
   * Determines if the marble at the position specified can move up.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move up.
   */
  protected boolean marbleCanJumpUp(int r, int c) {
    return (((r - 2) >= 0) && board[r - 2][c] == SlotState.Empty
            && board[r - 1][c] == SlotState.Marble);
  }
}
