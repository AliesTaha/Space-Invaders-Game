/**********************************************************************************
 * Author: Ali Taha                     Date: November 7th 2021
 * 
 * Exception thrown if an item cannot be added to the inventory
 **********************************************************************************/
package items;

public class CannotAddException extends RuntimeException {
    CannotAddException(String message) {
        super(message);
    }
}