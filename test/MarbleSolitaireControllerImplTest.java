import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MockModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

import static org.junit.Assert.assertEquals;

/**
 * Testing controller implementation MarbleSolitaireControllerImpl.
 */
public class MarbleSolitaireControllerImplTest {
  MarbleSolitaireModel model;
  MarbleSolitaireView view;
  StringBuilder output;

  //Mock Model and log
  StringBuilder log;
  MarbleSolitaireModel mockModel;

  //Readable inputs and their respective controllers
  Readable readable;
  MarbleSolitaireController controller;
  Readable readable2;
  MarbleSolitaireController controller2;
  Readable readable3;
  MarbleSolitaireController controller3;
  Readable readable4;
  MarbleSolitaireController controller4;
  Readable readable5;
  MarbleSolitaireController controller5;
  Readable readable6;
  MarbleSolitaireController controller6;
  Readable readable7;
  MarbleSolitaireController controller7;
  Readable readable8;
  MarbleSolitaireController controller8;
  Readable readable9;
  MarbleSolitaireController controller9;
  Readable readable10;
  MarbleSolitaireController controller10;
  Readable readable11;
  MarbleSolitaireController controller11;

  MarbleSolitaireController controllerWithMockModel;

  @Before
  public void setUp() {
    model = new EnglishSolitaireModel();
    output = new StringBuilder();
    view = new MarbleSolitaireTextView(model, output);

    //Mock Model and log
    log = new StringBuilder();
    mockModel = new MockModel(log);

    //Readable inputs and their respective controllers
    readable = new StringReader("q");
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    readable2 = new StringReader("badInput q");
    controller2 = new MarbleSolitaireControllerImpl(model, view, readable2);
    readable3 = new StringReader("badInput 1 iLoveOOD q");
    controller3 = new MarbleSolitaireControllerImpl(model, view, readable3);
    readable4 = new StringReader("4 2 4 4 q");
    controller4 = new MarbleSolitaireControllerImpl(model, view, readable4);
    readable5 = new StringReader("4 2 4 4 4 5 4 3 q");
    controller5 = new MarbleSolitaireControllerImpl(model, view, readable5);
    readable6 = new StringReader("4 1 4 3 q");
    controller6 = new MarbleSolitaireControllerImpl(model, view, readable6);
    readable7 = new StringReader("4\n 2\n 4\n 4\n 4\n 5\n 4\n 3\n q");
    controller7 = new MarbleSolitaireControllerImpl(model, view, readable7);
    readable8 = new StringReader("4 2 4 4 4 4 1 1 4 5 4 3 q");
    controller8 = new MarbleSolitaireControllerImpl(model, view, readable8);
    readable9 = new StringReader("6 4 4 4 5 6 5 4 7 5 5 5 7 3 7 5 4 5 6 5 7 5 5 5 2 5 4 5 "
            + "3 7 3 5 5 7 3 7 3 4 3 6 3 7 3 5 3 2 3 4 1 3 3 3 1 5 1 3 4 3 2 3 1 3 3 3 6 3 4 3 "
            + "5 1 5 3 3 1 5 1 5 4 5 2 5 1 5 3 3 4 3 2 3 2 5 2 5 2 5 4 5 4 5 6 5 6 3 6 3 6 3 4 "
            + "4 4 4 2 2 4 4 4 4 5 4 3 4 2 4 4 GAME HAS BEEN WON ADDITIONAL INPUTS ARE IRRELEVANT");
    controller9 = new MarbleSolitaireControllerImpl(model, view, readable9);
    readable10 = new StringReader("4 -1 -6 2 4 4 q");
    controller10 = new MarbleSolitaireControllerImpl(model, view, readable10);
    readable11 = new StringReader("4 true 2 4 4 q");
    controller11 = new MarbleSolitaireControllerImpl(model, view, readable11);

    controllerWithMockModel = new MarbleSolitaireControllerImpl(mockModel, view, readable4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void passedModelIsNull() {
    controller = new MarbleSolitaireControllerImpl(null, view, readable);
  }

  @Test(expected = IllegalArgumentException.class)
  public void passedViewIsNull() {
    controller = new MarbleSolitaireControllerImpl(model, null, readable);
  }

  @Test(expected = IllegalArgumentException.class)
  public void passedReadableIsNull() {
    controller = new MarbleSolitaireControllerImpl(model, view, null);
  }

  @Test
  public void testQuitting() {
    this.controller.playGame();
    assertEquals("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32\n", output.toString());
  }

  @Test
  public void badInput() {
    this.controller2.playGame();
    String[] lines = output.toString().split("\n");
    assertEquals("Invalid User Input = \"badInput\". Please input a positive integer "
            + "coordinate, or the letter \"q\" to quit the game. ", lines[8]);
  }

  @Test
  public void twoBadInputs() {
    this.controller3.playGame();
    //assertEquals("", output.toString());
    String[] lines = output.toString().split("\n");
    assertEquals("Invalid User Input = \"badInput\". Please input a positive integer "
            + "coordinate, or the letter \"q\" to quit the game. ", lines[8]);
    assertEquals("Invalid User Input = \"iLoveOOD\". Please input a positive integer "
            + "coordinate, or the letter \"q\" to quit the game. ", lines[9]);
  }

  @Test
  public void oneValidMoveThenQuit() {
    try {
      this.controller4.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("O _ _ O O O O", lines[11]);
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void twoValidMovesThenQuit() {
    try {
      this.controller5.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("O _ _ O O O O", lines[11]);
      assertEquals("O _ O _ _ O O", lines[19]);
      assertEquals("O _ O _ _ O O", lines[29]);
      //assertEquals("", output.toString());
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void oneInValidMoveThenQuit() {
    try {
      this.controller6.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("Invalid move. Play again. The desired move is not possible", lines[8]);
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void inputIsValidWithSpacesOrNewLines() {
    try {
      this.controller7.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("O _ _ O O O O", lines[11]);
      assertEquals("O _ O _ _ O O", lines[19]);
      assertEquals("O _ O _ _ O O", lines[29]);
      //assertEquals("", output.toString());
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void inputsPassedToMockModelAreCorrectlyAdjusted() {
    try {
      //Game will be played with the following single valid move then quit "4 2 4 4 q");
      this.controllerWithMockModel.playGame();
      assertEquals("move(inputs): fromRow = 3, fromCol = 1, toRow = 3, toCol = 3.\n",
              log.toString());
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void OneValidMoveThenInvalidMoveThenValidMoveThenQuit() {
    try {
      this.controller8.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("Invalid move. Play again. The desired move is not possible",
              lines[16]);
      assertEquals("O _ O _ _ O O", lines[38]);
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void gameHasBeenWonTest() {
    try {
      //Game has been won only 1 marble left at the center of the standard board.
      this.controller9.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("Game over!", lines[256]);
      assertEquals("_ _ _ O _ _ _", lines[260]);
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void inputIsANegativeInteger() {
    try {
      this.controller10.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("O O O _ O O O", lines[3]);
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

  @Test
  public void inputIsABoolean() {
    try {
      this.controller11.playGame();
      String[] lines = output.toString().split("\n");
      assertEquals("O O O _ O O O", lines[3]);
    } catch (IllegalStateException e) {
      //Should not happen
    }
  }

}