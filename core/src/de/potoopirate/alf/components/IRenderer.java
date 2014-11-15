package de.potoopirate.alf.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IRenderer {
	public void Render(float deltaTime, SpriteBatch batch);

	public float getDepth();
	public void setDepth(float depth);
}