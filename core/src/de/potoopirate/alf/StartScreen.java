package de.potoopirate.alf;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.potoopirate.alf.systems.ClientSystem;
import de.potoopirate.alf.systems.ServerSystem;

public class StartScreen extends ScreenAdapter {

	private static final TextureRegion SERVER = new TextureRegion(new Texture(Gdx.files.internal("icons/server.png")));
	private static final TextureRegion CLIENT = new TextureRegion(new Texture(Gdx.files.internal("icons/client.png")));
	private static final BitmapFont FONT = new BitmapFont(Gdx.files.internal("CandyFont.fnt"));
	
	private TextBounds bounds;
	private String ip;
	
	private Engine engine;
	private SpriteBatch batch;
	private Game game;
	private boolean hasClient;
	
	public StartScreen(Engine engine, Game game) {
		this.engine = engine;
		this.game = game;
		this.hasClient = false;
		batch = new SpriteBatch();
		FONT.setScale(0.33f);
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
		} else if (Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth()/2 && !hasClient) {
			System.out.println("Added a Client:");
			hasClient = true;
			Gdx.input.getTextInput(new TextInputListener() {
				
				@Override
				public void input(String text) {
					ip = text;
					game.setScreen(new ClientScreen(engine, ip));
				}
				
				@Override
				public void canceled() {
					Gdx.app.exit();
				}
			}, "Please enter your adress of happiness", "127.0.0.1");
		}
	}
}
