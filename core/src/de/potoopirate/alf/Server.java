package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.systems.ServerSystem;

public class Server extends ScreenAdapter{
	
	private Engine engine;
	
	public Server(Engine engine) {
		this.engine = engine;
		engine.addSystem(new ServerSystem());
	}
}
