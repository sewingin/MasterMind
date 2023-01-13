package MM;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class save's and load's the player's to a file.
 * 
 * @author Ingo Sewing
 * @date 22.12.2022
 *
 */
@SuppressWarnings("serial")
public class Players implements Serializable{

	/**
	 * This list contains the player's.
	 */
	ArrayList<Player> players;
	
	/**
	 * Set the player's list.
	 * 
	 * @param players The list of player's to set.
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * Get the player's list.
	 * 
	 * @return Return the player's list.
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Construct's the player's list.
	 */
	public Players() {
		// Ensures that the list is loaded from file
		players = loadPlayers();
	}
	
	/**
	 * Add a player to the player's list.
	 * 
	 * @param name The name of the player to add.
	 * @param date The date the player played the game.
	 * @param duration The duration of the game the player played.
	 * @param numOfTrys The number of attempts the player needed.
	 */
	public void addPlayer(String name, LocalDate date, String duration, int numOfTrys) {
		
		Player player = new Player(name, date, duration, numOfTrys);
		players.add(player);
	}
	
	/**
	 * Load the player's into the player's list.
	 * <p>
	 * Load's the player's list from file "Score.dat", if there is no file a
	 * new player's list is created.
	 * 
	 * @return The player's list.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Player> loadPlayers(){
		try{
			//Creating stream to read the object  
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Score.dat"));
			players = (ArrayList<Player>)(in.readObject());
			in.close();
		}
		catch(Exception e){
			players = new ArrayList<Player>();
		}
		return players;
	}

	/**
	 * Save's the player list into the file "Score.dat".
	 */
	public void savePlayers() {
		
		try {
			FileOutputStream fout = new FileOutputStream("Score.dat");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			
			out.writeObject(players);
			out.flush();
			//closing the stream    
			out.close();
			fout.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
