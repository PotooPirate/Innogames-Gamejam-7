package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.ScreenAdapter;

import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.entities.LevelEntity;
import de.potoopirate.alf.systems.FightSystem;
import de.potoopirate.alf.systems.PathSystem;
import de.potoopirate.alf.systems.RendererSystem;
import de.potoopirate.alf.systems.ServerSystem;
import de.potoopirate.alf.systems.SpawnSystem;

public class ServerScreen extends ScreenAdapter{
	
	private SpawnSystem spawnSystem;
	private Engine engine;
	public static double gameTimer = 0;

	public ServerScreen(Engine engine) {
		this.engine = engine;
		spawnSystem = new SpawnSystem();
		engine.addSystem(spawnSystem);
		engine.addSystem(new ServerSystem(spawnSystem));
		
		engine.addSystem(RendererSystem.getInstance());
		engine.addSystem(new FightSystem());
		
		LevelEntity level = new LevelEntity();
		engine.addEntity(level);
		
		gameTimer = 0;
		
		MusicPlayer.playBackgroundMusic();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		gameTimer += 0.1f / 60f;
		RendererSystem.getInstance().Render(Gdx.graphics.getDeltaTime());
		PathSystem.getInstance().Update(Gdx.graphics.getDeltaTime());
		
		if(Gdx.input.isKeyPressed(Keys.S)) {
			AnimalEntity.createHippo(1, 1);
		}
		else if(Gdx.input.isKeyPressed(Keys.C)) {
			AnimalEntity.createSnail(0,3);
		}
	}

	@Override
	public void resize(int width, int height) {
		RendererSystem.getInstance().resize(width, height);
	}
}