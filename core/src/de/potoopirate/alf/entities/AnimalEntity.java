package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;

import de.potoopirate.alf.components.PathComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.RaceComponent;
import de.potoopirate.alf.components.RaceComponent.Race;
import de.potoopirate.alf.components.SoundComponent;
import de.potoopirate.alf.components.TransformComponent;

public class AnimalEntity extends Entity {
	public AnimalEntity(int playerId, Race race, String sound) {
		add(new RaceComponent(race));
		add(new PathComponent());
		add(new PlayerComponent(playerId));
		add(new TransformComponent());
		add(new SoundComponent(sound));
	}
	
	public static AnimalEntity createSnail(int playerId) {
		return new AnimalEntity(playerId, Race.SNAIL, SoundComponent.SNAIL_SOUND);
	}

	public static AnimalEntity createHippo(int playerId) {
		return new AnimalEntity(playerId, Race.HIPPO, SoundComponent.HIPPO_SOUND);
	}

	public static AnimalEntity createOcto(int playerId) {
		return new AnimalEntity(playerId, Race.OCTO, SoundComponent.OCTO_SOUND);
	}
}
