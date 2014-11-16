package de.potoopirate.alf;


import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.components.SoundComponent;
import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ClientUISystem;

public class ClientScreen extends ScreenAdapter{
	
	private ClientSystem clientSystem;
	
	public ClientScreen(Engine engine, String ip) {
		clientSystem = new ClientSystem(ip);
		engine.addSystem(clientSystem);
		engine.addSystem(new ClientUISystem(clientSystem));

	}
}
