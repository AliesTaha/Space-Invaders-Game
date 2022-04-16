/**********************************************************************************
 * Author: Ali Taha                     Date: October 29th 2021 - November 3rd 2021
 * 
 * Template for the advanced planets in the game. These planets can trade manufactured products but don't have any 
 * natural resources to mine.
 **********************************************************************************/
package planets;

import java.util.ArrayList;
import aliens.AlienRace;

abstract class AdvancedPlanet extends Planet {

  AdvancedPlanet(int pNum, int y, int x, AlienRace aR) {
    super(pNum, y, x, aR);
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 3rd 2021
   * 
   * @param: N/A @return: ArrayList<String> Purpose: Returns the base menu options
   *             for any Advanced Planet
   ************************************************************************************/

  protected ArrayList<String> baseMenuOptions() {
    ArrayList<String> output = new ArrayList<>();

    output.add("BUY");
    output.add("SELL");
    // output.add("TALK"); //Add this feature later (hopefully) - Nov 3 2021
    return output;
  }
}
