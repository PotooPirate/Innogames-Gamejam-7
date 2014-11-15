package de.potoopirate.alf.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;

import de.potoopirate.alf.components.CollisionComponent;
import de.potoopirate.alf.components.PathComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.RaceComponent;
import de.potoopirate.alf.components.RaceComponent.Race;
import de.potoopirate.alf.components.SoundComponent;
import de.potoopirate.alf.components.TransformComponent;
import de.potoopirate.alf.components.AnimationRendererComponent;
import de.potoopirate.alf.systems.PathSystem;

public class AnimalEntity extends Entity {
	private static final TextureAtlas ATLAS_TORTSEN =  new TextureAtlas(Gdx.files.internal("Tortsen/Tortsen.atlas"));
	private static final TextureAtlas ATLAS_EMMA =  new TextureAtlas(Gdx.files.internal("spineboy/spineboy.atlas"));
	private static final TextureAtlas ATLAS_GUNTER =  new TextureAtlas(Gdx.files.internal("spineboy/spineboy.atlas"));
	
	public AnimalEntity(int playerId, int path, Race race,String sound,TextureAtlas atlas ,String pathJson) {
		add(new RaceComponent(race));
		add(new PathComponent());
		add(new PlayerComponent(playerId));
		add(new TransformComponent());
		add(new CollisionComponent());
	//	add(new SoundComponent(sound));
		add(new AnimationRendererComponent());
		this.getComponent(TransformComponent.class).Init(playerId == 0 ? new Vector2(30,30): new Vector2(610,440));
		
		this.getComponent(PathComponent.class).pathNumber = path;
		this.getComponent(PathComponent.class).currentTargetIndex = 0;

		SkeletonJson json = new SkeletonJson(atlas); 	
		json.setScale(this.getComponent(TransformComponent.class).getSize().x);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(pathJson));

		this.getComponent(AnimationRendererComponent.class).Init(skeletonData, this.getComponent(TransformComponent.class));
		this.getComponent(AnimationRendererComponent.class).SetAnimationState("flying", true, 0);
		if(playerId != 0) 
		{
			this.getComponent(AnimationRendererComponent.class).getSkeleton().setFlipX(true);
		}
		
		PathSystem.getInstance().AddAnimal(this);
	}
	
	public static AnimalEntity createSnail(int playerId, int path) {
		return new AnimalEntity(playerId,path, Race.SNAIL, SoundComponent.SNAIL_SOUND,ATLAS_GUNTER, "spineboy/spineboy.json");
	}

	public static AnimalEntity createHippo(int playerId, int path) {
		return new AnimalEntity(playerId,path, Race.HIPPO, SoundComponent.HIPPO_SOUND,ATLAS_TORTSEN, "Tortsen/Tortsen.json");
	}

	public static AnimalEntity createOcto(int playerId, int path) {
		return new AnimalEntity(playerId,path, Race.OCTO, SoundComponent.OCTO_SOUND,ATLAS_EMMA, "spineboy/spineboy.json");
	}
}
