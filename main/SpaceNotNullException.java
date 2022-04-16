/**********************************************************************************
 * Author: Ali Taha                     Date: November 2nd 2021
 * 
 * Exception thrown if the wanted space in the space array is not null
 **********************************************************************************/
package main;

class SpaceNotNullException extends RuntimeException {
    public SpaceNotNullException(String cellType) {
        super(cellType);
    } // The type of the cell in the space that isnt null
}