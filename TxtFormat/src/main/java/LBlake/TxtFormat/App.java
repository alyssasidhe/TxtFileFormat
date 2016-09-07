package LBlake.TxtFormat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import LBlake.TxtFormat.Format.FileTextFormat;
import LBlake.TxtFormat.InputOutput.FileInputOutput;
import LBlake.TxtFormat.WordCount.MostCommonRepeatedWords;

public class App {

	/**
	 * The main function takes in a string that represents the file to be
	 * loaded.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Handles getting data from the file and writing the formatted text to
		// the output file.
		FileInputOutput fileIO = new FileInputOutput();

		// If there is a second arg in args, the output file will be written to
		// a file with that file name.
		String outputFileName = "formatted" + args[0];
		if (args.length > 1) {
			outputFileName = args[1];
		}
		
		// Handles formatting text provided by the FileInputOutput class.
		FileTextFormat format = new FileTextFormat();

		// Handles counting the most common words.
		MostCommonRepeatedWords wordCount = new MostCommonRepeatedWords();

		// Holds the text from the file
		ArrayList<String> input = new ArrayList<String>();

		// Holds the formatted text
		ArrayList<String> output = new ArrayList<String>();

		// Tries to get data from the file in args. If it fails, the program
		// displays an exception and exits.
		try {
			input = fileIO.GetFileContents(args[0]);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// Calls the FileTextFormat class to format the text.
		output = format.FormatFileText(input);

		// Calls the output function from fileIO to put the formatted text into
		// a new file. The output file will by default have the same name as the
		// input filename, but with the word "formatted" in front of it. If
		// there is a second arg in the command line that will be the file name.
		try {
			fileIO.OutputFileContents(output, outputFileName);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// Create a LinkedHashMap to hold the most common words and their counts
		LinkedHashMap<String, Integer> commonWords = new LinkedHashMap<String, Integer>();

		// Call MostRepeatedWords and store the output in commonWords
		commonWords = wordCount.MostRepeatedWords(input);

		// create an iterator to iterate through the common words
		Iterator<Entry<String, Integer>> it = commonWords.entrySet().iterator();
		// Goes through the iterator and displays the most common words and
		// their word counts
		while (it.hasNext()) {
			Entry<String, Integer> pair = (Entry<String, Integer>) it.next();
			System.out.println(pair.getKey() + ":" + pair.getValue());
			it.remove();
		}
	}
}
