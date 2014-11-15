package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.potoopirate.alf.interfaces.IClientSystem;

public class ClientUISystem extends EntitySystem {
	
	private static final int BUTTON_WIDTH = Gdx.graphics.getWidth()/5;
	private static final int BUTTON_HEIGHT = Gdx.graphics.getHeight()/4;
	
	private Stage stage;
	private IClientSystem clientSystem;
	private int activePath;
	private int x, y;
	private boolean touched;
	
	private ShapeRenderer debugRenderer;
	
	public ClientUISystem(IClientSystem clientSystem) {
		this.clientSystem = clientSystem;
		debugRenderer = new ShapeRenderer();
		activePath = 2;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		
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
	    
	    throwSlot();
	    changePath();
	}
}
