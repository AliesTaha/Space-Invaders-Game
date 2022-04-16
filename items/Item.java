/**********************************************************************************
 * Author: Ali Taha                     Date: November 4th 2021 - 
 * 
 * A list of the different items in the game.
 **********************************************************************************/
package items;

public enum Item {
	APPLE(0, ItemType.HEALTH, 50, "Apple", "A crunchy apple with only two soft spots.", 1),
	PISTOL(1, ItemType.WEAPON, 200, "Laser Pistol", "Shoot from the hip and you'll never miss.", 1),
	IRON_ORE(2, ItemType.RESOURCE, 100, "Iron Ore", "Not very strong, but still useful.", 1),
	SHEET_METAL(3, ItemType.ARMOUR, 100, "Sheet Metal", "Will protect your ship from even the fastest marbles.", 1);

	private int id;
	private ItemType itemType;
	private int price;
	private String name;
	private String description;
	private int power; // For health items this is how much hp they heal, for weapons this is the
						// damage, and for armour this is the denense

	Item(int i, ItemType iT, int pr, String nm, String desc, int pw) {
		id = i;
		itemType = iT;
		price = pr;
		name = nm;
		description = desc;
		power = pw;
	}

	// General get emthods
	public int getID() {
		return id;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public int getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public int getPower() {
		return power;
	}

	public String getDescription() {

		String tempDescription = "* " + description + " *";

		// Customize description for the item type
		switch (itemType) {
		case WEAPON:
			return tempDescription + "\nDamage: " + getPower();
		case HEALTH:
			return tempDescription + "\nHP Boost: " + getPower();
		case ARMOUR:
			return tempDescription + "\nStrength: " + getPower();
		default:
			return tempDescription;
		}
	}
}