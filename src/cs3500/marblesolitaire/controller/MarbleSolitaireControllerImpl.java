package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * Represents an implementation of a controller for the game of Marble Solitaire. Any input coming
 * from the user will be received via the Readable object, and any output sent to the user will be
 * transmitted to the view.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Readable input;

  /**
   * Only constructor for MarbleSolitaireControllerImpl.
   *
   * @param model keeps track of state and operations available.
   * @param view  is responsible for displaying the state, and operations available to the user
   *              graphically.
   * @param input is the Readable abstraction for user input.
   * @throws IllegalArgumentException if model, view, or input are null.
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view,
                                       Readable input) {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("The MarbleSolitaireModel, MarbleSolitaireTextView, "
              + "and the Readable input cannot be null!");
    } else {
      this.model = model;
      this.view = view;
      this.input = input;
    }
  }

  /**
   * Utilizes a view and model to play a new game of Marble Solitaire. It throws an
   * IllegalStateException only if the controller is unable to successfully read input or transmit
   * output.
   *
   * <p>The game is "run" in the following sequence until the game ends:
   * 1) Using the view, render the current state of the game.
   * 2) Using the view, "Score: N" is transmitted to the user, replacing N with the actual score.
   * 3) If the game is ongoing, the next user input is obtained from the Readable object. A user
   * input consists of the following four individual values (separated by spaces or newlines).
   *
   * <p>- The row number of the position from where a marble is to be moved, beginning at 1
   * * (to make the input more user-friendly).
   * - The column number of the position from where a marble is to be moved, beginning at 1.
   * - The row number of the position to where a marble is to be moved, beginning at 1.
   * - The column number of the position to where a marble is to be moved, beginning at 1.
   * 4) These inputs are parsed and the information is passed on to the model to make the move.
   *
   * <p>5) If the game is not over, repeat the above steps. If the game is over, the method should
   * transmit each of the following in order: the message "Game over!", the final game state, and
   * the message "Score: N" with N replaced by the final score. The method then ends.
   *
   * <p>Quitting: If at any point, the next value is either the letter 'q' or the letter 'Q' , the
   * controller should transmit the following in order, separated by newlines:
   * the message "Game quit!", the message "State of game when quit:", the current game state,
   * and the message "Score: N" with N replaced by the final score. The method should then end.
   *
   * <p>Bad inputs: If any individual value is unexpected (i.e. something other than 'q' , 'Q',
   * or a positive number) it should ask the user to re-enter that value again. If the user
   * entered the from-row and from-column correctly but the to-row incorrectly, the controller
   * should continue attempting to read a value for to-row before moving on to read the value for
   * to-column. Once all four values are successfully read, if the move was invalid as signaled by
   * the model, the controller should transmit a message saying "Invalid move. Play again. X"
   * where X is any (optional) informative message about why the move was invalid (all on one line),
   * and resume waiting for valid input.
   */
  @Override
  public void playGame() throws IllegalStateException {
    Scanner sc = new Scanner(input);
    boolean gameOver = false;
    //Represents the desiredFromRow, desiredFromCol, desiredToRow, desiredToCol in order. If
    // coordinates.size() < 4 then not all the coordinates have been entered yet.
    ArrayList<Integer> coordinates = new ArrayList<Integer>();

    while (!gameOver) { //continue until the user quits
      //1) Using the view, render the current state of the game.
      this.commandViewToRenderBoard();

      //2) Using the view, "Score: N" is transmitted to the user, replacing N with the actual score.
      this.commandViewToRenderScore();

      if (this.model.isGameOver()) {
        gameOver = true;
        this.gameHasEnded();
        return;
      }

      if (!sc.hasNext()) {
        throw new IllegalStateException("Out of inputs!");
      }

      //3) If the game is ongoing, the next user input is obtained from the Readable object.
      while (coordinates.size() < 4) {
        //If the next input is an int then add it as a coordinate
        try {
          if (sc.hasNextInt()) {
            coordinates.add(sc.nextInt() - 1); //User board dimensions start at 1 and not 0
          } else {
            //Check if it is the letter q
            String userInput = sc.next();
            if (userInput.equalsIgnoreCase("q")) {
              gameOver = true;
              this.gameHasBeenQuit();
              return;
            }
            //Otherwise, render that the userInput is invalid and ask for new input, and loop.
            try {
              this.view.renderMessage("Invalid User Input = \"" + userInput + "\". Please input a "
                      + "positive integer coordinate, or the letter \"q\" to quit the game. \n");
            } catch (IOException e) {
              throw new IllegalStateException("IOException thrown when attempting to render invalid"
                      + "user input message!");
            }
          }
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("NoSuchElementException thrown when attempting to get "
                  + "next input from readable!");
        }
      }
      //Coordinates have been obtained, attempt a move!
      if (coordinates.size() == 4) {
        try {
          model.move(coordinates.get(0), coordinates.get(1), coordinates.get(2),
                  coordinates.get(3));
        } catch (IllegalArgumentException e) {
          try {
            this.view.renderMessage("Invalid move. Play again. " + e.getMessage() + "\n");
          } catch (IOException ex) {
            throw new IllegalStateException("IOException thrown when attempting to render"
                    + " \"Invalid move. Play again.\"");
          }
        }
        coordinates.clear();
      }
    }
  }

  private int getInteger(String userInput, Scanner sc) {
    int i = -1;
    try {
      i = Integer.parseInt(userInput);
      if (i < 0) {
        return sc.nextInt();
      }
    } catch (NumberFormatException nfe) {
      return sc.nextInt();
    }
    return i;
  }

  /**
   * When the game is over this method transmits each of the following in order:
   * the message "Game over!", the final game state, and the message "Score: N" with N replaced
   * by the final score. The method then ends.
   */
  private void gameHasEnded() throws IllegalStateException {
    try {
      this.view.renderMessage("Game over!\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException thrown when attempting to render Game Over!");
    }
    this.commandViewToRenderBoard();
    this.commandViewToRenderScore();
  }

  /**
   * When the game is quit this method has the view render the appropriate output.
   */
  private void gameHasBeenQuit() {
    try {
      this.view.renderMessage("Game quit!\nState of game when quit:\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException thrown when attempting to render Game Over!");
    }
    this.commandViewToRenderBoard();
    this.commandViewToRenderScore();
  }

  /**
   * Abstracted method used to command the view to render the current score of the game.
   *
   * @throws IllegalStateException if the view is unable to write to the destination.
   */
  private void commandViewToRenderScore() throws IllegalStateException {
    try {
      this.view.renderMessage("Score: " + this.model.getScore() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException thrown when attempting to render score!");
    }
  }

  /**
   * Abstracted method used to command the view to render the board.
   *
   * @throws IllegalStateException if the view is unable to write to the destination.
   */
  private void commandViewToRenderBoard() throws IllegalStateException {
    try {
      this.view.renderBoard();
      this.view.renderMessage("\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException thrown when attempting to render the board!");
    }
  }
}
