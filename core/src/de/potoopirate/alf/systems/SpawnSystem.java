package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

import de.potoopirate.alf.Network;
import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.interfaces.SpawnListener;

public class SpawnSystem extends EntitySystem implements SpawnListener {

	private Engine engine;
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = engine;
	}

	@Override
	public void spawnAnimal(int player, int lane, int animalType) {
		switch(animalType) {
			case Network.ANIMAL_HIPPO:
				engine.addEntity(AnimalEntity.createHippo(player,lane));
				break;
			case Network.ANIMAL_OCTO:
				engine.addEntity(AnimalEntity.createOcto(player,lane));
				break;
			case Network.ANIMAL_SNAIL:
				engine.addEntity(AnimalEntity.createSnail(player,lane));
				break;
		}
	}
	
}
