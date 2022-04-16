/**********************************************************************************
 * Author: Ali Taha                     Date: November 3rd 2021 - November 3rd 2021
 * 
 * Template for the alien races in the game. Holds their apperance and attributes.
 **********************************************************************************/
package aliens;

public class AlienRace {

	// Emotions of the aliens in ASCII
	private String normal;
	private String happy;
	private String sad;
	private String angry;
	private String questioning;

	private String defaultEmotion;

	AlienRace(String n, String h, String s, String a, String q) {
		normal = n;
		happy = h;
		sad = s;
		angry = a;
		questioning = q;

		defaultEmotion = normal;
	}

	// get/sets
	public String getNormal() {
		return normal;
	}

	public String getHappy() {
		return happy;
	}

	public String getSad() {
		return sad;
	}

	public String getAngry() {
		return angry;
	}

	public String getQuestioning() {
		return questioning;
	}

	public String getDefaultEmotion() {
		return defaultEmotion;
	}

	public void setDefaultEmotion(String emotion) {
		defaultEmotion = emotion;
	}
}
