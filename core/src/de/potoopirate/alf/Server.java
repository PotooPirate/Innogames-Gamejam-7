package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.systems.ServerSystem;
import de.potoopirate.alf.systems.SpawnSystem;

public class Server extends ScreenAdapter{
	
	private Engine engine;
	private SpawnSystem spawnSystem;
	
	public Server(Engine engine) {
		this.engine = engine;
		spawnSystem = new SpawnSystem();
		engine.addSystem(spawnSystem);
		engine.addSystem(new ServerSystem(spawnSystem));
	}
}