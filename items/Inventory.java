/**********************************************************************************
 * Author: Ali Taha                     Date: November 4th 2021 - 
 * 
 * An inventory of slots for the player or the aliens
 **********************************************************************************/
package items;

import java.util.ArrayList;
import java.util.Arrays;

public class Inventory {

    // Class Variables
    private ArrayList<Slot> slots; // Holds the item slots
    private int maxLength;
    private int pointer; // points at a specific item
    private int rowLength; // The number of items in each row when the inventory is displayed

    // Constructors
    public Inventory(int max) {
        slots = new ArrayList<>();
        maxLength = max;
        pointer = 0;
        rowLength = 3;
    }

    public Inventory(int max, Slot... initItems) {
        slots = new ArrayList<>();
        maxLength = max;
        pointer = 0;
        rowLength = 3;

        slots.addAll(Arrays.asList(initItems)); // Add initial items to the array
    }

    // get/set
    public int getMaxLength() {
        return maxLength;
    }

    public void addToMaxLength(int add) {
        maxLength += add;
    }

    public int getLength() {
        return slots.size();
    }

    public int getRowLength() {
        return rowLength;
    }

    public void setRowLength(int row) {
        rowLength = row;
    }

    public int getPointerItemPrice() {
        return slots.get(pointer).getItem().getPrice();
    }

    public int getPointerItemAmount() {
        return slots.get(pointer).getAmount();
    }

    public Item getPointerItem() {
        return slots.get(pointer).getItem();
    }

    public void resetPointer() {
        pointer = 0;
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: N/A @return: boolean Purpose: True if the inventory is full, false
     *             otherwise
     ************************************************************************************/
    public boolean isFull() {
        return slots.size() == maxLength;
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: N/A @return: boolean Purpose: Sees if the given item is in the
     *             inventory
     ************************************************************************************/
    public boolean contains(Item item) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getItem() == item)
                return true; // true if there is a match
        }

        return false; // false if no match
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: int @return: int Purpose: Removes some amount of the pointer item.
     *             Returns the amount that was removed
     * 
     *             **** Probably needs to be changed to be useful ****
     ************************************************************************************/
    public int removeItem(int amount) {
        // Get the slot that the pointer is poining at
        Slot slot = slots.get(pointer);

        // Remove the amount from the slot
        slot.addToAmount(-amount);

        int amountLeft = slot.getAmount();

        if (amountLeft > 0) {
            // If there if some left, just return the amount that was removed
            return amount;
        } else {
            // If none is left, remove the slot and return the amount that was removed
            slots.remove(pointer);
            return amount + amountLeft;
        }
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: Item, int @return: void @throws: CannotAddException Purpose: Tries to
     *               add some amount of an item to the inventory. If it was added,
     *               return true, else return false
     ************************************************************************************/
    public void addItem(Item item, int amount) {
        boolean itemInInventory = false;

        // Loop through items to see if this item is already carried
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            // If items match, add the amount
            if (slot.getItem() == item
                    && !(item.getItemType() == ItemType.WEAPON || item.getItemType() == ItemType.ARMOUR)) {
                slot.addToAmount(amount);
                itemInInventory = true;
            }
        }

        // If the item wasn't already in the inventory
        if (!itemInInventory) {
            // If the item is not already there, try to add it to the inventory
            if (slots.size() < maxLength) {
                slots.add(new Slot(item, amount));
            } else {
                // If there is no space, return false
                throw new CannotAddException("There is no space in the inventory");
            }
        }
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: String @return: void @throws: IllegalArgumentException,
     *                IndexOutOfBoundsException Purpose: Moves the pointer in the
     *                desired direction with WASD
     ************************************************************************************/
    public void movePointer(String direction) throws IllegalArgumentException, IndexOutOfBoundsException {
        int pointerAdd; // Value that is needed to be added to pointer to move in the wanted direction

        // Set pointer Add
        switch (direction) {
        case "w":
            pointerAdd = -3;
            break;
        case "a":
            pointerAdd = -1;
            break;
        case "s":
            pointerAdd = 3;
            break;
        case "d":
            pointerAdd = 1;
            break;
        default:
            throw new IllegalArgumentException();
        }

        // Check to see that moving the pointer there is not out of bounds
        int tempPointer = pointer + pointerAdd;
        slots.get(tempPointer);

        // Move pointer
        pointer = tempPointer;
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: N/A @return: String Purpose: Displays what's in the inventory.
     *             Includes the pointer. 0 - amounts 1 - prices 2 - amounts and
     *             prices
     ************************************************************************************/
    public String display(int showPrices) {

        if (slots.isEmpty() && showPrices == 1)
            return "The store's empty, come back in 3-5 business days\n\n";
        else if (slots.isEmpty())
            return "There's nothing in here.\n\n";

        StringBuilder output = new StringBuilder();
        boolean pointerInThisRow = false;

        // Loop through all the rows
        for (int i = 0; i <= slots.size() / rowLength; i++) {
            // Loop through items in the row
            for (int j = 0; j < rowLength; j++) {

                int index = rowLength * i + j; // index of the slot we're looking at

                Slot slot = null;

                try {
                    slot = slots.get(index); // Get the slot we're referring to
                } catch (IndexOutOfBoundsException e) {
                    continue; // Skip if its out of bounds
                }

                // Format what the item will look like
                String itemString = null;

                switch (showPrices) {
                case 0:
                    itemString = itemWithAmount(slot);
                    break;
                case 1:
                    itemString = itemWithPrice(slot);
                    break;
                case 2:
                    itemString = itemWithAmount(slot);
                    itemString = "$" + slot.getItem().getPrice() + " - " + itemString;
                    break;
                default:
                    itemString = "ERROR";
                }

                if (pointer == index) {
                    pointerInThisRow = true;
                    itemString = "-> " + itemString;
                }

                // Add item to output
                output.append(String.format("%25s", itemString));
            } // End of item in row loop

            // Make a new line each row
            output.append("\n\n");

            // Print the description of the object if the pointer was in the previous row
            if (pointerInThisRow) {
                pointerInThisRow = false;

                output.append(slots.get(pointer).getItem().getDescription());
                output.append("\n\n");
            }
        } // End of row loop

        return output.toString();
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: Slot @return: String Purpose: Returns the item with its amount
     ************************************************************************************/
    private String itemWithAmount(Slot slot) {
        return slot.getAmount() + " x " + slot.getItem().getName();
    }

    /************************************************************************************
     * Author: Ali Taha Date: November 7th 2021
     * 
     * @param: Slot @return: String Purpose: Returns the item with its price
     ************************************************************************************/
    private String itemWithPrice(Slot slot) {
        return "$" + slot.getItem().getPrice() + " - " + slot.getItem().getName();
    }
}