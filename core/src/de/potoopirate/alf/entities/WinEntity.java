package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import de.potoopirate.alf.components.AnimationRendererComponent;
import de.potoopirate.alf.components.RendererComponent;
import de.potoopirate.alf.components.TransformComponent;

public class WinEntity extends Entity {
	
	public WinEntity () {
		add(new TransformComponent());
		add(new RendererComponent());
		this.getComponent(TransformComponent.class).Init(new Vector2(0,0));
		
		float width = Gdx.graphics.getWidth()/1.5f;
		float height = Gdx.graphics.getHeight()/1.5f;
		float x = Gdx.graphics.getWidth()/2 - width/2;
		float y = Gdx.graphics.getHeight()/2 - width/2;
		this.getComponent(TransformComponent.class).setSize(new Vector2(width, height));
		this.getComponent(TransformComponent.class).setPosition(new Vector2(x, y));
	}
	
	public void show() {
		Texture temp = new Texture("icons/tortsenicon.png");
		this.getComponent(RendererComponent.class).Init(temp, this.getComponent(TransformComponent.class));
	}
}
