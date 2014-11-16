package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;

import de.potoopirate.alf.ServerScreen;
import de.potoopirate.alf.entities.WinEntity;

public class PlayerManagerSystem extends EntitySystem {
	public static int playerOneLife = 0;
	public static int playerTwoLife = 10;

	private Engine engine;
	private RendererSystem renderer;
	private WinEntity playerOneWinner;
	private WinEntity playerTwoWinner;
	
	private boolean showDialog;
	
	@Override
	public void addedToEngine(Engine engine)
	{
		super.addedToEngine(engine);
		this.engine = engine;
		renderer = RendererSystem.getInstance();
		playerOneWinner = new WinEntity();
		playerTwoWinner = new WinEntity();
		showDialog = false;
		engine.addSystem(this);
	}
	
	@Override
	public void update(float deltaTime) {
		if(playerOneLife == 0) {
			showDialog = true;
			restartGame(0);
			playerOneWinner.show();
		}
		if(playerTwoLife == 0) {
			showDialog = true;
			restartGame(1);
			playerTwoWinner.show();
		}
		
		if(Gdx.input.isTouched() && showDialog) {
			renderer.clearRendererList();
			engine.removeAllEntities();
			showDialog = false;
		}
	}
	
	private void restartGame(int winner) {
		renderer.clearRendererList();
		engine.removeAllEntities();
		playerOneLife = 10;
		playerTwoLife = 10;
	}
}
