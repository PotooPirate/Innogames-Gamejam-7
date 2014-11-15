package de.potoopirate.alf.systems;

import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import de.potoopirate.alf.components.Renderer;

public class RendererSystem extends EntitySystem {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private SkeletonRenderer skeltonRenderer;
	private SkeletonRendererDebug debugRenderer;
	
	private SortedMap<Float, Renderer> rendererList;
	private static RendererSystem instance;
	
	private RendererSystem() {
		rendererList = new TreeMap<Float,Renderer>();
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		skeltonRenderer = new SkeletonRenderer();
		skeltonRenderer.setPremultipliedAlpha(true); 
		debugRenderer = new SkeletonRendererDebug();
		debugRenderer.setBoundingBoxes(false);
		debugRenderer.setRegionAttachments(false);
	}
	
	public static RendererSystem getInstance() {
		if (instance == null) {
			instance = new RendererSystem();
		}
		return instance;
	}
	
	public void resize (int width, int height) {
		camera.setToOrtho(false); 
	}

	
	public void RegisterRenderer(Renderer renderer) {
		rendererList.put(renderer.getDepth(),renderer);
	}
	
	public void DeregisterRenderer(Renderer renderer) {
		rendererList.remove(renderer);
	}
	
	public void Render(float deltaTime) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.getProjectionMatrix().set(camera.combined);
		debugRenderer.getShapeRenderer().setProjectionMatrix(camera.combined);

		batch.begin();
		for (Map.Entry<Float,Renderer> entry : rendererList.entrySet()) {
			Renderer value = entry.getValue();
			        
			value.Render(deltaTime);
			skeltonRenderer.draw(batch, value.getSkeleton()); 
	    }
		batch.end();

	   //debugRenderer.draw(skeleton); // Draw debug lines.
	}
}
