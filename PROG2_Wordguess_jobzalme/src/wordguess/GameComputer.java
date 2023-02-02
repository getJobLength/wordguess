package wordguess;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.StyledEditorKit.ForegroundAction;

public class GameComputer {
	WordGuessIO wgio = new WordGuessIO();
	Words words = new Words();
	GameHuman gh = new GameHuman();
	Random random = new Random();
	private String pcMsg;
	private String word;
	private ArrayList<Character> pcGuesses = new ArrayList<>();
	private ArrayList<Character> wrongGuesses = new ArrayList<>();
	private ArrayList<Character> alphabet = new ArrayList<>();
	private ArrayList<Character> randomWord = new ArrayList<>();
	private ArrayList<String> wordList;
	private ArrayList<String> letters;

	public GameComputer() {
		wordList = words.getAllWords();
		letters = new ArrayList<String>();
	}

	private char[] yourWord;
	private String alphabetArray = "abcdefghijklmnopqrstuvwxyz";
	private int correctCount = 0;
	private int wrongCount = 0;
	private int inputCounter = 0;
	private char chosenLetter;
	private int playerInput = 0;

	public int getScore() {
		return inputCounter;
	}

	public void play(String upperUsername) {
		pcMsg = " ---------------------------------------------------------\r\n"
				+ "| De COMPUTER gaat nu raden.                              |\r\n"
				+ "| Neem een 12-letterwoord uit de lijst in gedachten.      |\r\n"
				+ "| De computer gaat letters raden (of het hele woord).     |\r\n" + "| " + upperUsername
				+ ", jij antwoord met posities,                         |\r\n"
				+ "| gescheiden door spaties (vb: 3 9 10)                    |\r\n"
				+ "| of '-' als de letter niet voorkomt.                     |\r\n"
				+ " ---------------------------------------------------------\r\n" + "[ENTER] om door te gaan. ";
		System.out.println(pcMsg);
		wgio.getEnterToContinue();

		// Create alphabet for pc
		for (int i = 0; i < alphabetArray.length(); i++) {
			alphabet.add(alphabetArray.charAt(i));
		}

		// Store random word
		initiateRandomWord();

		System.out.println(" ");

		while (!computerLost() || playerHasWon()) {
			if (words.CHEATMODE == true) {
				System.out.println(wordList);
			}
			printCurWordState(correctCount, chosenLetter, false);
			pcGuessHumanPlace();
			printAsciiArt();
		}
		printAsciiArt();

	}

	private boolean playerHasWon() {
		int correctCount = 0;
		for (int i = 0; i < yourWord.length; i++) {
			if (yourWord[i] != 0) {
				correctCount++;
			}
		}

		if (correctCount == word.length()) {
			return true;
		}
		return false;
	}

	private boolean computerLost() {
		if (wrongCount == 10) {
			return true;
		}
		return false;
	}

	// get a randomWord
	// add it to an arrayList in char's
	// yourWord = a char with the value of the randomWord arrayList size
	private void initiateRandomWord() {
		word = words.getRandomWord();
		for (int i = 0; i < word.length(); i++) {
			randomWord.add(word.charAt(i));
		}
		yourWord = new char[randomWord.size()];

	}

	public void printCurWordState(int indexLetter, char letter, boolean isFunctionable) {
		System.out.println("Het woord is: " + word);
		System.out.println(wrongCount + " fouten " + wrongGuesses + ", aantal invoer: " + inputCounter);

		// if isFunctionable == true
		// add the letter to the index of indexLetter
		if (isFunctionable) {
			yourWord[indexLetter] = letter;

		}

		// if the word at index i is not 0 print the char at index i
		// else print a dot
		for (int i = 0; i < yourWord.length; i++) {
			if (yourWord[i] != 0) {
				System.out.print(yourWord[i]);
			} else {
				System.out.print(".");
			}

		}
		System.out.println(" ");
	}

	// Loop over wordList
	// Letter = chosenLetter in string form
	// If the word doesn't contain the letter
	// add the word to the results arrayList
	private void removeLetterFromList() {
		ArrayList<String> results = new ArrayList<String>();
		for (String word : wordList) {
			String letter = String.valueOf(chosenLetter);
			if (!word.contains(letter)) {
				results.add(word);
			}
		}
	}

	public void pcGuessHumanPlace() {

		// chosenLetter is the letter removed from the alphabet in UpperCase
		chosenLetter = alphabet.remove(random.nextInt(alphabet.size()));
		chosenLetter = Character.toUpperCase(chosenLetter);

		System.out.println("De computer raad: " + chosenLetter);
		System.out.print("Geef de (reeks) plaats(en) of '-' : ");
		String letter = wgio.getPlaceLetterHuman();

		// If player puts in "-" increase wrongCount
		// call removeLetterFromList()
		// add the pcGuess (chosenLetter) to the wrongGuesses arrayList
		// increment wrongCount and inputCounter
		// return makes sure the rest of the method wont also run, otherwise error
		// because "-" is not a number
		if (letter.equals("-")) {
			removeLetterFromList();
			wrongGuesses.add(chosenLetter);
			wrongCount++;
			inputCounter++;
			return;
		}

		// If the letter = 1 long
		// Check if the letter is in the game more than once
		// up the inputCounter make the playerInput the letter in Integer form
		// call matchInputWithWord() and pass playerInput
		if (letter.length() == 1) {

			if (containsChosenLetterMoreThanOnce()) {
				System.err.println(
						"Let op de " + chosenLetter + " kwam meer dan een keer voor dit gaat ten koste van het spel!");
			}
			inputCounter++;
			playerInput = Integer.parseInt(letter);
			matchInputWithTheWord(playerInput);
		}

		// If the letter contains a space loop over the getDoubleLetterCount() and check
		// how many indexes there are.
		// input = letter with the spaces replaced with ""
		// value = input at the location of index
		// playerInput = value but then an integer
		// up the inputCounter and pass the playerInput to the method
		// matchInputWithWord;
		// Up the index to pass through the loop again.
		if (letter.contains(" ")) {

			int index = 0;
			while (index < getDoubleLetterCount()) {
				String input = letter.replaceAll("\\s", "");
				String value = String.valueOf(input.charAt(index));
				playerInput = Integer.parseInt(value);
				inputCounter++;
				matchInputWithTheWord(playerInput);
				index++;
			}

		}
	}

	// Loop over all char's in word, if the char in word at index "i" ==
	// chosenLetter increment the counter.
	// If counter > 1 (so there are more letters) return the counter;
	// Else if the counter = 1 the input is just one letter.
	private int getDoubleLetterCount() {
		int counter = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == chosenLetter) {
				counter++;
			}
		}
		if (counter > 1) {
			return counter;
		}
		return 1;
	}

	// Loop over all char's in word, if the char in word at index "i" ==
	// chosenLetter increment the correctCount.
	// Also increment counter. If counter > 1 boolean
	// containsChosenLetterMoreThanOnce() = true.
	// Else it is false.
	private boolean containsChosenLetterMoreThanOnce() {
		int counter = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == chosenLetter) {
				correctCount++;
				counter++;
			}
		}
		if (counter > 1) {
			return true;
		}
		return false;
	}

	// If the chosenLetter (pcGuess) == the player location, print the letter on the
	// player location.
	private void matchInputWithTheWord(int playerInput2) {

		char character = randomWord.get(playerInput2);

		if (chosenLetter == character) {
			printCurWordState(playerInput2, character, true);
		}

	}

	private void printAsciiArt() {
		if (wrongCount == 0) {
			System.out.println("_________________________________________________________\r\n" + "\r\n"
					+ "                   o      o     o       o                    \r\n"
					+ "                          |    <|>     /|\\                   \r\n"
					+ "________________________________|______/_\\________________ ");
		}
		if (wrongCount == 1) {
			System.out.println("_________________________________________________________\r\n"
					+ " ,--.                                                   \r\n"
					+ "| oo |                                                   \r\n"
					+ "| ~~ |            o      o      o       o                \r\n"
					+ "|/\\/\\|                   |      |>      |>                 \r\n"
					+ "________________________________|______/_\\_______________");
		}
		if (wrongCount == 2) {
			System.out.println("_________________________________________________________\r\n"
					+ "    ,--.                                                             \r\n"
					+ "   |  oo|                                                         \r\n"
					+ "   |  ~~|          o     o      o      _o/             \r\n"
					+ "   |/\\/\\|                |     <|\\      |                  \r\n"
					+ "________________________________|_______|________________ ");
		}
		if (wrongCount == 3) {
			System.out.println("_________________________________________________________\r\n"
					+ "              ,--.                                                  \r\n"
					+ "             |  oo|                         help\r\n"
					+ "             |  {}|o     o     __o/    \\o/               \r\n"
					+ "             |/\\/\\|      |      |       |               \r\n"
					+ "________________________________/)______|________________ ");
		}
		if (wrongCount == 4) {
			System.out.println("_________________________________________________________\r\n"
					+ "                ,--.                                                 \r\n"
					+ "               |  oo|                                                \r\n"
					+ "               |  ~~|    o      __o     \\o                          \r\n"
					+ "               |/\\/\\|    |       |\\     |>                        \r\n"
					+ "_________________________________/_)____|\\_______________");
		}
		if (wrongCount == 5) {
			System.out.println("_________________________________________________________\r\n"
					+ "                    ,--.                                            \r\n"
					+ "                   |  oo|                                           \r\n"
					+ "                   |  {}|o       \\o                                \r\n"
					+ "                   |/\\/\\|         |\\      __\\o                  \r\n"
					+ "__________________________________|\\_____|)__|___________          \r\n" + "");
		}
		if (wrongCount == 6) {
			System.out.println("_________________________________________________________\r\n"
					+ "                        ,--.                                        \r\n"
					+ "                       |  oo|                                       \r\n"
					+ "                       |  ~~|      \\o/      __|                    \r\n"
					+ "                       |/\\/\\|       |         \\o                 \r\n"
					+ "___________________________________/_\\_______(_\\_________");
		}
		if (wrongCount == 7) {
			System.out.println("_________________________________________________________\r\n"
					+ "                               ,--.                                 \r\n"
					+ "                              |  oo|                                \r\n"
					+ "                              |  {}|o                               \r\n"
					+ "                              |/\\/\\||      o/__                   \r\n"
					+ "____________________________________________|_(\\_________");
		}
		if (wrongCount == 8) {
			System.out.println("_________________________________________________________\r\n"
					+ "                                    ,--.                            \r\n"
					+ "                                   |  oo|                           \r\n"
					+ "                                   |  ~~|   o__                          \r\n"
					+ "                                   |/\\/\\|   /\\                   \r\n"
					+ "____________________________________________/_|___________");
		}
		if (wrongCount == 9) {
			System.out.println("_________________________________________________________\r\n"
					+ "                                        ,--.                        \r\n"
					+ "                                       |  oo|                       \r\n"
					+ "                                       |  {}|o                      \r\n"
					+ "                                       |/\\/\\||                    \r\n"
					+ "_________________________________________________________ ");
		}
		if (wrongCount == 10) {
			System.out.println("_________________________________________________________\r\n"
					+ "  ___    __    __  __  ____     _____  _  _  ____  ____             \r\n"
					+ " / __)  /__\\  (  \\/  )( ___)   (  _  )( \\/ )( ___)(  _ \\          \r\n"
					+ "( (_-. /(__)\\  )    (  )__)     )(_)(  \\  /  )__)  )   /              \r\n"
					+ " \\___/(__)(__)(_/\\/\\_)(____)   (_____)  \\/  (____)(_)\\_)        \r\n"
					+ "_________________________________________________________");
		}

		if (correctCount == 12) {
			System.out.println("_________________________________________________________\r\n"
					+ "             ___  _____  ____    ____  ____\r\n"
					+ "            / __)(  _  )(_  _)  (_  _)(_  _)\r\n"
					+ "           ( (_-. )(_)(   )(     _)(_   )(\r\n"
					+ "            \\___/(_____) (__)   (____) (__)\r\n"
					+ "_________________________________________________________ ");
		}
	}
}
