package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Acts as the view for a game of MarbleSolitaire. The view is responsible for displaying the
 * graphical representation of the game to the user. In this case the view overrides toString
 * in order to return a formatted string that represents the current state of the board represented
 * by the relevant MarbleSolitaireModelState object. The view has access to public methods in this
 * interface which describe the model but do not allow mutation.
 */
public class MarbleSolitaireTextView extends AbstractSolitaireTextView
        implements MarbleSolitaireView {
  /**
   * Creates a new MarbleSolitaireTextView using a passed model which implements
   * MarbleSolitaireModelState and an Appendable destination to which to render the board.
   *
   * @param model       is the model to be queried in order to display the game.
   * @param destination is the object that this view uses as its destination for transmitting the
   *                    state of the marble solitaire board.
   * @throws IllegalArgumentException if the passed model or Appendable is null.
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState model, Appendable destination)
          throws IllegalArgumentException {
    super(model, destination);
  }

  /**
   * Creates a new MarbleSolitaireTextView using a passed model which implements
   * MarbleSolitaireModelState, and System.out as the default destination, to render the board.
   *
   * @param model is the model to be queried in order to display the game.
   * @throws IllegalArgumentException if the passed model is null.
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState model) throws IllegalArgumentException {
    super(model);
  }

  /**
   * Converts the desired row of the board to a string.
   *
   * @param row is the desired row of the board to represent.
   * @return a string representation of the desired row of the board contained in model.
   */
  @Override
  protected String modelRowToString(int row) {
    StringBuilder stringSoFar2 = new StringBuilder();

    this.validSlotsToStringOfRow(row, stringSoFar2);
    if (this.model.getSlotAt(row, this.model.getBoardSize() - 1)
            != MarbleSolitaireModelState.SlotState.Invalid) {
      stringSoFar2.append("\n");
    }
    return stringSoFar2.toString();
  }
}
