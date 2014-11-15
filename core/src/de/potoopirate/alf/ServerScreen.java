package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.systems.RendererSystem;
import de.potoopirate.alf.systems.ServerSystem;
import de.potoopirate.alf.systems.SpawnSystem;

public class ServerScreen extends ScreenAdapter{
	
	private SpawnSystem spawnSystem;

	public ServerScreen(Engine engine) {
		spawnSystem = new SpawnSystem();
		engine.addSystem(spawnSystem);
		engine.addSystem(new ServerSystem(spawnSystem));
		
		//RendererSystem.getInstance();
		//TestEntity t = new TestEntity();
		//engine.addEntity(t);
		//t.Init();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		RendererSystem.getInstance().Render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		RendererSystem.getInstance().resize(width, height);
	}
}