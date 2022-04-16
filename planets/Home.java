/**********************************************************************************
 * Author: Ali Taha                     Date: November 1st 2021 - November 7th 2021
 * 
 * The home planet
 **********************************************************************************/
package planets;

import main.GalacticUI;
import cells.Protag;
import aliens.Saxon;
import items.*;
import java.util.ArrayList;

public class Home extends Planet {

    // Class variables
    Protag protag;

    public Home(int y, int x, Protag ptg) {
        super(0, y, x, null); // no alien race for home

        protag = ptg;
        setMapChar('H');
    }

    public void planetMenu() {
        if (protag.getInventory().contains(Item.IRON_ORE)) {
            GalacticUI.dialogue("Congrats, you did it!");
            GalacticUI.dialogue("That's all there is to this game right now.");
            GalacticUI.dialogue("Soo...");
            GalacticUI.dialogue("Wow, you have randomly won the lottery and won $1,000,000!");

            protag.addMoney(1000000);

            GalacticUI.dialogue("Feel free to buy a moon at your lesiure.");
        } else {
            GalacticUI.dialogue("Didn't you hear what I said?");
            GalacticUI.dialogue("Go get some iron ore!");
        }
    }
}