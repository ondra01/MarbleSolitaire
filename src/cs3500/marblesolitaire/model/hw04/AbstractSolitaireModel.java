package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {
  protected int armThickness;
  protected SlotState[][] board;
  protected int boardSize;

  /**
   * Every SolitaireModel must set up and populate it's SlotState[][] board with SlotStates
   * according to its own implementation of the board.
   */
  protected abstract void setUpBoard();

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

  /**
   * Determines if the marble at the position specified can move left.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move left.
   */
  protected boolean marbleCanJumpLeft(int r, int c) {
    return (((c - 2) >= 0) && board[r][c - 2] == SlotState.Empty
            && board[r][c - 1] == SlotState.Marble);
  }

  /**
   * Determines if the marble at the position specified can move right.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move right.
   */
  protected boolean marbleCanJumpRight(int r, int c) {
    return (((c + 2) < boardSize) && board[r][c + 2] == SlotState.Empty
            && board[r][c + 1] == SlotState.Marble);
  }

  /**
   * Determines if there is a marble at the position specified and if it can move.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if there is a SlotState.Marble at board[r][c] and it can move in any direction.
   */
  protected boolean marbleHasMove(int r, int c) {
    if (board[r][c] != SlotState.Marble) {
      return false;
    } else {
      //There is a marble at [r][c]. If the marble can jump in a direction then game is not over!
      return marbleCanJumpUp(r, c) || marbleCanJumpDown(r, c) || marbleCanJumpRight(r, c)
              || marbleCanJumpLeft(r, c);
    }
  }

  /**
   * Determines if the marble at the position specified can move up.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move up.
   */
  protected abstract boolean marbleCanJumpUp(int r, int c);

  /**
   * Determines if the marble at the position specified can move down.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move down.
   */
  protected abstract boolean marbleCanJumpDown(int r, int c);

  /**
   * Determines if a move is valid. A move is valid if all these conditions are true:
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
   * @return true if the move is possible, and false otherwise.
   */
  protected boolean moveIsValid(int fromRow, int fromCol, int toRow, int toCol) {
    return ((isTwoPositionsAway(fromRow, fromCol, toRow, toCol))
            && (this.getSlotAt(fromRow, fromCol) == SlotState.Marble)
            && (this.getSlotAt(toRow, toCol) == SlotState.Empty)
            && isMarbleBetweenFromAndTo(fromRow, fromCol, toRow, toCol));
  }

  /**
   * Determines if the destination row and column are two slots away from the source row and col.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @return true if the destination is 2 slots away from the source, and false otherwise.
   */
  protected boolean isTwoPositionsAway(int fromRow, int fromCol, int toRow, int toCol) {
    return destIsTwoPositionsDown(fromRow, fromCol, toRow, toCol)
            || destIsTwoPositionsUp(fromRow, fromCol, toRow, toCol)
            || destIsTwoPositionsRight(fromRow, fromCol, toRow, toCol)
            || destIsTwoPositionsLeft(fromRow, fromCol, toRow, toCol);
  }

  protected abstract boolean destIsTwoPositionsDown(int fromRow, int fromCol, int toRow, int toCol);

  protected abstract boolean destIsTwoPositionsUp(int fromRow, int fromCol, int toRow, int toCol);

  protected boolean destIsTwoPositionsLeft(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromRow == toRow) && (toCol == fromCol - 2);
  }

  protected boolean destIsTwoPositionsRight(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromRow == toRow) && (toCol == fromCol + 2);
  }

  /**
   * Determines if there is a marble in the slot between the ''to" and ''from" positions.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @return true if there is a marble in the slot between the ''to" and ''from" positions, and
   *         false otherwise.
   */
  protected boolean isMarbleBetweenFromAndTo(int fromRow, int fromCol, int toRow, int toCol) {
    return ((destIsTwoPositionsRight(fromRow, fromCol, toRow, toCol)
            && board[fromRow][fromCol + 1] == SlotState.Marble)
            || (destIsTwoPositionsLeft(fromRow, fromCol, toRow, toCol)
            && board[fromRow][fromCol - 1] == SlotState.Marble)
            || (destIsTwoPositionsUp(fromRow, fromCol, toRow, toCol)
            && board[fromRow - 1][fromCol] == SlotState.Marble)
            || (destIsTwoPositionsDown(fromRow, fromCol, toRow, toCol)
            && board[fromRow + 1][fromCol] == SlotState.Marble));
  }
}
