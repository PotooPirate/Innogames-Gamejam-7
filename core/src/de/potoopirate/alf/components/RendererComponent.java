package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.potoopirate.alf.systems.RendererSystem;

public class RendererComponent extends Component implements IRenderer{
	private TransformComponent transform;
	private float depth;
	private Sprite sprite;
	private Texture texture;

	public void Init(Texture data, TransformComponent transform) {
		this.transform = transform;
		this.texture = data;
		this.sprite = new Sprite(this.texture);
		this.sprite.setSize(this.transform.getSize().x, this.transform.getSize().y);
		this.sprite.setPosition(this.transform.getPosition().x, this.transform.getPosition().y);
		this.depth = 0;
		RendererSystem.getInstance().RegisterRenderer(this);
	}
	
	@Override
	public void Render(float deltaTime, SpriteBatch batch) {
		batch.draw(this.texture, this.transform.getPosition().x, this.transform.getPosition().y ,this.transform.getSize().x, this.transform.getSize().y);
	}

	@Override
	public float getDepth() {
		return this.depth;
	}

	@Override
	public void setDepth(float depth) {
		this.depth = depth;
	}
}
