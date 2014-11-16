package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import de.potoopirate.alf.components.RaceComponent.Race;

public class SoundComponent extends Component {

	public static Sound SNAIL_SPAWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Snail_Spawn.wav"));
	public static Sound SNAIL_HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Snail_Hit.wav"));
	public static Sound SNAIL_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Snail_Death.wav"));

	public static Sound OCTO_SPAWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Octo_Spawn.wav"));
	public static Sound OCTO_HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Octo_Hit.wav"));
	public static Sound OCTO_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Octo_Death.wav"));

	public static Sound HIPPO_SPAWN_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Hippo_Spawn.wav"));
	public static Sound HIPPO_HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Hippo_Hit.wav"));
	public static Sound HIPPO_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("soundeffects/Hippo_Death.wav"));

	public Sound spawnSound;
	public Sound hitSound;
	public Sound deathSound;
	
	public SoundComponent (Race race) {
		switch (race) {
		case SNAIL:
			this.spawnSound = SNAIL_SPAWN_SOUND;
			this.hitSound = SNAIL_HIT_SOUND;
			this.deathSound = SNAIL_DEATH_SOUND;
			break;
		case OCTO:
			this.spawnSound = OCTO_SPAWN_SOUND;
			this.hitSound = OCTO_HIT_SOUND;
			this.deathSound = OCTO_DEATH_SOUND;
			break;
		case HIPPO:
			this.spawnSound = HIPPO_SPAWN_SOUND;
			this.hitSound = HIPPO_HIT_SOUND;
			this.deathSound = HIPPO_DEATH_SOUND;
			break;
		}
	}
}
