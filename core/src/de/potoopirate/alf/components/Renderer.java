package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;

import de.potoopirate.alf.systems.RendererSystem;

public class Renderer extends Component {
	
	private SkeletonData skeletonData;
	private Skeleton skeleton;
	private AnimationState state;	
	private AnimationStateData stateData;
	private Transform transform;
	private float depth;
	
	public Renderer() {
		
	}
	
	public Skeleton getSkeleton() 
	{
		return this.skeleton;
	}
	
	public void Init(SkeletonData data, Transform transform) {
		this.skeletonData = data;
		this.skeleton = new Skeleton(skeletonData); 
		this.transform = transform;
		this.skeleton.setPosition(this.transform.getPosition().x, this.transform.getPosition().y);
		this.stateData = new AnimationStateData(skeletonData); 

		this.state = new AnimationState(this.stateData); 
		this.state.setTimeScale(0.5f); 
		
		RendererSystem.getInstance().RegisterRenderer(this);
	}
	
	public void SetAnimationState(String ani, boolean loop, float delay ) {
		this.state.addAnimation(0,ani,loop,delay);
		this.state.setAnimation(0, ani, loop);
	}
	
	public void CreateAnimationMix(String firstAni, String SecondAni, float speed) {
		this.stateData.setMix(firstAni, SecondAni, speed);
	}
	
	public void Destroy() {
		RendererSystem.getInstance().DeregisterRenderer(this);
	}
	
	public void Render(float deltaTime) {
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

}
