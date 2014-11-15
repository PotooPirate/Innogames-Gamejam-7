package de.potoopirate.alf;


import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ClientUISystem;

public class ClientScreen extends ScreenAdapter{
	
	private ClientSystem clientSystem;
	
	public ClientScreen(Engine engine) {
		clientSystem = new ClientSystem();
		engine.addSystem(clientSystem);
		engine.addSystem(new ClientUISystem(clientSystem));
	}
}
