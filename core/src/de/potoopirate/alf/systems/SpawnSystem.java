package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Array;

import de.potoopirate.alf.Network;
import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.entities.MainBaseEntity;
import de.potoopirate.alf.interfaces.SpawnListener;

public class SpawnSystem extends EntitySystem implements SpawnListener {

	private Engine engine;
	private Array<Entity> animalQueue;
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		this.engine = engine;
		animalQueue = new Array<Entity>();
		engine.addEntity(new MainBaseEntity(0));
		engine.addEntity(new MainBaseEntity(1));
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		for(Entity e : animalQueue) {
			engine.addEntity(e);
		}
		animalQueue.clear();
	}

	@Override
	public void spawnAnimal(int player, int lane, int animalType) {
		switch(animalType) {
			case Network.ANIMAL_HIPPO:
				animalQueue.add(AnimalEntity.createHippo(player,lane));
				break;
			case Network.ANIMAL_OCTO:
				animalQueue.add(AnimalEntity.createOcto(player,lane));
				break;
			case Network.ANIMAL_SNAIL:
				animalQueue.add(AnimalEntity.createSnail(player,lane));
				break;
		}
	}
	
}
