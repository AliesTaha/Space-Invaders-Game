/**********************************************************************************
 * Author: Ali Taha                     Date: October 27th 2021 - 
 * 
 * Holds all the information on the protagonist of the game.
 **********************************************************************************/
package cells;

import items.Inventory;
import items.Item;
import main.GalacticUI;

public class Protag extends Cell {
  // Class Variables
  private String name;
  private int hp;
  private int money;
  private Item weapon;
  private Item armour;

  private int fuel;
  private int maxFuel;
  private int headlightRadius;
  private Inventory inventory;

  // Constructor
  public Protag(String n) {
    super('>', 0, 0, "protagonist"); // The protagonists spaceship is a lil arrow, awww (^O^)

    name = n;
    hp = 5;
    money = 100;
    weapon = Item.PISTOL;
    armour = Item.SHEET_METAL;

    fuel = 5;
    maxFuel = 5;
    headlightRadius = 2;
    inventory = new Inventory(1);
  }

  // get/sets
  public int getHP() {
    return hp;
  }

  public void setHP(int h) {
    hp = h;
  }

  public int getMoney() {
    return money;
  }

  public void addMoney(int m) {
    money += m;
  }

  public void removeMoney(int m) {
    money -= m;
  }

  public String getName() {
    return name;
  }

  public int getFuel() {
    return fuel;
  }

  public void decreaseFuel() {
    fuel--;
  }

  public void fillTank() {
    fuel = maxFuel;
  }

  public int getMaxFuel() {
    return maxFuel;
  }

  public void addToMaxFuel() {
    maxFuel++;
  }

  public int getHeadlightRadius() {
    return headlightRadius;
  }

  public void setHeadlightRadius(int hR) {
    headlightRadius = hR;
  }

  public Inventory getInventory() {
    return inventory;
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: N/A @return: void Purpose: Displays the pause menu
   ************************************************************************************/
  public void pauseMenu() {
    loop: while (true) {
      switch (GalacticUI.userMenu("----Pause Menu----", "INFO", "INVENTORY", "save & QUIT", "RESUME")) {
      case "info":
        displayInfo();
        break;
      case "inventory":
        displayInventory();
        break;
      case "quit":
        GalacticUI.display(GalacticUI.UNIMPED);
        break;
      case "resume":
        break loop;
      default:
        GalacticUI.display(GalacticUI.NOTHING);
      }
    }
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: N/A @return: void Purpose: Displays the current info of the protag
   ************************************************************************************/
  private void displayInfo() {
    GalacticUI.popup("---------------------------\n\n" +

        name + "\n\n" +

        "Hp: " + hp + "\n" + "Money: $" + money + "\n\n" +

        "Weapon: " + weapon.getName() + " - " + weapon.getPower() + " Damage" + "\n" + "Armour: " + armour.getName()
        + " - " + armour.getPower() + " Defense" + "\n\n" +

        "Fuel: " + fuel + "\n\n" +

        "---------------------------");
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: N/A @return: void Purpose: Displays the inventory when in the pause
   *             menu
   ************************************************************************************/
  private void displayInventory() {

    String responseString = "";

    loop: while (true) {
      // run menu
      String userInput = GalacticUI.userMenu(
          "---------------------------\n\n" + inventory.display(0) + "---------------------------\n\n" + responseString,
          "Up - W", "Left - A", "Down - S", "Right - D", "USE", "BACK");
      responseString = ""; // reset string

      // Respond to the user input
      try {
        switch (userInput) {
        case "use":
          responseString = "You can't use that here.";
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
        responseString = "Try Something else.";
      }
    }
  }
}