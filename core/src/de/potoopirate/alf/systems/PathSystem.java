package de.potoopirate.alf.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import de.potoopirate.alf.components.PathComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.TransformComponent;
import de.potoopirate.alf.entities.AnimalEntity;

public class PathSystem extends EntitySystem {
	
	private List<Vector2> pathListOne;
	private List<Vector2> pathListTwo;
	private List<Vector2> pathListThree;
	
	private List<Vector2> pathListOneReverted;
	private List<Vector2> pathListTwoReverted;
	private List<Vector2> pathListThreeReverted;
	
	private List<AnimalEntity> allAnimals;
	private static PathSystem instance;
	
	public static final float speedFactor = 0.5f;
	
	private Engine engine;
	
	private float getWidth(float value) {
		return (value/6.4f) * (Gdx.graphics.getWidth()/100);
	}
	
	private float getHeight(float value) {
		return (value/4.8f) * (Gdx.graphics.getHeight()/100);
	}
	
	private PathSystem() {
		allAnimals = new ArrayList<AnimalEntity>();
		pathListOne = new ArrayList<Vector2>();
		pathListTwo = new ArrayList<Vector2>();
		pathListThree = new ArrayList<Vector2>();
		pathListOneReverted = new ArrayList<Vector2>();
		pathListTwoReverted = new ArrayList<Vector2>();
		pathListThreeReverted = new ArrayList<Vector2>();
		
		
		pathListOne.add(new Vector2(getWidth(35f),getHeight(390f)));
		pathListOne.add(new Vector2(getWidth(95f),getHeight(405f)));
		pathListOne.add(new Vector2(getWidth(120f),getHeight(425f)));
		pathListOne.add(new Vector2(getWidth(600f),getHeight(430f)));
		
		pathListTwo.add(new Vector2(getWidth(90f),getHeight(75f)));
		pathListTwo.add(new Vector2(getWidth(140f),getHeight(95f)));
		pathListTwo.add(new Vector2(getWidth(190f),getHeight(125f)));
		pathListTwo.add(new Vector2(getWidth(260f),getHeight(170f)));
		pathListTwo.add(new Vector2(getWidth(400f),getHeight(290f)));
		pathListTwo.add(new Vector2(getWidth(560f),getHeight(400f)));
		pathListTwo.add(new Vector2(getWidth(600f),getHeight(430f)));
		
		pathListThree.add(new Vector2(getWidth(530f),getHeight(30f)));
		pathListThree.add(new Vector2(getWidth(560f),getHeight(70f)));
		pathListThree.add(new Vector2(getWidth(620f),getHeight(100f)));
		pathListThree.add(new Vector2(getWidth(620f),getHeight(430f)));
		
		for(int i = (pathListOne.size()-1); i >= 0; i--) 
		{
			pathListOneReverted.add(pathListOne.get(i));
		}
		
		for(int i = (pathListTwo.size()-1); i >= 0; i--) 
		{
			pathListTwoReverted.add(pathListTwo.get(i));
		}
		
		for(int i = (pathListThree.size()-1); i >= 0; i--) 
		{
			pathListThreeReverted.add(pathListThree.get(i));
		}
		
		pathListOneReverted.add(new Vector2(35,35));
		pathListTwoReverted.add(new Vector2(35,35));
		pathListThreeReverted.add(new Vector2(35,35));
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
	}
	
	public void Update(float deltaTime) {
		try {
			for (AnimalEntity entry : allAnimals) {
				StartPath(entry, 
						entry.getComponent(PathComponent.class).currentTargetIndex ,
						entry.getComponent(PathComponent.class).pathNumber );
			}
		} catch(Exception e) {}
	}
	
	private void StartPath(AnimalEntity animal, int currentTarget, int pathID) {
		switch(pathID) {
		case 1:
			Vector2 currentTar =  animal.getComponent(PlayerComponent.class).id == 0 ?
			pathListOne.get((currentTarget < pathListOne.size()) ? currentTarget : pathListOne.size()-1)
			: pathListOneReverted.get((currentTarget < pathListOneReverted.size()) ? currentTarget : pathListOneReverted.size()-1);
			Vector2 currentPos = animal.getComponent(TransformComponent.class).getPosition();
			if(Vector2.dst(currentTar.x, currentTar.y, currentPos.x, currentPos.y) > 5) {
				MoveTo(animal, currentTar);
			}
			else {
				animal.getComponent(PathComponent.class).currentTargetIndex++;
				if(animal.getComponent(PathComponent.class).currentTargetIndex < pathListOne.size()) {
					MoveTo(animal,  animal.getComponent(PlayerComponent.class).id == 0 ? 
							pathListOne.get(animal.getComponent(PathComponent.class).currentTargetIndex)
							: pathListOneReverted.get(animal.getComponent(PathComponent.class).currentTargetIndex));
				}
			}
			break;
		case 2:
			Vector2 currentTarTwo =  animal.getComponent(PlayerComponent.class).id == 0 ?
			pathListTwo.get((currentTarget < pathListTwo.size()) ? currentTarget : pathListTwo.size()-1)
			: pathListTwoReverted.get((currentTarget < pathListTwoReverted.size()) ? currentTarget : pathListTwoReverted.size()-1);			
			Vector2 currentPosTwo = animal.getComponent(TransformComponent.class).getPosition();
			if(Vector2.dst(currentTarTwo.x, currentTarTwo.y, currentPosTwo.x, currentPosTwo.y) > 5) {
				MoveTo(animal, currentTarTwo);
			}
			else {
				animal.getComponent(PathComponent.class).currentTargetIndex++;
				if(animal.getComponent(PathComponent.class).currentTargetIndex < pathListTwo.size()) {
					MoveTo(animal, animal.getComponent(PlayerComponent.class).id == 0 ? 
							pathListTwo.get(animal.getComponent(PathComponent.class).currentTargetIndex)
							: pathListTwoReverted.get(animal.getComponent(PathComponent.class).currentTargetIndex));
				}
			}
			break;
		case 3:
			Vector2 currentTarTwoThree =  animal.getComponent(PlayerComponent.class).id == 0 ?
					pathListThree.get((currentTarget < pathListThree.size()) ? currentTarget : pathListThree.size()-1)
					: pathListThreeReverted.get((currentTarget < pathListThreeReverted.size()) ? currentTarget : pathListThreeReverted.size()-1);						
					Vector2 currentPosTwoThree = animal.getComponent(TransformComponent.class).getPosition();
			if(Vector2.dst(currentTarTwoThree.x, currentTarTwoThree.y, currentPosTwoThree.x, currentPosTwoThree.y) > 5) {
				MoveTo(animal, currentTarTwoThree);
			}
			else {
				animal.getComponent(PathComponent.class).currentTargetIndex++;
				if(animal.getComponent(PathComponent.class).currentTargetIndex < pathListThree.size()) {
					MoveTo(animal, animal.getComponent(PlayerComponent.class).id == 0 ? 
							pathListThree.get(animal.getComponent(PathComponent.class).currentTargetIndex)
							: pathListThreeReverted.get(animal.getComponent(PathComponent.class).currentTargetIndex));
				}
			}
			break;
		}
	}
	
	private void MoveTo(AnimalEntity animal, Vector2 currentTarget) {
		Vector2 temp = new Vector2(currentTarget.x, currentTarget.y);
		Vector2 direction = temp.sub(animal.getComponent(TransformComponent.class).getPosition());
		Vector2 normDir = direction.nor();
		animal.getComponent(TransformComponent.class).setPosition(animal.getComponent(TransformComponent.class).getPosition().add(normDir.scl(speedFactor)));
	}
	
	public void AddAnimal(AnimalEntity animal) {
		this.allAnimals.add(animal);
		switch (animal.getComponent(PathComponent.class).pathNumber) {
		case 1:
			StartPath(animal, animal.getComponent(PathComponent.class).currentTargetIndex, 1);
			break;
		case 2:
			StartPath(animal, animal.getComponent(PathComponent.class).currentTargetIndex, 2);
			break;
		case 3:
			StartPath(animal, animal.getComponent(PathComponent.class).currentTargetIndex, 3);
			break;
		default:
			break;
		}
	}
	
	public void RemoveAnimal(AnimalEntity animal) {
		if(allAnimals.contains(animal)) {
			allAnimals.remove(animal);
		}
	}
	
	public static PathSystem getInstance() {
		if(instance == null) {
			instance = new PathSystem();
		}
		return instance;
	}
}