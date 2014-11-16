package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import de.potoopirate.alf.components.RaceComponent.Race;

public class SoundComponent extends Component {

	public static final String SNAIL_SPAWN_SOUND = "Snail_Spawn.wav";
	public static final String SNAIL_HIT_SOUND = "Snail_Hit.wav";
	public static final String SNAIL_DEATH_SOUND = "Snail_Death.wav";

	public static final String OCTO_SPAWN_SOUND = "Octo_Spawn.wav";
	public static final String OCTO_HIT_SOUND = "Octo_Hit.wav";
	public static final String OCTO_DEATH_SOUND = "Octo_Death.wav";

	public static final String HIPPO_SPAWN_SOUND = "Hippo_Spawn.wav";
	public static final String HIPPO_HIT_SOUND = "Hippo_Hit.wav";
	public static final String HIPPO_DEATH_SOUND = "Hippo_Death.wav";
	
	public Sound spawnSound;
	public Sound hitSound;
	public Sound deathSound;
	
	public SoundComponent (Race race) {
		switch (race) {
		case SNAIL:
			this.spawnSound = Gdx.audio.newSound(Gdx.files.internal(SNAIL_SPAWN_SOUND));
			this.hitSound = Gdx.audio.newSound(Gdx.files.internal(SNAIL_HIT_SOUND));
			this.deathSound = Gdx.audio.newSound(Gdx.files.internal(SNAIL_DEATH_SOUND));
			break;
		case OCTO:
			this.spawnSound = Gdx.audio.newSound(Gdx.files.internal(OCTO_SPAWN_SOUND));
			this.hitSound = Gdx.audio.newSound(Gdx.files.internal(OCTO_HIT_SOUND));
			this.deathSound = Gdx.audio.newSound(Gdx.files.internal(OCTO_DEATH_SOUND));
			break;
		case HIPPO:
			this.spawnSound = Gdx.audio.newSound(Gdx.files.internal(HIPPO_SPAWN_SOUND));
			this.hitSound = Gdx.audio.newSound(Gdx.files.internal(HIPPO_HIT_SOUND));
			this.deathSound = Gdx.audio.newSound(Gdx.files.internal(HIPPO_DEATH_SOUND));
			break;
		}
	}
}
