package LBlake.TxtFormat.WordCount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class MostCommonRepeatedWords {

	/**
	 * Sorts the most repeated words and finds the top ten.
	 * 
	 * @param input
	 *            An ArrayList of strings to be have a word count.
	 * @return A LinkedHashMap with the top ten words as keys and the number of
	 *         occurences as values
	 */
	public LinkedHashMap<String, Integer> MostRepeatedWords(ArrayList<String> input) {

		// The LinkedHashMap to hold the results of the word counts.
		LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();

		// LinkedHashMap that holds the values before the top ten are selected
		// and sorted into result.
		LinkedHashMap<String, Integer> values = new LinkedHashMap<String, Integer>();

		// Get the list of words from the input file.
		ArrayList<String> words = GetWordList(input);

		// A set of strings. Because it's a set, it is all unique values.
		Set<String> uniques = new HashSet<String>(words);

		// Iterate through the uniques and find the frequency for each unique
		// string in words. Place the unique string as the key in values and
		// place the frequency of the word as the corresponding value.
		for (String key : uniques) {
			values.put(key, Collections.frequency(words, key));
		}

		

		// Find the top ten repeated words
		for (int i = 0; i < 10; i++) {
			// Create an entry to hold entries from the values map.
			Entry<String, Integer> maxEntry = null;
			
			// Get the entry with the highest value.
			for (Entry<String, Integer> entry : values.entrySet()) {
				if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
					maxEntry = entry;
				}
			}
			// Put the resulting entry into the result LinkedHashMap.
			result.put(maxEntry.getKey(), maxEntry.getValue());
			// Remove the resulting entry from values so that the next loop
			// doesn't return the same value.
			values.remove(maxEntry.getKey());
		}
		return result;
	}

	public ArrayList<String> GetWordList(ArrayList<String> input) {
		// ArrayList that holds the end result.
		ArrayList<String> result = new ArrayList<String>();
		
		//Number of characters since the last whitespace
		int lastWhitespace = 0;
		
		//Goes through the input and finds the words between whitespaces.
		for (int i = 0; i < input.size(); i++) {
			String currentString = input.get(i);
			for(int j = 0; j < currentString.length(); j++){
				
				//If there is a new whitespace, get the word between 
				if(Character.isWhitespace(currentString.charAt(j))){
					String word = GetWord(currentString.substring(j-lastWhitespace, j));
					result.add(word);
					lastWhitespace = 0;
					continue;
				}
				lastWhitespace++;
			}
		}
		return result;
	}
	
	public String GetWord(String string){
		//Remove any non-alphabetic characters from the string
		string = string.replaceAll("[^A-Za-z-]", "");
		return string;
	}

}
