package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class ClientUISystem extends EntitySystem {

	private Stage stage;
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		
		stage = new Stage();
	    Gdx.input.setInputProcessor(stage);
	    addUI();
	}
	
	private void addUI() {
	/*	Button button = new Button();
		stage.addActor(button);
		button.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println(":D yaaaay");
			}
		});*/
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		stage.act(deltaTime);
	    stage.draw();
	}

}
