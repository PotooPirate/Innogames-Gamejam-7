package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.systems.SpawnSystem;

public class ALFMain extends Game {

	private Engine engine;

	@Override
	public void create () {	
		AnimalEntity.ATLAS_TORTSEN = new TextureAtlas(Gdx.files.internal("Tortsen/Tortsen.atlas"));
		AnimalEntity.ATLAS_EMMA = new TextureAtlas(Gdx.files.internal("Emma/Emma.atlas"));
		AnimalEntity.ATLAS_GUNTER = new TextureAtlas(Gdx.files.internal("Gunter/Gunter.atlas"));
		
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
