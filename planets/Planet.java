/**********************************************************************************
 * Author: Ali Taha                     Date: October 25th 2021 - November 3rd 2021 
 * 
 * Template for the planets in the game. Holds stuff like the items it currently holds, the aliens
 * on it, and its map character.
 **********************************************************************************/
package planets;

import cells.Cell;
import aliens.AlienRace;

public abstract class Planet extends Cell {
	// Class Variables
	private int planetNum;
	private AlienRace alienRace;

	// Constructors
	Planet(int pNum, int y, int x, AlienRace aR) {
		super('O', y, x, "planet");
		planetNum = pNum;
		alienRace = aR;
	}

	// get/set
	public AlienRace getAlienRace() {
		return alienRace;
	}

	// Abstract Methods
	public abstract void planetMenu();
	// protected abstract String[] menuOptions();
}