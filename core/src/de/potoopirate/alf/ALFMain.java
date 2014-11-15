package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import de.potoopirate.alf.entities.TestEntity;
import de.potoopirate.alf.systems.RendererSystem;

public class ALFMain extends Game {
	
	private Engine engine;	
	
	@Override
	public void create () {	
		engine = new Engine();	
		RendererSystem.getInstance();
		TestEntity t = new TestEntity();
		engine.addEntity(t);
		t.Init();
	}

	public void resize (int width, int height) {
		RendererSystem.getInstance().resize(width, height);
	}
	
	@Override
	public void render () {
		
		super.render();
		
	//	if(Gdx.input.isKeyPressed(Keys.S) && engine.getSystem(Server.class) == null) {
		RendererSystem.getInstance().Render(Gdx.graphics.getDeltaTime());
		
		engine.update(Gdx.graphics.getDeltaTime());
		
	//	if(Gdx.input.isKeyPressed(Keys.S) && engine.getSystem(Client.class) == null) {
		//	System.out.println("Added a Server:");
		//	setScreen(new Server(engine));
		//}else if(Gdx.input.isKeyPressed(Keys.C) && engine.getSystem(ClientSystem.class) == null) {
	//		System.out.println("Added a Client:");

			//setScreen(new Client(engine));
	//	}

		//}

	}
}
