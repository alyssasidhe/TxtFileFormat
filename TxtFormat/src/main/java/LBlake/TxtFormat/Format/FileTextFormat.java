package LBlake.TxtFormat.Format;

import java.util.ArrayList;

/**
 * This class formats the text according to the rules set down in the design
 * document.
 * 
 * @author LBlake
 *
 */
public class FileTextFormat {

	/**
	 * This function formats the text according to the design document.
	 * 
	 * @param input
	 *            This input is the information loaded from the file.
	 * @return Returns the formatted array list.
	 */
	public ArrayList<String> FormatFileText(ArrayList<String> input) {
		// Counts the number of characters since the last new line.
		int characters = 0;

		// Counts the number of characters since the last whitespace.
		int lastWhitespace = 0;

		// Used to determine if a new sentence was started to determine if a
		// letter should be capitalized.
		boolean newSentence = true;

		// Counts the number of sentences since the last carriage return.
		int sentences = 0;

		// This ArrayList holds data during formatting. After the function
		// exits, it will return the data to the input variable.
		ArrayList<String> workingData = new ArrayList<String>();

		// Starts a for loop through each line within the input ArrayList.
		for (int i = 0; i < input.size(); i++) {

			// Converts the first line from input into a character array.
			char currentCharacters[] = input.get(i).toCharArray();

			// Iterates over the character array from input.
			for (int j = 0; j < currentCharacters.length; j++) {

				// Checks the previous few characters for common abbreviations
				String checked = "";
				if (j > 4 && j < (currentCharacters.length - 1)) {
					checked = new String(currentCharacters, j - 5, 7);
				}
				// Checks if the current character is the ending punctuation for
				// a sentence.
				if (CheckNewSentence(checked, currentCharacters[j], lastWhitespace)) {
					newSentence = true;
					sentences++;

					// If the current sentence is the end of a paragraph,
					// creates a new line and inserts an additional line between
					// paragraphs.
					if (sentences == 4) {
						sentences = 0;
						// String to hold the characters.
						String string = new String();

						// Gets the beginning index
						int beginning = j - characters;

						// Gets the ending index
						int end = j;

						// Create a substring of currentLine and store it in
						// string.
						string = new String(currentCharacters, beginning, end - beginning + 1);

						// Adds the current substring to the returning
						// ArrayList.
						workingData.add(string);
						workingData.add(" ");

						// initialize characters
						characters = 0;
						lastWhitespace = 0;
						j++;
						continue;
					}
				}

				// If the number of characters is currently 80, add the
				// characters to a new line.
				if (characters == 80) {
					// String to hold the characters.
					String string = new String();

					// Gets the beginning index
					int beginning = j - characters;

					// Gets the ending index
					int end = j - lastWhitespace;

					// Create a substring of currentLine and store it in string.
					string = new String(currentCharacters, beginning, end - beginning + 1);

					// Adds the current substring to the returning ArrayList.
					workingData.add(string);

					// initialize characters
					characters = lastWhitespace;
					continue;
				} else if (j == currentCharacters.length - 1) {
					// String to hold the characters.
					String string = new String();

					// Gets the beginning index
					int beginning = j - characters;

					// Gets the ending index
					int end = j;

					// Create a substring of currentLine and store it in string.
					string = new String(currentCharacters, beginning, end - beginning + 1);

					// Adds the current substring to the returning ArrayList.
					workingData.add(string);

					// initialize characters
					characters = lastWhitespace;
					continue;
				}

				// Checks to see if a there was a recent end of sentence.
				if (newSentence) {

					// If there was a recent end of sentence, capitalize the
					// first character of the new sentence.
					if (Character.isLowerCase(currentCharacters[j])) {
						currentCharacters[j] = Character.toUpperCase(currentCharacters[j]);
						newSentence = false;
					}
				}
				// If the current character is a whitespace, start counting the
				// characters since the last whitespace.
				else if (Character.isWhitespace(currentCharacters[j])) {
					lastWhitespace = 0;
				}
				characters++;
				lastWhitespace++;
			}
		}
		return workingData;

	}

	public boolean CheckNewSentence(String checked, char currentCharacter, int lastWhitespace) {
		// create a reg ex pattern to test if there was a punctuation mark.
		String pattern = "\\.|\\!|\\?";
		// create reg ex patterns to see if the punctuation is actually at the
		// end of a sentence. For scalability there would need to be a
		// repository for new exceptions to "a period ends a sentence," but this
		// is proof of concept.
		String patterns[] = { "(.*)\\d+\\.\\d+(.*)", "(.*)\\sst\\.\\s(.*)", "(.*)degs\\.(.*)", "(.*)Mr\\.\\s(.*)" };
		if (!Character.toString(currentCharacter).matches(pattern)) {
			return false;
		} else if (lastWhitespace <= 2) {
			return false;
		}
		for (int x = 0; x < patterns.length; x++) {
			if (checked.matches(patterns[x])) {
				return false;
			}
		}
		return true;
	}

}
