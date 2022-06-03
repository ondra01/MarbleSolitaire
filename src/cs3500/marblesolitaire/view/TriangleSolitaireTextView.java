package cs3500.marblesolitaire.view;

import java.io.IOException;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

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
    return null;
  }
}
