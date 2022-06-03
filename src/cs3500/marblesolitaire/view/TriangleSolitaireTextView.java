package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Acts as the view for a game of MarbleSolitaire. The view is responsible for displaying the
 * graphical representation of the game to the user. In this case the view overrides toString
 * in order to return a formatted string that represents the current state of the board represented
 * by the relevant MarbleSolitaireModelState object. The view has access to public methods in this
 * interface which describe the model but do not allow mutation.
 */
public class TriangleSolitaireTextView extends AbstractSolitaireTextView
        implements MarbleSolitaireView {

  /**
   * Creates a new AbstractSolitaireTextView using a passed model which implements
   * MarbleSolitaireModelState and an Appendable destination to which to render the board.
   *
   * @param model       is the model to be queried in order to display the game.
   * @param destination is the object that this view uses as its destination for transmitting the
   *                    state of the marble solitaire board.
   * @throws IllegalArgumentException if the passed model or Appendable is null.
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable destination)
          throws IllegalArgumentException {
    super(model, destination);
  }

  /**
   * Creates a new AbstractSolitaireTextView using a passed model which implements
   * MarbleSolitaireModelState, and System.out as the default destination, to render the board.
   *
   * @param model is the model to be queried in order to display the game.
   * @throws IllegalArgumentException if the passed model is null.
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState model)
          throws IllegalArgumentException {
    super(model);
  }

  @Override
  protected String modelRowToString(int row) {
    int numInvalidSlotsInRow = 0;
    int numValidSlotsInRow = 0;
    //Determine the number of valid and invalid slots
    for (int col = 0; col < this.model.getBoardSize(); col++) {
      if (this.model.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Invalid) {
        numInvalidSlotsInRow++;
      } else {
        numValidSlotsInRow++;
      }
    }
    if (numInvalidSlotsInRow + numValidSlotsInRow != this.model.getBoardSize()) {
      throw new IllegalArgumentException("This should not be possible...");
    }

    StringBuilder stringSoFar2 = new StringBuilder();

    //Add the spaces in the front of the triangle row before the marbles and empties
    for (int count = 0; count < numInvalidSlotsInRow; count++) {
      stringSoFar2.append(" ");
    }

    //Add the marbles and empties
    this.validSlotsToStringOfRow(row, stringSoFar2);
    if (this.model.getSlotAt(row, this.model.getBoardSize() - 1)
            != MarbleSolitaireModelState.SlotState.Invalid) {
      stringSoFar2.append("\n");
    }

    return stringSoFar2.toString();
  }
}
