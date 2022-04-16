/**********************************************************************************
 * Author: Ali Taha                     Date: October 26th 2021 - 
 * 
 * This is the base unit for the universe. Anything that can show up on the main universe map
 * is a cell. Not meant to be instantiated.
 **********************************************************************************/
package cells;

import java.util.Arrays;

public abstract class Cell {
	// Variables
	private char mapChar; // The character this cell is represented by on the map
	private int[] coordinates; // Coordinates of the cell; [y][x]
	private String cellType;

	// Constructors
	protected Cell() {
		mapChar = '.'; // Universe
		coordinates = new int[2];
		cellType = null;
	}

	protected Cell(char mC, int y, int x, String cT) {
		mapChar = mC; // Universe
		coordinates = new int[2];
		setX(x);
		setY(y);
		cellType = cT;
	}

	// Get/Sets
	public char getMapChar() {
		return mapChar;
	}

	public void setMapChar(char mC) {
		mapChar = mC;
	}

	public int[] getCoordinates() {
		return Arrays.copyOf(coordinates, 2);
	}

	public void setCoordinates(int[] coord) {
		coordinates = Arrays.copyOf(coord, 2);
	}

	public void setCoordinates(int y, int x) {
		setY(y);
		setX(x);
	}

	public int getX() {
		return coordinates[1];
	}

	public void setX(int x) {
		coordinates[1] = x;
	}

	public int getY() {
		return coordinates[0];
	}

	public void setY(int y) {
		coordinates[0] = y;
	}

	public String getCellType() {
		return cellType;
	}

	public void setCellType(String cT) {
		cellType = cT;
	}

	/************************************************************************************
	 * Author: Ali Taha Date: November 7th 2021
	 * 
	 * @param: int, int @return: boolean Purpose: Checks if the cell is at the given
	 *              cooridnates
	 ************************************************************************************/
	public boolean checkCoords(int y, int x) {
		return (getY() == y && getX() == x);
	}
}