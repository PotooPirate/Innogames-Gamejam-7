package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ClientUISystem;

public class Client extends ScreenAdapter{
	
	private Engine engine;
	
	public Client(Engine engine) {
		this.engine = engine;
		engine.addSystem(new ClientSystem());
		engine.addSystem(new ClientUISystem());
	}
}
