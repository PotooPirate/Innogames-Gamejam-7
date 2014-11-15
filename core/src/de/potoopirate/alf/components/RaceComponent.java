package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;

public class RaceComponent extends Component {
	public enum Race {
		SNAIL, 
		OCTO, 
		HIPPO
	}
	
	private Race race;
	
	public RaceComponent (Race race) {
		this.race = race;
	}
	
}
