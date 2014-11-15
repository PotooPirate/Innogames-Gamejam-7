package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent extends Component {

	public static final String SNAIL_SOUND = "snail.wav";
	public static final String OCTO_SOUND = "octo.wav";
	public static final String HIPPO_SOUND = "hippo.wav";
	public Sound animalSound;
	
	public SoundComponent (String fileName) {
		this.animalSound = Gdx.audio.newSound(Gdx.files.internal(fileName));
	}
}
