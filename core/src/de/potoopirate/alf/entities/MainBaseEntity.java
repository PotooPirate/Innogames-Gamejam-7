package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;

import de.potoopirate.alf.components.LifeComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.TransformComponent;

public class MainBaseEntity extends Entity {

	public MainBaseEntity (int playerId) {
		add(new PlayerComponent(playerId));
		add(new LifeComponent());
		TransformComponent transform = new TransformComponent();
		add(transform);
		
	}
}
