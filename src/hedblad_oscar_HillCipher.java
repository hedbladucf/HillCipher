/*
 * Name: Oscar Hedblad
 * PID: o3415424	
 * CIS3360 - 0R2
 * Program 1 - HillCiper
 * 
 * DESCRIPTION: Program that reads in a ("samplekey.txt") file and stores it as a Nx1 matrix.
 * It then reads in a plaintext ("panelnotes.txt") and splits up the text according to the 
 * size of the key matrix. It then converts the letters into numbers in order to be able to 
 * perform the necessary mathematical operations. Once the letters are split up and in digit form
 * it performs the necessary matrix multiplication and achieves new numbers. These numbers are
 * then % 26 in order to get the corresponding cipher-letter. The program then converts the numbers
 * back to letters and prints out the new ciphertext into a file ("panelnotes-enc.txt"). 
 */

// Import necessary libraries.
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class hedblad_oscar_HillCipher {
	
	public static void main(String args[]){
		
		// Scanner object that handles the user input
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the name of the file storing the key.");
		String sampleKey = scan.next();
		Scanner input;
		input = null;
		// Try-catch that catches exceptions that might be thrown.
		try{
			
			input = new Scanner(new File(sampleKey));
			
		}
		
		catch(Exception e){
			System.out.println("File storing samplekey could not be identified.");
			
		}
		// Reads the first digit and decides how large the key matrix will be
		int bulk = input.nextInt();
		// Makes keyMatrix and determines how large it is necessary to be.
		int[][] keyMatrix = new int[bulk][bulk];
		/* Nested for-loop that represents the key matrix row & column. It then
		 * reads in the integers one at a time. */
		for(int j = 0; j < bulk; j++){
			for(int k = 0; k < bulk; k++){
				keyMatrix[j][k] = input.nextInt(); 
			}
		}
		// Asking for the file containing the readable text
		System.out.println("Please enter the name of the file to encrypt.");
		String text = scan.next();
		Scanner readableInput;
		readableInput = null;
		
		try{
			readableInput = new Scanner(new File(text));
		}
		catch(Exception e){
			System.out.println("File storing plaintext could not be identified.");
		}
		// Asking for name of output file
		System.out.println("Please enter the name of the file to store the ciphertext.");
		String outputFile = scan.next();
		// Creation of an ArrayList that only accepts lower case characters.
		ArrayList<Character> readableText = new ArrayList<Character>();
		int index = 0;
		
		// Only reads in characters, ignore white space and non-alphabetical characters
		while(readableInput.hasNext()){
			// Knock away everything that isn't a character and read in each string 
			String bumper = readableInput.next();
			for(int i = 0; i < bumper.length(); i++){
				// Checks if character is indeed a letter. Then converts to lower case.
				if(Character.isLetter(bumper.charAt(i))){
					char letter = Character.toLowerCase(bumper.charAt(i));
					// Use add-function to add it to the readable plaintext
					readableText.add(index, letter);
					// Incrementing the array index
					index++;
				}
			}
		}
		while(index % bulk != 0){
			// Checks to see if padding is necessary, if so, pad with lower case 'x'
			readableText.add(index, 'x');
			index++;
		}
		// Create array for ciphed text
		int[] codedText = new int [10001];
		// Index of ciphed text
		int codeIndex = 0;
		// Extra integer to keep track of which part of the readable plaintext we are encrypting.
		int keepTrack = 0;
		
		/* Nested for-loop that iterates according three integers (first, second, third) according 
		 * to the readable plaintext, and the matrix row & column. It use the getNumericValue() function 
		 * to convert to the numerical value of the character. It then mods by 26 to get the correct character
		 * value. It then increments the index of the ciphed text. */
		for(int first = 0; first < readableText.size()/bulk; first++){
			for(int second = 0; second < bulk; second++){
				for(int third = 0; third < bulk; third++){
					codedText[codeIndex] += (keyMatrix[second][third]*(Character.getNumericValue(readableText.get(third+keepTrack))-10));
				}
				codedText[codeIndex] = codedText[codeIndex] % 26;
				codeIndex++;
			}
			keepTrack = keepTrack + bulk;
		}
		/* Creation of file writer that, based on prior user input, creates an output file 
		 * containing the encoded text. */
		FileWriter printToFile;
		try{
			printToFile = new FileWriter(outputFile);
			for(int i = 0; i < codeIndex; i++) {printToFile.write(String.valueOf((char)(codedText[i] + 97)));}
			printToFile.close();
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
		// Closes my scanners
		scan.close();
		input.close();
		readableInput.close();
	}
}

	