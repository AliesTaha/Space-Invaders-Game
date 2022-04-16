/**********************************************************************************
 * Author: Ali Taha                     Date: October 22nd 2021 - November 17 2021
 * 
 * Galactic Captitalism - A game where the objective is to amass enough money to
 * buy a moon. Say goodbye to Earth and travel to alien planets to trade 
 * natural resources.
 **********************************************************************************/
package main;

import cells.*;

class GalacticCapitalism {

  // Class Variables
  static Universe universe;
  static Protag protag;

  public static void main(String[] args) {
    GalacticUI.display("\n\n\n\nWelcome to Galactic Capitalism\n\nTo select a menu option type the capitalized word.\n"
        + "You can type in whatever case you want.");

    mainMenu: while (true) {
      // -------Main Menu------------
      switch (GalacticUI.userMenu("-----Main Menu-----", "NEW Game", "CONTINUE", "INSTRUCTIONS", "EXIT")) {
      case "new":
        startGame();
        universe = new Universe(protag);
        universe.spaceTravel(); // Start travelling in the universe
        break;

      case "continue":
        GalacticUI.display(GalacticUI.UNIMPED);
        break;

      case "instructions":
        GalacticUI.popup(
            "Amass enough money to buy a moon.\nTravel to alien planets to trade and exploit them of their natural resources.");
        break;

      case "exit":
        break mainMenu; // Breaks the main Menu loop

      default:
        GalacticUI.display(GalacticUI.UNIMPED);
        break;
      }
    } // End of main loop
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: N/A @return: void Purpose: Performs the setup at the start of the
   *             game, username and all that
   ************************************************************************************/
  private static void startGame() {

    // Create player character
    String name = GalacticUI.userInputAnyString("Your name is?");
    protag = new Protag(name);

    d("Good, Good.");
    d("In this world you will learn to be the most powerful type of person there is...");
    d("...a CAPITALIST.");

    d("But first you'll have to do a small errand for me.");
    d("You've got $100, go buy some Iron Ore.");
    d("Where? It's on Ceres.");
    d("Don't worry, I've marked it on your map.");
    d("Just fly over there and fly back, easy 5 minute trip.");
  }

  // shorthand for dialogue
  private static void d(String message) {
    GalacticUI.dialogue(message);
  }
}