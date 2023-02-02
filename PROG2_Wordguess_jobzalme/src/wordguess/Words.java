package wordguess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Ger Saris
 * @version 1.0
 * @date 23-12-2021
 * 
 **/

public class Words {

	// constants
	static final int WORDLENGTH = 12;
	static final boolean CHEATMODE = true;

	// instance variables
	private ArrayList<String> wordlist = new ArrayList<>();
	private Random random = new Random();

	// constructor
	public Words() {
		try {
			Scanner inscan = new Scanner(
					new File("resources/woordenlijst_12.txt"));
			while (inscan.hasNextLine()) {
				wordlist.add(inscan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getRandomWord() {
		int index = random.nextInt(wordlist.size());
		return (wordlist.get(index));
	}

	public ArrayList<String> getAllWords() {
		return wordlist;
	}

}
