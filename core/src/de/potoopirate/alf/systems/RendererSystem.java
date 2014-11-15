package de.potoopirate.alf.systems;

import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

import de.potoopirate.alf.components.AnimationRendererComponent;
import de.potoopirate.alf.components.IRenderer;

public class RendererSystem extends EntitySystem {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private SkeletonRenderer skeltonRenderer;
	private SkeletonRendererDebug debugRenderer;
	
	private SortedMap<Float, IRenderer> rendererList;
	private SortedMap<Float, IRenderer> rendererListTemp;
	
	static final BitmapFont PLAYERFONT = new BitmapFont(Gdx.files.internal("CandyFont.fnt"));

	private static RendererSystem instance;
	
	private RendererSystem() {
		rendererList = new TreeMap<Float, IRenderer>();
		rendererListTemp = new TreeMap<Float, IRenderer>();
		
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

	
	public void RegisterRenderer(IRenderer renderer) {
		Random rand = new Random();
		rendererList.put(renderer.getDepth() + rand.nextFloat(),renderer);
		rendererListTemp.put(renderer.getDepth() + rand.nextFloat(),renderer);
	}
	
	public void DeregisterRenderer(IRenderer renderer) {		
		for(Iterator<Map.Entry<Float, IRenderer>> it = rendererListTemp.entrySet().iterator();it.hasNext();)
		{
			Map.Entry<Float, IRenderer> entry = it.next();
		    if(entry.getValue().equals(renderer)) {   
		    	it.remove();
		    	return;
		    }
		}
	}
	
	private void UpdateList() {
		this.rendererList.clear();
		try {
			for (Map.Entry<Float,IRenderer> entry : rendererListTemp.entrySet()) {
				rendererList.put(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {}
	}
	
	public void Render(float deltaTime) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		UpdateList();

		camera.update();
		batch.getProjectionMatrix().set(camera.combined);
		debugRenderer.getShapeRenderer().setProjectionMatrix(camera.combined);
		batch.begin();
		
		try {
			for (Map.Entry<Float,IRenderer> entry : rendererList.entrySet()) {
				IRenderer value = entry.getValue();
				        
				value.Render(deltaTime, batch);
				if(value.getClass() == AnimationRendererComponent.class) {
					AnimationRendererComponent temp = (AnimationRendererComponent) value;
					skeltonRenderer.draw(batch, temp.getSkeleton()); 
				}
		    }
		} catch(Exception e) { }
		PLAYERFONT.setScale(0.3f);
		
		PLAYERFONT.draw(batch, "Player 1: " + PlayerManagerSystem.playerOneLife, 10, Gdx.graphics.getHeight() - 10);
		PLAYERFONT.draw(batch, "Player 2: " + PlayerManagerSystem.playerTwoLife,Gdx.graphics.getWidth() - (Gdx.graphics.getWidth()/3), Gdx.graphics.getHeight() - 10);
		batch.end();
	}
}
