package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;

import de.potoopirate.alf.components.PathComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.RaceComponent;

public class Animal extends Entity {
	public Animal(int playerId) {
		add(new RaceComponent());
		add(new PathComponent());
		add(new PlayerComponent(playerId));
	}
}
