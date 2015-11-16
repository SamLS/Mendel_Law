/* Samantha Sturges
 * Bioinformatics Algorithms
 * All programs are calculated using problems from rosalind.info
 * This problem is located at http://rosalind.info/problems/iprb/
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile
{
	String fileToString() throws IOException
	{
		//Gets user input for a file
		System.out.println("Type file name with txt extension: ");
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		String fileName = br.readLine();

		//Reads in file line by line and stores file as a StringBuffer
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

		StringBuffer str = new StringBuffer();
		String line = null;

		while((line = bufferedReader.readLine()) != null)
		{
			str.append(line).append("\n");
		}

		bufferedReader.close();

		return str.toString();
	}
}