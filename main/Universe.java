/**********************************************************************************
 * Author: Ali Taha                     Date: October 25th 2021 - 
 * 
 * Universe world for the game. Holds all the panets and the user can movearound in it.
 * Everything in the world is generated from the seed, so a game can be repeated from the start, and 
 * it makes it easier to load from a file.
 * 
 * Methods (Outdated, this is too much work to maintain - Nov 2 2021):
 * 
 * Big:
 * # spaceTravel(): void
 * - mainMap(): String
 * 
 * Misc:
 * - randNum(int): int
 * - checkOrthogonalPlanet()
 * - moveHorizontal(Cell, int): void
 * - moveVertical(Cell, int): void
 * - movecell(Cell, int, int): void
 * - setCellCoordinates(int, int): void
 * + isVisible(int, int): boolean
 **********************************************************************************/
package main;

import cells.*;
import planets.*;
import items.*;
import java.util.ArrayList;

public class Universe {
	// Constants
	static final int MAX_X = 25;
	static final int MAX_Y = 25;

	// Class Variables
	private Cell[][] space; // Y coordinate, X coordinate
	private ArrayList<Planet> planets;
	private Protag protag; // The protagonist of the game

	// Constructors
	Universe(Protag ptg) { // New Universe

		// Initialize universe
		space = new Cell[MAX_X][MAX_Y];

		// Set up the protagonist in the universe at the right coordinate
		protag = ptg;
		space[22][3] = protag;
		setCellCoordinates(22, 3);
		protag.getInventory().addItem(Item.APPLE, 1);

		// Add planets
		planets = new ArrayList<>();

		// Add the home planet
		Home home = new Home(22, 2, protag);
		planets.add(home);
		space[home.getY()][home.getX()] = home;

		// Add Ceres
		Ceres ceres = new Ceres(20, 8, protag);
		planets.add(ceres);
		space[ceres.getY()][ceres.getX()] = ceres;
	}

	// get/set methods
	public Cell[][] getSpace() {
		return space;
	}

	/************************************************************************************
	 * Author: Ali Taha Date: October 27th 2021
	 * 
	 * @param: N/A @return: void Purpose: Handles the user moving around in the
	 *             space array from planet to planet.
	 ************************************************************************************/
	protected void spaceTravel() {

		String responseString = "";
		// infinite loop
		loop: while (true) {
			String userInput = GalacticUI.userMenu(mainMap() + "\n" + responseString, getSpaceOptions()); // run menu
			responseString = ""; // reset

			// Move the player in the direction that the user wants to move
			try {
				switch (userInput) { // Get direction user wants to move from the menu
				case "w": // up
					moveVertical(protag, -1); // negative is up in y
					break;
				case "s": // down
					moveVertical(protag, 1);
					break;
				case "a": // left
					moveHorizontal(protag, -1); // positive is right in x
					break;
				case "d": // right
					moveHorizontal(protag, 1);
					break;

				case "land":
					// Find the orthogonal planet and run menu
					checkOrthogonalPlanet(protag).planetMenu();
					responseString = "";
					break;

				case "pause":
					protag.pauseMenu();
					break;

				default:
					GalacticUI.display(GalacticUI.NOTHING); // If somehow the input is not a valid option
					break loop;
				}
			} catch (IndexOutOfBoundsException | SpaceNotNullException e) { // Catches if the user tries to move out of
																			// bounds
				responseString = "You can't move there.";
			}
		} // end infinite loop
	}

	/*****************************************************************
	 * Author: Ali Taha Date: October 26th 2021
	 * 
	 * @param: N/A @return: String Purpose: Returns what the main map looks like
	 *             factoring in the visible radius of the protagonist
	 *****************************************************************/
	private String mainMap() {
		StringBuilder map = new StringBuilder();
		Ceres ceres = (Ceres) planets.get(1);

		// loop through every cell
		for (int y = 0; y < space.length; y++) {
			for (int x = 0; x < space[0].length; x++) {
				try {
					if (isVisible(y, x))
						map.append(space[y][x].getMapChar()); // If a cell is visible, put it on the map
					else if (ceres.checkCoords(y, x) && ceres.isUnvisited()) {
						map.append('*'); // Undiscovered ceres gets a *
					} else {
						map.append('.'); // Unseen space gets a .
					}
				} catch (NullPointerException e) {
					map.append(' '); // Empty Cells get a space
				}
			}
			map.append('\n');
		}

		return map.toString();
	}

	/*****************************************************************
	 * Author: Ali Taha Date: November 2nd 2021
	 * 
	 * @param: N/A @return: String[] Purpose: Returns the options protag has while
	 *             in space
	 *****************************************************************/
	private String[] getSpaceOptions() {
		ArrayList<String> options = new ArrayList<>();

		// Add directional options
		options.add("Up - W");
		options.add("Left - A");
		options.add("Down - S");
		options.add("Right - D");

		// Add option to land on planet
		if (checkOrthogonalPlanet(protag) != null)
			options.add("LAND on planet");

		// Add pause option
		options.add("PAUSE");

		return options.toArray(new String[0]);
	}

	/*****************************************************************
	 * Author: Ali Taha Date: November 2nd 2021
	 * 
	 * @param: Cell @return: Planet Purpose: IF a planet is orthogonal to the given
	 *              cell, returns that planet. Else returns null
	 *****************************************************************/
	private Planet checkOrthogonalPlanet(Cell cell) {

		for (int i = 0; i < planets.size(); i++) {
			Planet planet = planets.get(i);

			if (distance(cell, planet) == 1)
				return planet;
		}

		// Default if no planet detected
		return null;
	}

	// -----------Misc-----------------------------

	/************************************************************************************
	 * Author: Ali Taha Date: October 22nd 2021
	 * 
	 * @param: int @return: int Purpose: Generates a pseudo random number based on
	 *             the given int. Uses the Lehmer Generator with a = 48271
	 ************************************************************************************/
	protected static int randNum(int iterations) {
		long m = 2147483647;
		long a = 48271;
		long x = 384982374;

		for (int i = 0; i < iterations; i++) {
			x = (a * x) % m;
		}

		return (int) x;
	}

	/************************************************************************************
	 * Author: Ali Taha Date: November 1st 2021
	 * 
	 * @param: Cell, int @return: void @throws: IndexOutOfBoundsException,
	 *               SpaceNotNullException Purpose: Moves the cell in the given
	 *               coordinate horizontally right by the given number of spaces
	 ************************************************************************************/
	private void moveHorizontal(Cell cell, int moveX) throws IndexOutOfBoundsException, SpaceNotNullException {
		moveCell(cell, 0, moveX);
	}

	/************************************************************************************
	 * Author: Ali Taha Date: November 1st 2021
	 * 
	 * @param: cell, int @return: void @throws: IndexOutOfBoundsException,
	 *               SpaceNotNullException Purpose: Moves the cell in the given
	 *               coordinate vertically down by the given number of spaces
	 ************************************************************************************/
	private void moveVertical(Cell cell, int moveY) throws IndexOutOfBoundsException, SpaceNotNullException {
		moveCell(cell, moveY, 0);
	}

	/************************************************************************************
	 * Author: Ali Taha Date: October 27th 2021
	 * 
	 * @param: Cell, int, int @return: void @throws: IndexOutOfBoundsException,
	 *               SpaceNotNullException Purpose: Moves the given cell moveY units
	 *               down and moveX units right as long as the target space is not
	 *               null
	 ************************************************************************************/
	private void moveCell(Cell cell, int moveY, int moveX) {
		int y = cell.getY();
		int x = cell.getX();

		if (space[y + moveY][x + moveX] == null) { // Move if the spot is empty
			// Set the target spot to be the initial spot
			space[y + moveY][x + moveX] = space[y][x];

			// Delete the inital spot
			space[y][x] = null;

			// Set the new position of the cell
			setCellCoordinates(y + moveY, x + moveX);
		} else {
			// throw exception if the wanted space is not null and give the type
			throw new SpaceNotNullException(space[y + moveY][x + moveX].getCellType());
		}
	}

	/************************************************************************************
	 * Author: Ali Taha Date: October 27th 2021
	 * 
	 * @param: int, int @return: void Purpose: Sets the coordinates of a Cell at a
	 *              postion to that position within the Cell
	 ************************************************************************************/
	private void setCellCoordinates(int y, int x) {
		// Try to set the new position of the cell
		try {
			space[y][x].setCoordinates(y, x);
		} catch (NullPointerException e) {
			/* Just don't do anything if null */}
	}

	/************************************************************************************
	 * Author: Ali Taha Date: October 29th 2021
	 * 
	 * @param: int, int @return: void Purpose: Checks if a coordinate in space is in
	 *              the visible radius from the protagonist.
	 ************************************************************************************/
	private boolean isVisible(int yCoord, int xCoord) {
		int y = Math.abs(yCoord - protag.getY()); // distance in y from the protagonist
		int x = Math.abs(xCoord - protag.getX());
		int radius = protag.getHeadlightRadius();

		if (y == radius + 1 || x == radius + 1)
			return false; // exclude the cells on the very edge of the visible diamond

		return (x + y <= protag.getHeadlightRadius() + 1);
	}

	/************************************************************************************
	 * Author: Ali Taha Date: November 2nd 2021
	 * 
	 * @param: Cell, Cell @return: int Purpose: Returns the distance from one cell
	 *               toanother using the taxicab metric
	 ************************************************************************************/
	private int distance(Cell start, Cell end) {
		int yDiff = Math.abs(Math.abs(start.getY()) - Math.abs(end.getY()));
		int xDiff = Math.abs(Math.abs(start.getX()) - Math.abs(end.getX()));

		return yDiff + xDiff;
	}
}