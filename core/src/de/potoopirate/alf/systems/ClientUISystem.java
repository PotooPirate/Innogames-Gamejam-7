package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.potoopirate.alf.interfaces.ClientListener;

public class ClientUISystem extends EntitySystem {
	
	private static final int BUTTON_WIDTH = Gdx.graphics.getWidth()/5;
	private static final int BUTTON_HEIGHT = Gdx.graphics.getHeight()/4;
	private static final int SECTION = Gdx.graphics.getWidth()/3;
	
	private static final int BLOCK_COUNTER_RELEASE = 10;
	private static final Texture TORTSEN_ICON = new Texture(Gdx.files.internal("icons/tortsenicon.png"));
	private Stage stage;
	private ClientListener clientSystem;
	private int activePath;
	private int x, y;
	private boolean touched;		
	private boolean started;		//Holding the client in a block state
	private float blockCounter;
	private SpriteBatch batch;
	
	private ShapeRenderer debugRenderer;
	
	public ClientUISystem(ClientListener clientSystem) {
		this.clientSystem = clientSystem;
		debugRenderer = new ShapeRenderer();
		activePath = 2;
		batch  = new SpriteBatch();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		
		blockCounter = 0;
		started = false;
		stage = new Stage();
	    Gdx.input.setInputProcessor(stage);
	    
	}

	
	private void throwSlot() {
		if(Gdx.input.isTouched() && !touched) {
	    	y = 0;
	    	touched = true;
	    } else if (Gdx.input.isTouched() && touched) {
	    	y += Gdx.input.getDeltaY();
	    } else if (!Gdx.input.isTouched() && touched) {
	    	touched = false;
	    	if(y <= -200) {
	    		x = Gdx.input.getX();
	    		if (x < Gdx.graphics.getWidth()/3) {
	    			clientSystem.throwSlot(1, activePath);
	    		} else if (x < (Gdx.graphics.getWidth()/3)*2) { 
	    			clientSystem.throwSlot(2, activePath);
	    		} else {
	    			clientSystem.throwSlot(3, activePath);
	    		}
	    	}
	    	System.out.println("delta X: " + x + "/ delta Y:" + y);
	    }
	}
	
	private void changePath() {
		if(Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if (x > BUTTON_WIDTH && x < BUTTON_WIDTH*2 && y < BUTTON_HEIGHT*3) {
				activePath = 1;
			} else if (x > BUTTON_WIDTH*2 && x < BUTTON_WIDTH*3 && y < BUTTON_HEIGHT*3 ) {
				activePath = 2;
			} else if (x > BUTTON_WIDTH*3 && x < BUTTON_WIDTH*4 && y < BUTTON_HEIGHT*3) {
				activePath = 3;
			}
		}
	}
	
	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.update(deltaTime);
		blockCounter += deltaTime;
		stage.act(deltaTime);
	    stage.draw();
	    
	    
	    debugRenderer.begin(ShapeType.Line);
	    debugRenderer.setColor(1f, 0, 0, 1);
	    debugRenderer.line(Gdx.graphics.getWidth()/3, 0, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight());
	    debugRenderer.line((Gdx.graphics.getWidth()/3)*2, 0, (Gdx.graphics.getWidth()/3)*2, Gdx.graphics.getHeight());
	    debugRenderer.end();

	    debugRenderer.begin(ShapeType.Filled);
	    debugRenderer.setColor(activePath == 1 ? Color.GREEN : Color.GRAY);
	    debugRenderer.rect(BUTTON_WIDTH, BUTTON_HEIGHT*3, BUTTON_WIDTH, BUTTON_HEIGHT);
	    debugRenderer.setColor(activePath == 2 ? Color.GREEN : Color.GRAY);
	    debugRenderer.rect(BUTTON_WIDTH*2, BUTTON_HEIGHT*3, BUTTON_WIDTH, BUTTON_HEIGHT);
	    debugRenderer.setColor(activePath == 3 ? Color.GREEN : Color.GRAY);
	    debugRenderer.rect(BUTTON_WIDTH*3, BUTTON_HEIGHT*3, BUTTON_WIDTH, BUTTON_HEIGHT);
	    debugRenderer.end();
	    
	    int trotsenIconXLocation = (SECTION - SECTION/2) - TORTSEN_ICON.getWidth()/2 ;
	    int trotsenIconYLocation = BUTTON_HEIGHT;
	    batch.begin();
	    batch.draw(TORTSEN_ICON,trotsenIconXLocation, trotsenIconYLocation );
	    batch.end();
	    
	    
	    if(blockCounter >= BLOCK_COUNTER_RELEASE && started) {
		    throwSlot();
		    changePath();
	    } else if (!started) {
	    	started = clientSystem.isStarted();
	    }
	}
}
