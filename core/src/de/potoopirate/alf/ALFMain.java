package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input.Keys;

import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.entities.LevelEntity;
import de.potoopirate.alf.systems.FightSystem;
import de.potoopirate.alf.systems.PathSystem;
import de.potoopirate.alf.systems.RendererSystem;

public class ALFMain extends Game {
	
	private Engine engine;	
	
	@Override
	public void create () {	
		engine = new Engine();	
		engine.addSystem(RendererSystem.getInstance());
		//engine.addSystem(new FightSystem());
		
				LevelEntity level = new LevelEntity();
		engine.addEntity(level);
		engine.addEntity(AnimalEntity.createHippo(1,2));
		engine.addEntity(AnimalEntity.createHippo(0,2));
	}

	public void resize (int width, int height) {
		RendererSystem.getInstance().resize(width, height);
	}
	
	@Override
	public void render () {
		
		super.render();
		
		RendererSystem.getInstance().Render(Gdx.graphics.getDeltaTime());
		PathSystem.getInstance().Update(Gdx.graphics.getDeltaTime());
		engine.update(Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyPressed(Keys.S)) {
			AnimalEntity.createHippo(1,2);
		}
		else if(Gdx.input.isKeyPressed(Keys.C)) {
			AnimalEntity.createHippo(0,2);
		}
	}
}
