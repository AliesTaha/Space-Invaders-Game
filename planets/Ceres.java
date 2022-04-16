/**********************************************************************************
 * Author: Ali Taha                     Date: November 1st 2021 - November 7th 2021
 * 
 * Template for the advanced planets in the game. These planets can trade manufactured products but don't have any 
 * natural resources to mine. 
 * Alien Race: Saxon
 **********************************************************************************/
package planets;

import main.GalacticUI;
import cells.Protag;
import aliens.Saxon;
import items.*;
import java.util.ArrayList;

public class Ceres extends AdvancedPlanet {

  // Class Variables
  private Inventory inventory;
  private Protag protag;
  private boolean unvisited;

  public Ceres(int y, int x, Protag ptg) {
    super(1, y, x, new Saxon()); // Ceres is a Saxon Planet
    protag = ptg;
    unvisited = true;

    inventory = new Inventory(1000, // More than the store can ever hold
        new Slot(Item.IRON_ORE, 1000));
  }

  // get/set
  public boolean isUnvisited() {
    return unvisited;
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 2nd 2021
   * 
   * @param: N/A @return: void Purpose: Runs the menu for Ceres where the player
   *             can buy, sell, and do other stuff
   ************************************************************************************/
  public void planetMenu() {
    unvisited = false; // Make the planet appear visited

    String responseString = "Hello, do you want to trade, Ali??";

    planetMenu: while (true) {
      switch (GalacticUI.userMenu(alienMessage(responseString), menuOptions())) {
        case "buy":
          buyMenu();
          break;

        case "sell":
          sellMenu();
          break;

        case "talk":
          responseString = GalacticUI.UNIMPED;
          break;

        case "leave":
          break planetMenu;

        default:
          responseString = GalacticUI.NOTHING;
          break;
      }
    }
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: N/A @return: void Purpose: Runs the menu where the player can buy
   *             stuff
   ************************************************************************************/
  private void buyMenu() {
    String responseString = "";

    loop: while (true) {
      // run menu
      String userInput = GalacticUI.userMenu("------------------Ceres Store------------------\n\n"
          + inventory.display(1) + responseString + "\n\nMoney: $" + protag.getMoney(), "Up - W", "Left - A",
          "Down - S", "Right - D", "BUY", "BACK");
      responseString = ""; // reset string

      // Respond to the user input
      try {

        switch (userInput) {
          case "buy":
            int amount = GalacticUI.userInputInt("How many would you like to buy?", 1,
                inventory.getPointerItemAmount());
            responseString = buyItem(amount);
            break;
          case "back":
            break loop;
          default: // Every Pointer movement option falls here
            inventory.movePointer(userInput);
            break;
        }

      } catch (IndexOutOfBoundsException e) { // Catches if the user tries to move out of bounds
        responseString = "You can't select that.";
      } catch (IllegalArgumentException e) { // Catches if the uesr somehow eneters an invalid input
        responseString = "Try something else.";
      }
    }
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: int @return: String Purpose: Ckecks to see if the user can buy the
   *             selcted item, if they can, then they do. The method assumes the
   *             inventory pointer is already pointed at what the player wants
   ************************************************************************************/
  public String buyItem(int amount) {
    Inventory playerInv = protag.getInventory();

    // Check if the player has enough space and money to buy the item
    if (protag.getMoney() < inventory.getPointerItemPrice() * amount)
      return "You're too broke to buy this.";

    // See if the item fits in the player's inventory
    try {
      playerInv.addItem(inventory.getPointerItem(), amount);
    } catch (CannotAddException e) {
      return "There isn't enough space in your inventory for this.";
    }

    // Take money from player and remove item from store inventory
    protag.removeMoney(inventory.getPointerItemPrice() * amount);
    inventory.removeItem(amount);

    return "Thank you for your patronage.";
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: N/A @return: void Purpose: Runs the menu where the player can sell
   *             stuff
   ************************************************************************************/
  private void sellMenu() {
    String responseString = "";
    Inventory playerInv = protag.getInventory();

    loop: while (true) {
      // run menu
      String userInput = GalacticUI.userMenu("------------------Your Inventory------------------\n\n"
          + playerInv.display(2) + responseString + "\n\nMoney: $" + protag.getMoney(), "Up - W", "Left - A",
          "Down - S", "Right - D", "SELL", "BACK");
      responseString = ""; // reset string

      // Respond to the user input
      try {

        switch (userInput) {
          case "sell":
            int amount = GalacticUI.userInputInt("How many would you like to sell?", 1,
                playerInv.getPointerItemAmount());
            responseString = sellItem(amount);
            break;
          case "back":
            break loop;
          default: // Every Pointer movement option falls here
            inventory.movePointer(userInput);
            break;
        }

      } catch (IndexOutOfBoundsException e) { // Catches if the user tries to move out of bounds
        responseString = "You can't select that.";
      } catch (IllegalArgumentException e) { // Catches if the uesr somehow eneters an invalid input
        responseString = "Try something else.";
      }
    }
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: int @return: String Purpose: Sells the amount of the item from the
   *             player's menu. The method assumes the inventory pointer is
   *             already pointed at what the player wants to sell
   ************************************************************************************/
  public String sellItem(int amount) {
    Inventory playerInv = protag.getInventory();

    // Add to store inventory if the item is already there
    if (inventory.contains(playerInv.getPointerItem()))
      inventory.addItem(playerInv.getPointerItem(), amount);

    // Take money from player and remove item from play inventory
    protag.addMoney(playerInv.getPointerItemPrice() * amount);
    playerInv.removeItem(amount);

    return "That was a win-win deal.";
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 2nd 2021
   * 
   * @param: N/A @return: void Purpose: Provides the options for the Ceres's main
   *             menu
   ************************************************************************************/
  protected String[] menuOptions() {
    ArrayList<String> options = new ArrayList<>();

    options.addAll(baseMenuOptions()); // Add the base menu options to the menu options

    options.add("LEAVE Planet");
    return options.toArray(new String[0]);
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 3rd 2021
   * 
   * @param: String @return: String Purpose: Returns the aliens current emotion
   *                plus the message formatted
   ************************************************************************************/
  private String alienMessage(String message) {
    return getAlienRace().getDefaultEmotion() + "\n\n" + message;
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 3rd 2021
   * 
   * @param: String, String @return: String Purpose: Returns a given alien emotion
   *                 plus the message formatted
   ************************************************************************************/
  private String alienMessage(String emotion, String message) {
    return emotion + "\n\n" + message;
  }
}