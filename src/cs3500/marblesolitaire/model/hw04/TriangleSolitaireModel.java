package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * TriangleSolitaireModel represents the data and operations that can be performed for a game of
 * Triangle solitaire.
 */
public class TriangleSolitaireModel extends AbstractSolitaireModel implements MarbleSolitaireModel {

  /**
   * Default constructor (no parameters) that creates a 5-row game with the empty slot at (0,0).
   */
  public TriangleSolitaireModel() {
    this.boardSize = 5;
    this.setUpBoard();
    this.setSlotToEmpty(0, 0);
  }

  /**
   * Creates a game with the specified dimensions (number of slots in the bottom-most row) and the
   * empty slot at (0,0).
   *
   * @param dimensions represents the number of slots in the bottom-most row.
   * @throws IllegalArgumentException if the specified dimension is invalid (non-positive).
   */
  public TriangleSolitaireModel(int dimensions) throws IllegalArgumentException {
    if (dimensions < 1) {
      throw new IllegalArgumentException("Specified Dimension of Triangle Solitaire Model is "
              + "invalid (non-positive)");
    } else {
      this.boardSize = dimensions;
      this.setUpBoard();
      this.setSlotToEmpty(0, 0);
    }
  }

  /**
   * Creates a 5-row game with the empty slot at the specified position.
   *
   * @param row represents the specified row of the initial empty slot.
   * @param col represents the specified column of the initial empty slot.
   * @throws IllegalArgumentException if the specified position is invalid.
   */
  public TriangleSolitaireModel(int row, int col) throws IllegalArgumentException {
    this.boardSize = 5;
    this.setUpBoard();
    this.setSlotToEmpty(row, col);
  }

  /**
   * Creates a game with the specified dimension and an empty slot at the specified row and column.
   *
   * @param dimensions represents the number of slots in the bottom-most row.
   * @param row        specifies the row position of the empty slot.
   * @param col        specifies the column position of the empty slot.
   * @throws IllegalArgumentException if the specified dimension is invalid (non-positive) or the
   *                                  specified position of the empty slot is invalid.
   */
  public TriangleSolitaireModel(int dimensions, int row, int col) throws IllegalArgumentException {
    if (dimensions < 1) {
      throw new IllegalArgumentException("Specified Dimension of Triangle Solitaire Model is "
              + "invalid (non-positive)");
    } else {
      //dimensions is a positive number
      this.boardSize = dimensions;
      this.setUpBoard();
      this.setSlotToEmpty(row, col);
    }
  }

  /**
   * Every SolitaireModel must set up and populate it's SlotState[][] board with SlotStates
   * according to its own implementation of the board.
   */
  @Override
  protected void setUpBoard() {
    board = new SlotState[boardSize][boardSize];
    int invalidDimension;

    //Set the remaining slots in each row to invalid, after setting the correct number of
    // slots in each row to marbles.
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board.length; c++) {
        invalidDimension = boardSize - r - 1;
        if (c + invalidDimension >= boardSize) {
          board[r][c] = SlotState.Invalid;
        } else {
          board[r][c] = SlotState.Marble;
        }
      }
    }
  }

  /**
   * Determines if the marble at the position specified can move up.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move up.
   */
  @Override
  protected boolean marbleCanJumpUp(int r, int c) {
    //Check if diagonal up right jump is possible
    try {
      if (((r - 2) < boardSize) && board[r - 2][c] == SlotState.Empty
              && board[r - 1][c] == SlotState.Marble) {
        return true;
      }
      //Check if diagonal up left jump is possible and if not then return false
      return ((r - 2) < boardSize) && ((c - 2) < boardSize)
              && board[r - 2][c - 2] == SlotState.Empty
              && board[r - 1][c - 1] == SlotState.Marble;
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }

  /**
   * Determines if the marble at the position specified can move down.
   *
   * @param r the row number to check.
   * @param c the column number to check.
   * @return true if the SlotState.Marble at board[r][c] can move down.
   */
  @Override
  protected boolean marbleCanJumpDown(int r, int c) {
    try {
      //Check if diagonal down left jump is possible
      if (((r + 2) < boardSize) && board[r + 2][c] == SlotState.Empty
              && board[r + 1][c] == SlotState.Marble) {
        return true;
      }
      //Check if diagonal down right jump is possible and if not then return false
      return ((r + 2) < boardSize) && ((c + 2) < boardSize)
              && board[r + 2][c + 2] == SlotState.Empty
              && board[r + 1][c + 1] == SlotState.Marble;
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }

  }

  @Override
  protected boolean destIsTwoPositionsDown(int fromRow, int fromCol, int toRow, int toCol) {
    return ((fromCol == toCol) && (toRow == fromRow + 2))
            || ((toCol == fromCol + 2) && (toRow == fromRow + 2));
  }

  @Override
  protected boolean destIsTwoPositionsUp(int fromRow, int fromCol, int toRow, int toCol) {
    return ((fromCol == toCol) && (toRow == fromRow - 2))
            || ((toCol == fromCol - 2) && (toRow == fromRow - 2));
  }

  /**
   * Move a single marble from a given position to another given position.
   * A move is valid only if the from and to positions are valid. Specific
   * implementations may place additional constraints on the validity of a move.
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
        if (fromCol == toCol) {
          board[fromRow - 1][fromCol] = SlotState.Empty;
        } else {
          //fromCol != toCol so moving up diagonally left
          board[fromRow - 1][fromCol - 1] = SlotState.Empty;
        }
      }
      if (this.destIsTwoPositionsDown(fromRow, fromCol, toRow, toCol)) {
        if (fromCol == toCol) {
          board[fromRow + 1][fromCol] = SlotState.Empty;
        } else {
          //fromCol != toCol so moving down diagonally right
          board[fromRow + 1][fromCol + 1] = SlotState.Empty;
        }
      }
    } else {
      //Move is invalid so throw IllegalArgumentException
      throw new IllegalArgumentException("The desired move is not possible");
    }
  }
  /*
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
   * false otherwise.

  protected boolean isMarbleBetweenFromAndTo(int fromRow, int fromCol, int toRow, int toCol) {
    try {
      return ((destIsTwoPositionsRight(fromRow, fromCol, toRow, toCol)
              && board[fromRow][fromCol + 1] == SlotState.Marble)
              || (destIsTwoPositionsLeft(fromRow, fromCol, toRow, toCol)
              && board[fromRow][fromCol - 1] == SlotState.Marble)
              || (destIsTwoPositionsUp(fromRow, fromCol, toRow, toCol)
              && (board[fromRow - 1][fromCol] == SlotState.Marble)
              || (board[fromRow - 1][fromCol - 1] == SlotState.Marble))
              || (destIsTwoPositionsDown(fromRow, fromCol, toRow, toCol)
              && (board[fromRow + 1][fromCol] == SlotState.Marble)
              || (board[fromRow + 1][fromCol + 1] == SlotState.Marble)));
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
  */
}
