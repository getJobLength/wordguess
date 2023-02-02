package wordguess;

import java.util.Scanner;

public class WordGuessIO {
	public String upperUsername;
	public String hot;
	public String letterGuess;
	public String letterPlace; 
	
	
	public void getPlayerName() { 
		String username;
		System.out.print("Wat is je naam? : ");
		//Get userinput
		Scanner playerName = new Scanner(System.in); 
		username = playerName.nextLine();
	    this.upperUsername = username.substring(0, 1).toUpperCase() + username.substring(1); 
	}
	
	public void getHeadorTail() {
		System.out.print("Wil je kop of munt? : ");
		//Get userinput
		Scanner userInp = new Scanner(System.in);
		hot = userInp.nextLine(); 
		System.out.println("Je hebt gekozen voor: " + hot + "."); 
	}
	
	public void getEnterToContinue() {
		Scanner enterCheck = new Scanner(System.in);
		enterCheck.nextLine();
	}
	
	public void getPlayerGuess() {
		System.out.println("Raad een letter of het woord : ");
		Scanner playerGuess = new Scanner(System.in); 
		letterGuess = playerGuess.nextLine().toUpperCase();  
	}
	
	public String getPlaceLetterHuman() {
		Scanner placeLetterGetter = new Scanner(System.in);
		return letterPlace = placeLetterGetter.nextLine(); 
	}
	
}
