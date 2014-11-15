package de.potoopirate.alf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {

	private static Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/alfBackground.mp3"));
	
	public MusicPlayer() {
		
	}
	
	public static void playBackgroundMusic () {
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}
	
	public static void stopBackgroundMusic () {
		backgroundMusic.stop();
	}
}
