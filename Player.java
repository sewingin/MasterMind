package MM;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents the player of the game.
 * 
 * @author Ingo Sewing
 * @date 21.12.2022
 *
 */
@SuppressWarnings("serial")
public class Player implements Serializable{
	
	/**
	 * The players name.
	 */
	private String name;
	
	/**
	 * The current local date.
	 */
	private LocalDate date;
	
	/**
	 * The duration the player needed for the successful game play.
	 */
	private String duration;
	
	/**
	 * The number of the attempt's the player needed for the succesful game play. 
	 */
	private int numOfTrys;
	
	/**
	 * Construct's a player. 
	 * 
	 * @param name The player's name.
	 * @param date The current date.
	 * @param duration The duration the player needed.
	 * @param numOfTrys The number of the attempt's the player needed.
	 */
	public Player(String name, LocalDate date, String duration, int numOfTrys) {
		this.name = name;
		this.date = date;
		this.duration = duration;
		this.numOfTrys = numOfTrys;
	}
	
	/**
	 * Return's the player's name.
	 * 
	 * @return The name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the local date.
	 * 
	 * @return The date of the game play.
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * The duration which the player needed for the game.
	 * 
	 * @return The duration.
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Returns the number of game attempt's the player needed.
	 * 
	 * @return The number of game attempt's.
	 */
	public int getNumOfTrys() {
		return numOfTrys;
	}
}
