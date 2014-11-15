package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.entities.LevelEntity;
import de.potoopirate.alf.systems.PathSystem;
import com.badlogic.gdx.graphics.GL20;

import de.potoopirate.alf.systems.RendererSystem;
import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ServerSystem;

public class ALFMain extends Game {

	private Engine engine;

	@Override
	public void create () {	
		engine = new Engine();	
		setScreen(new StartScreen(engine, this));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		
		engine.update(Gdx.graphics.getDeltaTime());
	}

}
