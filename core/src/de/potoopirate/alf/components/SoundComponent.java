package de.potoopirate.alf.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent {

	public static final String SNAIL_SOUND = "snail.wav";
	public static final String OCTOPUS_SOUND = "octopus.wav";
	public Sound animalSound;
	
	public SoundComponent (String fileName) {
		this.animalSound = Gdx.audio.newSound(Gdx.files.internal(fileName));
	}
}
