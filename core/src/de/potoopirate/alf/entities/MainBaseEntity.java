package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import de.potoopirate.alf.components.LifeComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.RaceComponent.Race;
import de.potoopirate.alf.components.TransformComponent;

public class MainBaseEntity extends Entity {
	
	private float getWidth(float value) {
		return (value/6.4f) * (((float)Gdx.graphics.getWidth())/100.0f);
	}
	
	private float getHeight(float value) {
		return (value/4.8f) * (((float)Gdx.graphics.getHeight())/100.0f);
	}
	
	public MainBaseEntity (int playerId) {
		add(new PlayerComponent(playerId, Race.HIPPO));
		add(new LifeComponent());
		TransformComponent transform = new TransformComponent();
		transform.Init(playerId == 0 ? new Vector2(getWidth(35),getHeight(35)): new Vector2(getWidth(610f),getHeight(440f)));
		add(transform);
	}
}
