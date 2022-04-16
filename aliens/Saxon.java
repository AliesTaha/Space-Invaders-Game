/**********************************************************************************
 * Author: Ali Taha                     Date: November 3rd 2021 - November 3rd 2021
 * 
 * The first alien race that the player encounters. Planets: Ceres
 **********************************************************************************/
package aliens;

public class Saxon extends AlienRace {

	public Saxon() {
		// Set appearances for this alien race using construtcor
		super(
				// normal
				"        _..._\n" + "      .'     '.\n" + "     / \\     / \\\n" + "    (  |     |  )\n"
						+ "    (`\"`  \"  `\"`)\n" + "     \\         /\n" + "      \\  ___  /\n" + "       '.___.'",
				// happy
				"        _..._\n" + "      .'     '.\n" + "     / \\     / \\\n" + "    (  |     |  )\n"
						+ "    (`\"`  \"  `\"`)\n" + "     \\         /\n" + "      \\  \\__  /\n" + "       '.___.'",
				// sad
				"        _..._\n" + "      .'     '.\n" + "     / \\     / \\\n" + "    (  |     |  )\n"
						+ "    (`\"`  \"  `\"`)\n" + "     \\    _    /\n" + "      \\  / \\  /\n" + "       '.___.'",
				// angry
				"        _..._\n" + "      .'     '.\n" + "     / \\     / \\\n" + "    (    \\ /    )\n"
						+ "    (`\"`  \"  `\"`)\n" + "     \\         /\n" + "      \\  ___  /\n" + "       '.___.'",
				// questioning
				"        _..._\n" + "      .'     '.\n" + "     / \\      _\\\n" + "    (  |     /  )\n"
						+ "    (`\"`  \"  `\"`)\n" + "     \\         /\n" + "      \\  ___  /\n" + "       '.___.'");
	}
}
