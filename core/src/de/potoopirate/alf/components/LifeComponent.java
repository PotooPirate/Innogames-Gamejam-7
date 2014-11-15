package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;

public class LifeComponent extends Component {
	public int life = 0;
	
	public void looseLife()
	{
		++this.life;
	}
}
