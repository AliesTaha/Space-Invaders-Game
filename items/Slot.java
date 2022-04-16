/**********************************************************************************
 * Author: Ali Taha                     Date: November 4th 2021 - 
 * 
 * The slot that items are held in the inventory. Contains the items and the number of those items in the inventory.
 **********************************************************************************/
package items;

public class Slot {
    Item item;
    int amount;

    public Slot(Item i, int a) {
        item = i;
        amount = a;
    }

    // get/set
    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    // Returns true if there is still some items left after adding/subtracting the
    // amount, otherwise returns false
    public boolean addToAmount(int x) {
        amount += x;
        return amount > 0;
    }
}
