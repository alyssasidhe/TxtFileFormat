package LBlake.TxtFormat.InputOutput;

import java.util.*;
import java.io.*;  

public class FileInputOutput {
	
	/**
	 * This function will locate a txt file and load the lines into an ArrayList. Each line will become a string
	 * in the ArrayList.
	 * @param filename The filename passed as a parameter when invoking the executable. File should be in the working directory.
	 * @return
	 * @throws FileNotFoundException 
	 */
	public ArrayList<String> GetFileContents(String filename) throws FileNotFoundException, IOException {
		
		//attempts to load the file into memory
		File file = new File(filename);
		
		//Loads file contents into a scanner
		Scanner scanner = new Scanner(file);

		//Creates an ArrayList of Strings to hold the daya
		ArrayList<String> result = new ArrayList<String>();
		
		// runs until end of file
		while(scanner.hasNextLine()) {
			//Adds each line into the result
			result.add(scanner.nextLine());
		}
		
		// closes the file streams
		scanner.close();
		
		//returns the ArrayList of Strings
		return result;
	}
	
	public void OutputFileContents(ArrayList<String> fileContents, String outputFileName) throws FileNotFoundException, UnsupportedEncodingException {
		//Create a PrintWriter object to write to the file.
		PrintWriter out = new PrintWriter(outputFileName, "UTF-8");
		
		//Create an iterator for fileContents
		Iterator<String> it = fileContents.iterator();
		
		//Go through the entirety of the iterator.
		while(it.hasNext()){
			out.println(it.next());
		}
		
		//Close the writer.
		out.close();
	}
}
