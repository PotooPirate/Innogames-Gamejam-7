package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.AnimationStateListener;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;

import de.potoopirate.alf.components.RaceComponent.Race;
import de.potoopirate.alf.systems.PlayerManagerSystem;
import de.potoopirate.alf.systems.RendererSystem;

public class AnimationRendererComponent extends Component implements IRenderer, AnimationStateListener {
	
	private SkeletonData skeletonData;
	private Skeleton skeleton;
	public AnimationState state;	
	private AnimationStateData stateData;
	private TransformComponent transform;
	private PlayerComponent pc;
	private float depth;
	
	public AnimationRendererComponent() {
		
	}
	
	public Skeleton getSkeleton() 
	{
		return this.skeleton;
	}
	
	public void Init(SkeletonData data, TransformComponent transform, PlayerComponent pc) {
		this.pc = pc;
		this.skeletonData = data;
		this.skeleton = new Skeleton(skeletonData); 
		this.transform = transform;
		this.skeleton.setPosition(this.transform.getPosition().x, this.transform.getPosition().y);
		this.stateData = new AnimationStateData(skeletonData); 

		this.state = new AnimationState(this.stateData); 
		this.depth = 1;
		this.state.setTimeScale(0.5f); 
		
		CreateAnimationMix("walking", "dying" , 0.5f);
		CreateAnimationMix("walking", "attacking", 0.5f);
		CreateAnimationMix("attacking", "walking", 0.5f);
		
		this.skeleton.getRootBone().setScaleX(this.transform.getSize().x);
		this.skeleton.getRootBone().setScaleY(this.transform.getSize().y);
		
		RendererSystem.getInstance().RegisterRenderer(this);
	}
	
	public TrackEntry SetAnimationState(String ani, boolean loop, float delay , int id) {
		this.skeleton.updateWorldTransform();
		state.addAnimation(0, ani, loop, 0);
		return state.setAnimation(id, ani, loop);
	}
	
	public void CreateAnimationMix(String firstAni, String SecondAni, float speed) {
		this.stateData.setMix(firstAni, SecondAni, speed);
	}
	
	public void Destroy() {
		RendererSystem.getInstance().DeregisterRenderer(this);
	}
	
	@Override
	public void Render(float deltaTime, SpriteBatch batch) {

		this.skeleton.setPosition(this.transform.getPosition().x, this.transform.getPosition().y);
		this.state.update(deltaTime);
		this.state.apply(skeleton); 
		this.skeleton.updateWorldTransform();	
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	@Override
	public void event(int trackIndex, Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void complete(int trackIndex, int loopCount) {
		trackIndex = 0;
		
	}

	@Override
	public void start(int trackIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end(int trackIndex) {
		if(this.state.getCurrent(trackIndex).getAnimation().toString().equals("dying")) {
			this.Destroy();
		}
		else {
			this.state.setAnimation(0, "walking", true);
		}
	}

	@Override
	public void OnDyAnimationEnd(AnimationState animationState) {
		// TODO Auto-generated method stub
		
	}

}
