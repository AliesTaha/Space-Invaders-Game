/**********************************************************************************
 * Author: Ali Taha                     Date: October 22nd 2021 - ???
 * 
 * A user interface for Galactic Capitalism. This version of the class
 * outputs everything to the console.
 * 
 * ----Methods:--- (Outdated)

 * General:
 * + display(String): void
 * + dialogue(String): void
 * + userMenu(String, String...): String
 * 
 * 
 * User Input:
 * - userInputString(String, String...): String
 * - userInputInt(String, int, int): int
 * - userInputAnyString(String): String
 * 
 * Misc:
 * - isUpperCase(String): boolean
 * - formatQuestion(String, String...): String
 **********************************************************************************/
package main;

import java.io.*;
import java.util.Arrays;

public class GalacticUI {

  // Constants
  public static final String UNIMPED = "Sorry, that's unimplemented right now. Try something else.";
  public static final String NOTHING = "Mysteriously, nothing happened.";

  // Constructor
  private GalacticUI() {
  } // Makes sure that this class can never be instantiated

  /************************************************************************************
   * Author: Ali Taha Date: October 22nd 2021
   * 
   * @param: String @return: void Purpose: Output the given string to the console
   *                with a new line.
   ************************************************************************************/
  public static void display(String message) {
    System.out.println(message);
  }

  /************************************************************************************
   * Author: Ali Taha Date: October 27th 2021
   * 
   * @param: String @return: void Purpose: Output the given string. User must go
   *                to the next piece of dialogue by typing next.
   ************************************************************************************/
  public static void dialogue(String message) {
    userMenu(message, "Next - N");
  }

  /************************************************************************************
   * Author: Ali Taha Date: November 7th 2021
   * 
   * @param: String @return: void Purpose: Output the given string. User must go
   *                back to the last menu by typing back.
   ************************************************************************************/
  public static void popup(String message) {
    userMenu(message, "Back - B");
  }

  /************************************************************************************
   * Author: Ali Taha Date: October 22nd 2021 - October 24th 2021
   * 
   * @param: String, String... @return: String Purpose: A general purpose menu for
   *                 use in other larger menus. Scans the given option strings for
   *                 words in all caps and set those as the valid menu options.
   *                 When the user enters a valid answer to the message, returns
   *                 it in lowercase.
   ************************************************************************************/
  public static String userMenu(String message, String... options) {

    String[] responses = new String[options.length]; // Array the holds the valid resonse from the user

    // Find Captialized words in each option and set it as the response. If no
    // capitalized word, the first word is used.
    for (int i = 0; i < options.length; i++) {

      String opt = options[i];
      String resp = null;
      boolean firstWord = true;
      int startWord = 0;
      int spaceIndex = opt.indexOf(' '); // Index of the first space in the string

      // Keep looping through possible words in the string until one is found that's
      // capitalized
      while (true) {
        if (spaceIndex == -1)
          spaceIndex = opt.length();

        String possibleWord = opt.substring(startWord, spaceIndex);

        // take the first word even if it isn't all capitals
        if (firstWord) {
          resp = possibleWord;
          firstWord = false;
        } else if ((!possibleWord.equals("")) && isUpperCase(possibleWord)) {
          resp = possibleWord;
          break; // breaks out of the word loop once the first capitalized word is found
        }

        if (spaceIndex == opt.length())
          break; // Break once the last word is reached
        // Get the indices of the next word
        startWord = spaceIndex + 1;
        spaceIndex = opt.indexOf(' ', startWord);
      } // end of word loop

      responses[i] = resp.toLowerCase(); // Put the last valid response in the index
    } // End of options loop

    // Format the question to ask the user w/ the options in it
    String question = formatQuestion(message, options);
    // Ask for a user response and check if it matches the valid responses
    return userInputString(question, responses).toLowerCase();
  }

  // ----------------------User Input---------------------

  /************************************************************************************
   * Author: Ali Taha Date: October 24th 2021
   * 
   * @param: String, String... @return: String Purpose: Gets the user to input one
   *                 of the given Strings, and returns it.
   ************************************************************************************/
  private static String userInputString(String message, String... responses) {

    String userString = null; // The int that the user inputs
    Arrays.sort(responses); // Sort responses so the binary search works

    while (true) {
      userString = userInputAnyString(message);

      // If the string is a valid answer, break loop
      if (Arrays.binarySearch(responses, userString.toLowerCase()) >= 0)
        break;
    }

    return userString;
  } // End Method

  /************************************************************************************
   * Author: Ali Taha Date: October 24th 2021
   * 
   * @param: String @return: String Purpose: Gets the user to input any String,
   *                and returns it
   ************************************************************************************/
  public static String userInputAnyString(String message) {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String userString = ""; // The int that the user inputs

    // Add new line in front of message to improve readability
    display("\n" + message);

    // Read from user input
    try {
      userString = in.readLine();
    } catch (Exception e) {
      userString = ""; // If something goes wrong, just make the program try again
    }

    // Don't return a null
    if (userString != null)
      return userString;
    else
      return "";
  } // End Method

  /************************************************************************************
   * Author: Ali Taha Date: September 14th 2021 (Modified October 18th 2021)
   * 
   * @param: String, int, int @return: int Purpose: Get the user to input an
   *                 integer between and min and max. (inclusive)
   ************************************************************************************/
  public static int userInputInt(String message, int min, int max) {

    String userString = null;
    int userInt = 0; // The int that the user inputs

    while (true) {
      userString = userInputAnyString(message);

      // Check if the input is an Integer
      try {
        userInt = Integer.parseInt(userString);
      } catch (NumberFormatException e) {
        continue;
      }

      // If the int is between the min and the max, break the loop
      if (userInt <= max && userInt >= min)
        break;
    }

    return userInt;
  } // End Method

  // ----------------------Misc Methods-----------------------------------------

  /*****************************************************************
   * Author: Ali Taha Date: October 24th 2021
   * 
   * @param: String @return: boolean
   * 
   *                Purpose: Checks if the string is all upper case
   *****************************************************************/
  private static boolean isUpperCase(String str) {
    boolean allCapitals = true;

    // Loop through characters in the word and check they're all capitals
    for (int j = 0; j < str.length(); j++) {
      // Check that all the letters is the possible word are capitals
      if (str.charAt(j) < 'A' || str.charAt(j) > 'Z') {
        allCapitals = false;
        break;
      }
    }

    return allCapitals;
  }

  /*****************************************************************
   * Author: Ali Taha Date: October 24th 2021
   * 
   * @param: String, String... @return: String
   * 
   *                 Purpose: Given a questions and a list of options, it formats
   *                 a String with the questions, a space, and then the options
   *                 sequentially. There is no newLine character at the end of the
   *                 question.
   *****************************************************************/
  private static String formatQuestion(String message, String... options) {
    StringBuilder output = new StringBuilder(message + "\n");
    for (int i = 0; i < options.length; i++) {
      output.append("\n" + options[i]);
    }

    return output.toString();
  }
}