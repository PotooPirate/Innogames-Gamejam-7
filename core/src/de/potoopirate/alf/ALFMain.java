package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.xml.internal.ws.client.ClientSchemaValidationTube;

import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ServerSystem;

public class ALFMain extends Game {
	
	private Engine engine;
	
	private Client client;
	private Server server;
	
	@Override
	public void create () {
		engine = new Engine();
		client = new Client(engine);
		server = new Server(engine);
	}

	@Override
	public void render () {
		super.render();
		engine.update(Gdx.graphics.getDeltaTime());
		
		if(Gdx.input.isKeyPressed(Keys.S) && engine.getSystem(ClientSystem.class) == null) {
			System.out.println("Added a Server:");
			setScreen(client);
		}else if(Gdx.input.isKeyPressed(Keys.C) && engine.getSystem(ServerSystem.class) == null) {
			System.out.println("Added a Client:");
			setScreen(client);
		}
	}
}
