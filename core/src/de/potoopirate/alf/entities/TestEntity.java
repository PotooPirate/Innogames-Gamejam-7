package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

import de.potoopirate.alf.components.Renderer;
import de.potoopirate.alf.components.TransformComponent;

public class TestEntity extends Entity {

	private TransformComponent transform;
	private Renderer renderer;
	
	public TestEntity() 
	{
		
	}
	
	public void Init() 
	{
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("spineboy/spineboy.atlas"));
		SkeletonJson json = new SkeletonJson(atlas); 
		json.setScale(0.6f); 
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spineboy/spineboy.json"));
		this.transform = new TransformComponent();
		this.transform.Init(new Vector2(120,60));
		this.add(this.transform);
		
		this.renderer = new Renderer();
		this.renderer.Init(skeletonData, this.transform);
		this.renderer.SetAnimationState("run", true, 0);
		this.add(this.renderer);	
	}
}
