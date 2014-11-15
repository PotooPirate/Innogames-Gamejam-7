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
	private static final TextureAtlas ATLAS_EMMA =  new TextureAtlas(Gdx.files.internal("Emma/Emma.atlas"));
	private static final TextureAtlas ATLAS_GUNTER =  new TextureAtlas(Gdx.files.internal("Gunter/Gunter.atlas"));
	
	private float getWidth(float value) {
		return (value/6.4f) * (((float)Gdx.graphics.getWidth())/100.0f);
	}
	
	private float getHeight(float value) {
		return (value/4.8f) * (((float)Gdx.graphics.getHeight())/100.0f);
	}
	
	
	public AnimalEntity(int playerId, int path, Race race,String sound,TextureAtlas atlas ,String pathJson) {
		add(new RaceComponent(race));
		add(new PathComponent());
		add(new PlayerComponent(playerId));
		add(new TransformComponent());
		add(new CollisionComponent());
	//	add(new SoundComponent(sound));
		add(new AnimationRendererComponent());
		this.getComponent(TransformComponent.class).Init(playerId == 0 ? new Vector2(getWidth(30f),getHeight(30f)): new Vector2(getWidth(610f),getHeight(440f)));
		
		this.getComponent(PathComponent.class).pathNumber = path;
		this.getComponent(PathComponent.class).currentTargetIndex = 0;

		SkeletonJson json = new SkeletonJson(atlas); 	
		json.setScale(this.getComponent(TransformComponent.class).getSize().x);
		SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(pathJson));

		this.getComponent(AnimationRendererComponent.class).Init(skeletonData, this.getComponent(TransformComponent.class));
		this.getComponent(AnimationRendererComponent.class).state.setAnimation(0, "walking", true);
		if(playerId == 0 && !race.equals(Race.HIPPO)) 
		{
			this.getComponent(AnimationRendererComponent.class).getSkeleton().setFlipX(true);
		}
		
		if(race.equals(Race.HIPPO) && playerId == 1) {
			this.getComponent(AnimationRendererComponent.class).getSkeleton().setFlipX(true);
		}
		
		PathSystem.getInstance().AddAnimal(this);
	}
	
	public static AnimalEntity createSnail(int playerId, int path) {
		return new AnimalEntity(playerId,path, Race.SNAIL, SoundComponent.SNAIL_SOUND,ATLAS_GUNTER, "Gunter/Gunter.json");
	}

	public static AnimalEntity createHippo(int playerId, int path) {
		return new AnimalEntity(playerId,path, Race.HIPPO, SoundComponent.HIPPO_SOUND,ATLAS_TORTSEN, "Tortsen/Tortsen.json");
	}

	public static AnimalEntity createOcto(int playerId, int path) {
		return new AnimalEntity(playerId,path, Race.OCTO, SoundComponent.OCTO_SOUND,ATLAS_EMMA, "Emma/Emma.json");
	}
}
