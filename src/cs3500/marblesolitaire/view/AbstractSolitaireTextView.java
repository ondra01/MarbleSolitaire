package cs3500.marblesolitaire.view;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Represents the abstraction of common data and operations which all MarbleSolitaireViews have
 * in common.
 */
public abstract class AbstractSolitaireTextView implements MarbleSolitaireView {
  protected final MarbleSolitaireModelState model;
  protected final Appendable destination;

  /**
   * Creates a new AbstractSolitaireTextView using a passed model which implements
   * MarbleSolitaireModelState and an Appendable destination to which to render the board.
   *
   * @param model       is the model to be queried in order to display the game.
   * @param destination is the object that this view uses as its destination for transmitting the
   *                    state of the marble solitaire board.
   * @throws IllegalArgumentException if the passed model or Appendable is null.
   */
  protected AbstractSolitaireTextView(MarbleSolitaireModelState model, Appendable destination)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model Cannot be Null!");
    } else if (destination == null) {
      throw new IllegalArgumentException("Appendable Cannot be Null!");
    } else {
      this.model = model;
      this.destination = destination;
    }
  }

  /**
   * Creates a new AbstractSolitaireTextView using a passed model which implements
   * MarbleSolitaireModelState, and System.out as the default destination, to render the board.
   *
   * @param model is the model to be queried in order to display the game.
   * @throws IllegalArgumentException if the passed model is null.
   */
  protected AbstractSolitaireTextView(MarbleSolitaireModelState model)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model Cannot be Null!");
    } else {
      this.model = model;
      this.destination = System.out;
    }
  }

  /**
   * Return a string that represents the current state of the board. The
   * string should have one line per row of the game board. Each slot on the
   * game board is a single character (O, _ or space for a marble, empty and
   * invalid position respectively). Slots in a row should be separated by a
   * space. Each row has no space before the first slot and after the last slot.
   *
   * @return the game state as a string
   */
  @Override
  public String toString() {
    StringBuilder stringSoFar = new StringBuilder();
    for (int row = 0; row < this.model.getBoardSize(); row++) {
      stringSoFar.append(this.modelRowToString(row));
    }
    //Remove the last new line character
    stringSoFar.deleteCharAt(stringSoFar.length() - 1);
    return stringSoFar.toString();
  }

  protected abstract String modelRowToString(int row);

  /**
   * Render the board to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderBoard() throws IOException {
    this.destination.append(this.toString());
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    this.destination.append(message);
  }

  protected void validSlotsToStringOfRow(int row, StringBuilder stringSoFar2) {
    for (int col = 0; col < this.model.getBoardSize(); col++) {
      //Marbles should be replaced with an uppercase O
      if (this.model.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Marble) {
        stringSoFar2.append("O");
        //Add a space if this is not the last column
        if (col < this.model.getBoardSize() - 1) {
          stringSoFar2.append(" ");
        }
      } else if (this.model.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Empty) {
        //Empty should be replaced with an _
        stringSoFar2.append("_");
        //Add a space if this is not the last column
        if (col < this.model.getBoardSize() - 1) {
          stringSoFar2.append(" ");
        }
      }
      //If this is an invalid and the previous SlotState was a marble or an empty then return the
      //string as is with a new line
      else if (this.model.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Invalid) {
        if ((col - 1 >= 0) && (model.getSlotAt(row, col - 1)
                == MarbleSolitaireModelState.SlotState.Marble
                || model.getSlotAt(row, col - 1)
                == MarbleSolitaireModelState.SlotState.Empty)) {
          //Remove the space after the last marble or empty
          stringSoFar2.deleteCharAt(stringSoFar2.length() - 1);
          stringSoFar2.append("\n");
          return;
        } else {
          stringSoFar2.append("  ");
        }
      }
    }
    return;
  }
}
