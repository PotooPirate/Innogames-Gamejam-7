package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;

import de.potoopirate.alf.components.PathComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.RaceComponent;
import de.potoopirate.alf.components.RaceComponent.Race;

public class AnimalEntity extends Entity {
	public AnimalEntity(int playerId, Race race) {
		add(new RaceComponent(race));
		add(new PathComponent());
		add(new PlayerComponent(playerId));
	}
	
	public static AnimalEntity createSnail(int playerId) {
		return new AnimalEntity(playerId, Race.SNAIL);
	}

	public static AnimalEntity createHippo(int playerId) {
		return new AnimalEntity(playerId, Race.HIPPO);
	}

	public static AnimalEntity createOcto(int playerId) {
		return new AnimalEntity(playerId, Race.OCTO);
	}
}
