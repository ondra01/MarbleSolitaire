package cs3500.marblesolitaire.controller;

/**
 * Represents a controller for the game of Marble Solitaire.
 */
public interface MarbleSolitaireController {
  /**
   * Utilizes a view and model to play a new game of Marble Solitaire. It throws an
   * IllegalStateException only if the controller is unable to successfully read input or transmit
   * output.
   */
  void playGame() throws IllegalStateException;
}
