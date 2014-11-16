package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import de.potoopirate.alf.components.SoundComponent;
import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.systems.SpawnSystem;

public class ALFMain extends Game {

	private Engine engine;

	@Override
	public void create () {	
		AnimalEntity.ATLAS_TORTSEN = new TextureAtlas(Gdx.files.internal("Tortsen/Tortsen.atlas"));
		AnimalEntity.ATLAS_EMMA = new TextureAtlas(Gdx.files.internal("Emma/Emma.atlas"));
		AnimalEntity.ATLAS_GUNTER = new TextureAtlas(Gdx.files.internal("Gunter/Gunter.atlas"));
		
		SoundComponent.SNAIL_SPAWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Snail_Spawn.wav"));
		SoundComponent.SNAIL_HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Snail_Hit.wav"));
		SoundComponent.SNAIL_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Snail_Death.wav"));

		SoundComponent.OCTO_SPAWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Octo_Spawn.wav"));
		SoundComponent.OCTO_HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Octo_Hit.wav"));
		SoundComponent.OCTO_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Octo_Death.wav"));

		SoundComponent.HIPPO_SPAWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Hippo_Spawn.wav"));
		SoundComponent.HIPPO_HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Hippo_Hit.wav"));
		SoundComponent.HIPPO_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Hippo_Death.wav"));

		
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
