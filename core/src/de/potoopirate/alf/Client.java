package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ClientUISystem;

public class Client extends ScreenAdapter{
	
	private Engine engine;
	
	private ClientSystem clientSystem;
	
	public Client(Engine engine) {
		this.engine = engine;
		clientSystem = new ClientSystem();
		engine.addSystem(clientSystem);
		engine.addSystem(new ClientUISystem(clientSystem));
	}
}
