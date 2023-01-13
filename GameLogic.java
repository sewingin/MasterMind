package MM;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

/**
 * This class contains the game logic
 * 
 * In this class the random game is generated and the player'
 * game attempt is checked and the result is returned.
 * 
 * @author Ingo Sewing
 * @date 20.12.2022
 *
 */
public final class GameLogic {

	/**
	 * Char contains all possible game choices to generate the game.
	 */
	final private static char[] choice = {'r','y','b','g','m','c'};
	
	/**
	 * Save's the random selection the player have to guess.
	 */
	private static char[] selection = new char[4];
	
	/**
	 * Saves the start time of the game.
	 */
	private static LocalTime start;
	
	/**
	 * Saves the end time of the game.
	 */
	private static LocalTime end;
	
	/**
	 * Constructor 
	 */
	private GameLogic() {
	}
	
	/**
	 * This funktion calculate's the Game.
	 */
	public static void calculateGame() {
		Random cube = new Random();
		for(int i=0; i<4; i++ ) {
			selection[i] = choice[cube.nextInt(6)];
		} 
		start = LocalTime.now();
	}
	
	/**
	 * Check's the players game attempt. 
	 * 
	 * @param attempt The player's game attempt.
	 * @return Return the result of the player's game guess.
	 */
	public static char[] checkGameTry(char[] attempt) {
		
		char[] result = new char[] {'n','n','n','n'};
		int white=0, black=0;
		int red=0, redTry=0;
		int yellow=0, yellowTry=0;
		int blue=0, blueTry=0;
		int green=0, greenTry=0;
		int magenta=0, magentaTry=0;
		int cyan=0, cyanTry=0;
		
		//count number of color occurrences in selection and game try
		for(int i = 0; i < 4; i++) {
			
			if (attempt[i]==selection[i]) {
				black++;
				continue;
			}
			switch (selection[i]) {
				case 'r':
					red++;
					break;
				case 'y':
					yellow++;
					break;
				case 'b':
					blue++;
					break;
				case 'g':
					green++;
					break;
				case 'm':
					magenta++;
					break;
				case 'c':
					cyan++;
					break;
			}
			
			switch (attempt[i]) {
				case 'r':
					redTry++;
					break;
				case 'y':
					yellowTry++;
					break;
				case 'b':
					blueTry++;
					break;
				case 'g':
					greenTry++;
					break;
				case 'm':
					magentaTry++;
					break;
				case 'c':
					cyanTry++;
					break;
			}
		}
		
		//check number of white answers
		if(redTry<=red) {
			white+=redTry;
		}
		else {
			white+=red;
		}
		
		if(yellowTry<=yellow) {
			white+=yellowTry;
		}
		else {
			white+=yellow;
		}
		
		if(blueTry<=blue) {
			white+=blueTry;
		}
		else {
			white+=blue;
		}
		
		if(greenTry<=green) {
			white+=greenTry;
		}
		else {
			white+=green;
		}
		
		if(magentaTry<=magenta) {
			white+=magentaTry;
		}
		else {
			white+=magenta;
		}
		
		if(cyanTry<=cyan) {
			white+=cyanTry;
		}
		else {
			white+=cyan;
		}
		
		//make result array and avoid result positions are in same positions in game try
		for(int i=0; i < white; i++) {
			result[i]='w';
		}
		for(int i=white; i < (white + black); i++) {
			result[i]='b';
		}
		return result;
	}

	/**
	 * Compute's the duration of the game.
	 * 
	 * @return The formated (hh.mm.ss) duration of the game.
	 */
	public static String duration() {
		end = LocalTime.now();
		Duration duration = Duration.between(start, end);
		long HH = duration.toHours();
		long MM = duration.toMinutesPart();
		long SS = duration.toSecondsPart();
		String timeInHHMMSS = String.format("%02d:%02d:%02d", HH, MM, SS);
		
		return timeInHHMMSS;
	} 
}
