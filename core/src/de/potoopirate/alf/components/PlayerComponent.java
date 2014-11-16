package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;

import de.potoopirate.alf.components.RaceComponent.Race;

public class PlayerComponent extends Component {
	public int id;
	public Race race;
	
	public PlayerComponent(int playerId, Race race) {
		id = playerId;
		this.race = race;
	}
}
