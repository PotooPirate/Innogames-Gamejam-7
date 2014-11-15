package de.potoopirate.alf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {

	private Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("alfBackground.wav"));
	
	public MusicPlayer() {
		
	}
	
	public void playBackgroundMusic () {
		this.backgroundMusic.play();
	}
	
	public void stopBackgroundMusic () {
		this.backgroundMusic.stop();
	}
}
