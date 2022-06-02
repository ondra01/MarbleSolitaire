package cs3500.marblesolitaire.model.hw02;

/**
 * EnglishSolitaireModel represents the data and operations that can be performed for a game of
 * English solitaire.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private final int armThickness;
  private SlotState[][] board;
  private int boardSize;

  /**
   * Initializes the game board with arm thickness 3 and the empty slot at the center.
   */
  public EnglishSolitaireModel() {
    armThickness = 3;
    this.setUpBoard();

    //Set the center slot to empty
    board[armThickness][armThickness] = SlotState.Empty;
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
    armThickness = 3;
    this.setUpBoard();
    this.setSlotToEmpty(sRow, sCol);
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
    //Checks if armThickness is a positive odd number
    if ((armThickness % 2) == 1) {
      this.armThickness = armThickness;
    } else {
      throw new IllegalArgumentException("The arm thickness must be a positive odd number!");
    }
    this.setUpBoard();

    //Set the center slot to empty
    board[boardSize / 2][boardSize / 2] = SlotState.Empty;
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
    //Checks if armThickness is a positive odd number
    if ((armThickness % 2) == 1) {
      this.armThickness = armThickness;
    } else {
      throw new IllegalArgumentException("The arm thickness must be a positive odd number!");
    }
    this.setUpBoard();
    this.setSlotToEmpty(sRow, sCol);
  }

  /**
   * Sets the specified slot to empty if it contains a marble.
   *
   * @param sRow specifies the row position of the empty slot.
   * @param sCol specifies the column position of the empty slot.
   * @throws IllegalArgumentException if the specified slot is invalid or out of bounds.
   */
  private void setSlotToEmpty(int sRow, int sCol) throws IllegalArgumentException {
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
   * Initializes all Invalid and Empty SlotStates in board. NOTE no SlotState of the board is empty
   * yet!
   */
  private void setUpBoard() {
    //INCORRECT INTERPRETATION: boardSize = (2 * armThickness) + 1;
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
  private boolean moveIsValid(int fromRow, int fromCol, int toRow, int toCol) {
    return ((isTwoPositionsAway(fromRow, fromCol, toRow, toCol))
            && (this.getSlotAt(fromRow, fromCol) == SlotState.Marble)
            && (this.getSlotAt(toRow, toCol) == SlotState.Empty)
            && isMarbleBetweenFromAndTo(fromRow, fromCol, toRow, toCol));
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
  private boolean isMarbleBetweenFromAndTo(int fromRow, int fromCol, int toRow, int toCol) {
    return ((destIsTwoPositionsRight(fromRow, fromCol, toRow, toCol)
            && board[fromRow][fromCol + 1] == SlotState.Marble)
            || (destIsTwoPositionsLeft(fromRow, fromCol, toRow, toCol)
            && board[fromRow][fromCol - 1] == SlotState.Marble)
            || (destIsTwoPositionsUp(fromRow, fromCol, toRow, toCol)
            && board[fromRow - 1][fromCol] == SlotState.Marble)
            || (destIsTwoPositionsDown(fromRow, fromCol, toRow, toCol)
            && board[fromRow + 1][fromCol] == SlotState.Marble));
  }

  private boolean destIsTwoPositionsDown(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromCol == toCol) && (toRow == fromRow + 2);
  }

  private boolean destIsTwoPositionsUp(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromCol == toCol) && (toRow == fromRow - 2);
  }

  private boolean destIsTwoPositionsLeft(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromRow == toRow) && (toCol == fromCol - 2);
  }

  private boolean destIsTwoPositionsRight(int fromRow, int fromCol, int toRow, int toCol) {
    return (fromRow == toRow) && (toCol == fromCol + 2);
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
  private boolean isTwoPositionsAway(int fromRow, int fromCol, int toRow, int toCol) {
    return destIsTwoPositionsDown(fromRow, fromCol, toRow, toCol)
            || destIsTwoPositionsUp(fromRow, fromCol, toRow, toCol)
            || destIsTwoPositionsRight(fromRow, fromCol, toRow, toCol)
            || destIsTwoPositionsLeft(fromRow, fromCol, toRow, toCol);
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
   * Determines if there is a marble at the position specified and if it can move.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if there is a SlotState.Marble at board[r][c] and it can move in any direction.
   */
  private boolean marbleHasMove(int r, int c) {
    if (board[r][c] != SlotState.Marble) {
      return false;
    } else {
      //There is a marble at [r][c]. If the marble can jump in a direction then game is not over!
      return marbleCanJumpUp(r, c) || marbleCanJumpDown(r, c) || marbleCanJumpRight(r, c)
              || marbleCanJumpLeft(r, c);
    }
  }

  /**
   * Determines if the marble at the position specified can move left.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move left.
   */
  private boolean marbleCanJumpLeft(int r, int c) {
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
  private boolean marbleCanJumpRight(int r, int c) {
    return (((c + 2) < boardSize) && board[r][c + 2] == SlotState.Empty
            && board[r][c + 1] == SlotState.Marble);
  }

  /**
   * Determines if the marble at the position specified can move down.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move down.
   */
  private boolean marbleCanJumpDown(int r, int c) {
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
  private boolean marbleCanJumpUp(int r, int c) {
    return (((r - 2) >= 0) && board[r - 2][c] == SlotState.Empty
            && board[r - 1][c] == SlotState.Marble);
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
   * Determines if the passed row or column are beyond the dimensions of the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return true if the sought row or column are beyond the dimensions of the board, and false
   *         otherwise.
   */
  private boolean rowOrColIsInvalid(int row, int col) {
    //Check if Row or Column are beyond the dimensions of the board
    return ((row < 0) || (row > this.boardSize - 1) || (col < 0) || (col > this.boardSize - 1));
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
}
