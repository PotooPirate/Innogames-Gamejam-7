package de.potoopirate.alf;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.utils.Array;
import de.potoopirate.alf.components.RaceComponent.Race;

public class NaturalSelection {
	
	private static Array<Integer> availableStatus = new Array<Integer>();
	private static Map<Race, Integer> assignedStatus = new HashMap<Race, Integer>();
		
	// assign new status to all races -> has to be called before each round begins
	public static void assignNewStatus () {
		// create status number (0, 1, 2...) for each Race
		for (int i = 0; i < Race.values().length; i++)
		{
			availableStatus.add(i);
		}
		
		Random rand = new Random();
		int randomStatus;
		
		for (Race race : Race.values()) {
			randomStatus = availableStatus.get(rand.nextInt(availableStatus.size - 1));
			assignedStatus.put(race, randomStatus);
			availableStatus.removeValue(randomStatus, true);
		}
	}
	
	// get the status of a race
	public static int getStatusOfRace (Race race) {
		return assignedStatus.get (race);
	}
	
}
