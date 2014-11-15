package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ServerSystem;

public class StartScreen extends ScreenAdapter {

	private static final TextureRegion SERVER = new TextureRegion(new Texture(Gdx.files.internal("icons/server.png")));
	private static final TextureRegion CLIENT = new TextureRegion(new Texture(Gdx.files.internal("icons/client.png")));
	
	private Engine engine;
	private SpriteBatch batch;
	private Game game;
	
	public StartScreen(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		batch.begin();
		batch.draw(SERVER, 0f, 0f, 0f, 0f, 100f, 100f, 1.0f, 1.0f, 0f);
		batch.draw(CLIENT, Gdx.graphics.getWidth()-100f, 0f, 0f, 0f, 100f, 100f, 1.0f, 1.0f, 0f);
		batch.end();
		
		if (Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth()/2 && engine.getSystem(ServerSystem.class) == null) {
			System.out.println("Added a Server:");
			game.setScreen(new ServerScreen(engine));
		} else if (Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth()/2 && engine.getSystem(ClientSystem.class) == null) {
			System.out.println("Added a Client:");
			game.setScreen(new ClientScreen(engine));
		}
	}
}
