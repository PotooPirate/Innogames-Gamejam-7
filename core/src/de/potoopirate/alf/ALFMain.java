package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

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
