package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.potoopirate.alf.ServerScreen;
import de.potoopirate.alf.components.AnimationRendererComponent;
import de.potoopirate.alf.components.RaceComponent;
import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.entities.WinEntity;

public class PlayerManagerSystem extends EntitySystem {
	private static final Texture player1Win = new Texture(Gdx.files.internal("Winning1.png"));
	private static final Texture player2Win = new Texture(Gdx.files.internal("Winning2.png"));

	private Engine engine;
	
	private SpriteBatch batch;
	private boolean showDialog;
	
	public static int playerOneLife = 20;
	public static int playerTwoLife = 20;
	private int whoWon;
	
	@Override
	public void addedToEngine(Engine engine)
	{
		super.addedToEngine(engine);
		this.engine = engine;
		batch = new SpriteBatch();
		showDialog = false;
		engine.addSystem(this);
	}
	
	@Override
	public void update(float deltaTime) {
		if(playerOneLife == 0 && !showDialog) {
			showDialog = true;
			whoWon = 1;
		}
		if(playerTwoLife == 0 && !showDialog) {
			showDialog = true;
			whoWon = 2;
		}
		
		if(showDialog) {
			batch.begin();
			batch.draw((whoWon == 1 ? player1Win : player2Win), Gdx.graphics.getWidth()/2-player1Win.getWidth()/2, 
					Gdx.graphics.getHeight()/2-player1Win.getHeight()/2, player1Win.getWidth(), player1Win.getHeight());
			batch.end();
		}
		
		if(Gdx.input.isTouched() && showDialog) {
			//engine.getSystem(RendererSystem.class).clearRendererList();
			//engine.removeAllEntities();
			showDialog = false;
			clearField();
			playerOneLife = 10;
			playerTwoLife = 10;
		}
	}
	
	private void clearField() {
		ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(RaceComponent.class));
		AnimalEntity e;
		for(int i = 0; i < entities.size(); i++) {
			e = (AnimalEntity)entities.get(i);
			e.getComponent(AnimationRendererComponent.class).SetAnimationState("dying", false, 0, 0).setListener(e.getComponent(AnimationRendererComponent.class));
			PathSystem.getInstance().RemoveAnimal(e);
			engine.removeEntity(e);
		}
	}
}
