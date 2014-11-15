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

import de.potoopirate.alf.systems.RendererSystem;

public class AnimationRendererComponent extends Component implements IRenderer, AnimationStateListener {
	
	private SkeletonData skeletonData;
	private Skeleton skeleton;
	private AnimationState state;	
	private AnimationStateData stateData;
	private TransformComponent transform;
	private float depth;
	
	public AnimationRendererComponent() {
		
	}
	
	public Skeleton getSkeleton() 
	{
		return this.skeleton;
	}
	
	public void Init(SkeletonData data, TransformComponent transform) {
		this.skeletonData = data;
		this.skeleton = new Skeleton(skeletonData); 
		this.transform = transform;
		this.skeleton.setPosition(this.transform.getPosition().x, this.transform.getPosition().y);
		this.stateData = new AnimationStateData(skeletonData); 

		this.state = new AnimationState(this.stateData); 
		this.depth = 1;
		this.state.setTimeScale(0.5f); 
		
		
		RendererSystem.getInstance().RegisterRenderer(this);
	}
	
	public TrackEntry SetAnimationState(String ani, boolean loop, float delay , int id) {
		this.skeleton.updateWorldTransform();
		return this.state.addAnimation(id,ani,loop,delay);

	}
	
	public void CreateAnimationMix(String firstAni, String SecondAni, float speed) {
		this.stateData.setMix(firstAni, SecondAni, speed);
	}
	
	public void Destroy() {
		RendererSystem.getInstance().DeregisterRenderer(this);
	}
	
	@Override
	public void Render(float deltaTime, SpriteBatch batch) {
		skeleton.getRootBone().setScaleX(this.transform.getSize().x);
		skeleton.getRootBone().setScaleY(this.transform.getSize().y);
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
	
		
	}

	@Override
	public void start(int trackIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end(int trackIndex) {
		if(trackIndex == 1) {
			this.SetAnimationState("walking", true, 0, 2);
		}
		else {
			this.Destroy();
		}		
	}

	@Override
	public void OnDyAnimationEnd(AnimationState animationState) {
		// TODO Auto-generated method stub
		
	}

}
