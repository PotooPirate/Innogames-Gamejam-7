package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import de.potoopirate.alf.components.AnimationRendererComponent;
import de.potoopirate.alf.components.RendererComponent;
import de.potoopirate.alf.components.TransformComponent;

public class LevelEntity extends Entity {
	
	public LevelEntity () {
		add(new TransformComponent());
		add(new RendererComponent());
		this.getComponent(TransformComponent.class).Init(new Vector2(0,0));
		
		this.getComponent(TransformComponent.class).setSize(new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
		Texture temp = new Texture("Outlines.png");
		this.getComponent(RendererComponent.class).Init(temp, this.getComponent(TransformComponent.class));
		
	}
}
