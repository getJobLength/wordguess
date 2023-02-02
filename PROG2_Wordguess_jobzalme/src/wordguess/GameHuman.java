package wordguess;

import java.util.ArrayList;

public class GameHuman {
	WordGuessIO wgio = new WordGuessIO();
	Words words = new Words();
	private String humanMsg;
	private String word;
	private ArrayList<Character> playerGuesses = new ArrayList<>();
	private ArrayList<Character> wrongGuesses = new ArrayList<>();
	private int correctCount = 0;
	private int wrongCount = 0;
	private int inputCounter = 0;

	public int getScore() {
		int score = correctCount + wrongCount;
		return score;
	}

	public void play(String upperUsername) {
		humanMsg = " ---------------------------------------------------------\r\n" + "| " + upperUsername
				+ " jij gaat nu raden.                                  |\r\n"
				+ "| Probeer het 12-letterwoord zo snel mogelijk te raden.    |\r\n"
				+ "| Geef per keer een letter in, of het hele woord.          |\r\n"
				+ "| Bij 10 keer een foute letter ben je af.                  |\r\n"
				+ "| Als je het woord fout raadt, krijg je 5 strafpunten.     |\r\n"
				+ "| Succes!                                                  |\r\n"
				+ " ---------------------------------------------------------\r\n" + "[ENTER] om door te gaan.\r\n";
		System.out.println(humanMsg);

		wgio.getEnterToContinue();

		// Store random word
		word = words.getRandomWord();

		while (true) {
			// Check if correctCount matches word length. If so jump out of loop and win
			// game.
			if (correctCount == word.length()) {
				printAsciiArt();
				break;
			}
			if (wrongCount >= 10) {
				printAsciiArt();
				break;
			}

			printAsciiArt();

			if (words.CHEATMODE == true) {
				System.out.println("THE SECRET WORD IS: " + word);
			}

			printCurWordState(word, playerGuesses, correctCount);

			wgio.getPlayerGuess();

			if (wgio.letterGuess.length() > 1 && wgio.letterGuess.equals(word)) {
				// TODO GOT IT
				correctCount = word.length();

			} else if (wgio.letterGuess.length() > 1 && wgio.letterGuess != word) {
				System.out.println("Het woord is fout je krijgt 5 strafpunten!");
				wrongCount = wrongCount + 5;

			} else {
				// TODO Bug array out of bounds
				playerGuesses.add(wgio.letterGuess.charAt(0));

			}

			inputCounter++;

			if (wgio.letterGuess.length() == 1 && !word.contains(wgio.letterGuess)
					&& !wrongGuesses.contains(wgio.letterGuess.charAt(0))) {
				wrongGuesses.add(wgio.letterGuess.charAt(0));
				wrongCount++;

			}

		}

	}

	public boolean printCurWordState(String word, ArrayList<Character> playerGuesses, int correctCount) {
		System.out.println(wrongCount + " fouten " + wrongGuesses + ", aantal invoer: " + inputCounter);

		// Store player guesses in ArrayList,
		// if its right put the letter at the Char spot and increment correctCount.
		// Otherwise print a "." and increment wrongCount.
		for (int i = 0; i < word.length(); i++) {
			if (correctCount == word.length() || wrongCount >= 10) {
				System.out.println("Het woord is: ");
				System.out.println(word.toUpperCase());
				break;
			} else if (playerGuesses.contains(word.charAt(i))) {
				System.out.print(word.charAt(i));
				correctCount++;
			} else {
				System.out.print(".");
			}
		}

		// Enter
		System.out.println();
		return (word.length() == correctCount);
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
