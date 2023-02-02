package wordguess;

import java.util.Random;


public class WordguessGame {
	WordGuessIO wgio = new WordGuessIO(); 
	GameHuman gh = new GameHuman(); 
	GameComputer gc = new GameComputer();
	private boolean computerIsNext; 
	
	public void start() {
	String welcomemsg = "=========================================================\r\n" + 
			"| Welkom bij Wordguess!                                 |\r\n" + 
			"=========================================================\r\n" + 
			"| Je gaat tegen de computer spelen.                     |\r\n" + 
			"| Het doel is zo snel mogelijk een woord te raden.      |\r\n" + 
			"| Het 12-letterwoord moet gekozen worden uit de lijst.  |\r\n" + 
			"| Jij raadt een woord van de computer en andersom.      |\r\n" + 
			"| Wie het snelst het woord raadt wint.                  |\r\n" + 
			"| Bij een gelijkspel spelen we nog een ronde.           |\r\n" + 
			"=========================================================";
	
	System.out.println(welcomemsg);
	wgio.getPlayerName();
	System.out.println("Welkom " + wgio.upperUsername + "!");
	doToss(); 
	if (computerIsNext) {
		gc.play(wgio.upperUsername);
	} else {
		gh.play(wgio.upperUsername);
	}
	printScore(gh.getScore(), gc.getScore(), wgio.upperUsername );

	
	}
	
	//Coin toss method
	private void doToss() {
		Random random = new Random();
		String kop = "Kop";
		String munt = "Munt";
		String result = ""; 
		String[] resultArray = new String[5];
		int kopCounter = 0; 
		int muntCounter = 0; 
		int resultCounter = 0; 
		
		System.out.println("Eerst doen we een TOSS om wie mag beginnen.");
		wgio.getHeadorTail();
		System.out.println("Er wordt 5 keer gegooid. De kant die het meeste\r\n" + 
				"keer gegooid wordt, mag beginnen.");
		
		System.out.print("Flipping: ");
		
		//Toss and put results in array
		for (int i = 0; i < 5; i++) {
			int r = random.nextInt(2);
			
			if(r == 0 && resultArray[i] == null) {
				 result = kop; 
				 resultArray[i] = kop; 
			} else if(r == 1 && resultArray[i] == null) {
				 result = munt; 
				 resultArray[i] = munt; 
			}
			System.out.print(result + " | ");
		}
		System.out.println("");
		
		//Read results in array and count
		for (int i = 0; i < resultArray.length; i++) {
			//System.out.println(resultArray[i]);
			
			//up kopcounter or muntcounter if they are in array[i]
			if(resultArray[i] == kop) {
				kopCounter++; 
			} else if(resultArray[i] == munt) {
				muntCounter++; 
			}
		}
		//Show kop and munt counter for testing purposes
		//System.out.println("Kopcounter: " + kopCounter); 
		//System.out.println("Muntcounter: " + muntCounter);
		
		//If kopCounter is higher than muntCounter result is kop other wise muntCounter is higher.
		//Set the higher counter to resultCounter
		if(kopCounter > muntCounter) {
			result = kop; 
			resultCounter = kopCounter; 
		} else {
			result = munt; 
			resultCounter = muntCounter; 
		}
		
		//If userinput = result user wins, otherwise pc wins. 
		if(wgio.hot.equalsIgnoreCase(result)) {
			System.out.println("Het is geworden: " + result + " (" + resultCounter + " keer gerold)");
			System.out.println(wgio.upperUsername + " jij mag beginnen!");
			gh.play(wgio.upperUsername); 
			computerIsNext = true;
			
		} else {
			System.out.println("Het is geworden: " + result + " (" + resultCounter + " keer gerold)");
			System.out.println("De computer mag beginnen!");
			gc.play(wgio.upperUsername);
		}
		
		
		
	}
	
	public void printScore(int playerCount, int computerCount, String name) {
		if(Words.CHEATMODE == true) {
			playerCount = computerCount; 
		}
		
		if (playerCount > computerCount) {
			System.out.println("Gefeliciteerd je hebt gewonnen, " + name + "!");
			System.out.println("Jouw score was: " + gh.getScore());
			System.out.println("De computer zijn score is: " + gc.getScore());
			System.out.println("Gelijkspel! Druk op [ENTER] om het spel te beëindigen.");
			wgio.getEnterToContinue();
			return;
		} else if (playerCount == computerCount){
			System.out.println("Jouw score was: " + gh.getScore());
			System.out.println("De computer zijn score is: " + gc.getScore());
			System.out.println("Gelijkspel! Druk op [ENTER] om het spel opnieuw te spelen.");
			wgio.getEnterToContinue();
			doToss();
			if (computerIsNext) {
				gc.play(wgio.upperUsername);
			} else {
				gh.play(wgio.upperUsername);
			}
			return;
		}
		System.out.println("Jammer je hebt verloren!");
		System.out.println("Jouw score was: " + gh.getScore());
		System.out.println("De computer zijn score is: " + gc.getScore());
		System.out.println("Gelijkspel! Druk op [ENTER] om het spel te beëindigen.");
		wgio.getEnterToContinue();
		
	}

}