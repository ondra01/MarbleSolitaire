package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {
  protected int armThickness;
  protected SlotState[][] board;
  protected int boardSize;

  protected AbstractSolitaireModel() {
  }

  protected abstract void setUpBoard();
  protected abstract boolean marbleHasMove(int r, int c);

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    int score = 0;
    //Add 1 to the score for each marble
    for (SlotState[] rowSlotState : board) {
      for (SlotState s : rowSlotState) {
        if (s == SlotState.Marble) {
          score++;
        }
      }
    }
    return score;
  }

  /**
   * Determine and return if the game is over or not. A game is over if no
   * more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board.length; c++) {
        if (marbleHasMove(r, c)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Sets the specified slot to empty if it contains a marble.
   *
   * @param sRow specifies the row position of the empty slot.
   * @param sCol specifies the column position of the empty slot.
   * @throws IllegalArgumentException if the specified slot is invalid or out of bounds.
   */
  protected void setSlotToEmpty(int sRow, int sCol) throws IllegalArgumentException {
    //Set the specified slot to empty if it contains a marble. If the specified slot is invalid or
    //out of bounds then an IllegalArgumentException is thrown.
    if (rowOrColIsInvalid(sRow, sCol)) {
      throw new IllegalArgumentException("The desired row or column is beyond the dimensions"
              + " of the board");
    } else if (board[sRow][sCol] == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    } else if (board[sRow][sCol] == SlotState.Marble) {
      board[sRow][sCol] = SlotState.Empty;
    }
  }

  /**
   * Determines if the passed row or column are beyond the dimensions of the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return true if the sought row or column are beyond the dimensions of the board, and false
   *         otherwise.
   */
  protected boolean rowOrColIsInvalid(int row, int col) {
    //Check if Row or Column are beyond the dimensions of the board
    return ((row < 0) || (row > this.boardSize - 1) || (col < 0) || (col > this.boardSize - 1));
  }

  /**
   * Return the size of this board. The size is roughly the longest dimension of a board
   *
   * @return the size as an integer
   */
  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  /**
   * Get the state of the slot at a given position on the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return the state of the slot at the given row and column
   * @throws IllegalArgumentException if the row or the column are beyond
   *                                  the dimensions of the board
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (this.rowOrColIsInvalid(row, col)) {
      throw new IllegalArgumentException("The desired row or column are beyond the dimensions of "
              + "the board which are: " + this.boardSize + "X" + this.boardSize);
    }
    //Valid Row and Column so return the SlotState at that position
    return board[row][col];
  }
}
