package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent extends Component {
	
	private Vector2 position;
	private Vector2 size;
	private Vector2 rotation;
	
	public TransformComponent() {
		
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public Vector2 getRotation() {
		return rotation;
	}

	public void setRotation(Vector2 rotation) {
		this.rotation = rotation;
	}
	
	public void Init(Vector2 position) {
		this.position = position;
		this.size = new Vector2(1,1);
		this.rotation = new Vector2(0,0);
	}
}
