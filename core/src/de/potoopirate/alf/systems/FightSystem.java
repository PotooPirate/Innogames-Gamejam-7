package de.potoopirate.alf.systems;

import java.util.*;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import de.potoopirate.alf.components.CollisionComponent;
import de.potoopirate.alf.components.LifeComponent;
import de.potoopirate.alf.components.PathComponent;
import de.potoopirate.alf.components.PlayerComponent;
import de.potoopirate.alf.components.RaceComponent;
import de.potoopirate.alf.components.TransformComponent;
import de.potoopirate.alf.entities.AnimalEntity;
import de.potoopirate.alf.entities.MainBaseEntity;

public class FightSystem extends EntitySystem {
	/*
	 
	//Mapper for Animals and MainBases
	private ComponentMapper<PlayerComponent> 		PlayerMapper;
	private ComponentMapper<TransformComponent> 	TransformerMapper;
	
	//Animal-specific Mappers
	private ComponentMapper<RaceComponent> 		RaceMapper;
	private ComponentMapper<PathComponent>		PathMapper;
	private ComponentMapper<CollisionComponent>	CollisionMapper;
	
	//MainBase-specific Mapper
	private ComponentMapper<LifeComponent>		LifeMapper;	
	
	//Array of all Animals that are currenty alive
	private ImmutableArray<Entity> 		animals;
	//Array of all MainBases
	private ImmutableArray<Entity>		bases;
	//Arrays to sort Animals by players 
	private Array<AnimalEntity>				allAnimalsP1;
	private Array<AnimalEntity>				allAnimalsP2;
	
	private PlayerComponent					player;
	
	//The Animal-Entities, that are currently checked
	private AnimalEntity						AnimalP1;
	private AnimalEntity						AnimalP2;
	
	//Path of the two animals, that are currently checked
	private PathComponent						animal1Path;
	
	//Race of the two animals, that are currently checked
	private RaceComponent						animalP1Race;
	private RaceComponent						animalP2Race;
	
	//Transform (Position) of the two animals, that are currently checked
	private TransformComponent					animalP1Position;
	private TransformComponent					animalP2Position;
	
	//Array of animals, that has died in this iteration
	private Array<AnimalEntity>				deadAnimals;
	
	//Engine on everything is build
	private Engine engine;
	
	public FightSystem()
	{
		RaceMapper = ComponentMapper.getFor(RaceComponent.class);
		PlayerMapper = ComponentMapper.getFor(PlayerComponent.class);
		TransformerMapper = ComponentMapper.getFor(TransformComponent.class);
		LifeMapper = ComponentMapper.getFor(LifeComponent.class);
		PathMapper = ComponentMapper.getFor(PathComponent.class);
		CollisionMapper = ComponentMapper.getFor(CollisionComponent.class);
	}
	
	@Override
	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
		
		animals = engine.getEntitiesFor(Family.getFor(RaceComponent.class, PlayerComponent.class, TransformComponent.class, PathComponent.class, CollisionComponent.class));
		bases = engine.getEntitiesFor(Family.getFor(LifeComponent.class, PlayerComponent.class, TransformComponent.class));
	}
	
	@Override
	public void update(float deltaTime)
	{
		//separate animals per player. This omits of a lot of collisions between befriended animals
		for(int e=0;e<animals.size();++e)
		{
			player = PlayerMapper.get(animals.get(e));
			switch(player.id)
			{
				case 1: {allAnimalsP1.add((AnimalEntity) animals.get(e)); break;}
				case 2: {allAnimalsP2.add((AnimalEntity) animals.get(e)); break;}
			}
		}
		
		//pairwise iteration over all animals previously
		for(int e1 = 0;e1<allAnimalsP1.size;++e1)
		{
			AnimalP1 = allAnimalsP1.get(e1);
			animal1Path = PathMapper.get(AnimalP1);
			animalP1Position = TransformerMapper.get(AnimalP1);
			
			for(int e2 = 0;e2<allAnimalsP2.size;e2++)
			{
				AnimalP2 = allAnimalsP2.get(e2);
				if(animal1Path == PathMapper.get(AnimalP2))
				{
					
					animalP2Position = TransformerMapper.get(AnimalP2);
					
					float xDiff = animalP1Position.getPosition().x - animalP2Position.getPosition().x;
					float yDiff = animalP1Position.getPosition().y - animalP2Position.getPosition().y;
					
					float squareNorm = xDiff*xDiff + yDiff*yDiff;
					
					if(squareNorm < MAXIMUM_COLLISION_RANGE)
					{
						animalP1Race = RaceMapper.get(AnimalP1);
						animalP2Race = RaceMapper.get(AnimalP2);
						
						if(animalP1Race > animalP2Race)
						{
							CollisionMapper.get(AnimalP2).dead=true;
							allAnimalsP2.removeIndex(e2);
							deadAnimals.add(AnimalP2);
							--e2;
						}
						if(animalP2Race > animalP1Race)
						{
							CollisionMapper.get(AnimalP1).dead=true;
							allAnimalsP1.removeIndex(e1);
							deadAnimals.add(AnimalP2);
							--e1;
							break;
						}
					}
					
				}
			}
		}
		
		for(int e1 = 0; e1<allAnimalsP1.size;++e1)
		{
			//TODO: check collision with headquarter of P2
		}
		
		for(int e2 = 0; e2<allAnimalsP2.size;++e2)
		{
			//TODO: check collision with headquarter of P1
		}
		
		
		for(AnimalEntity e : deadAnimals)
		{
			engine.removeEntity(e);
		}
		
	}
*/	
}


//TODO: Klarstellen, wie die Race gespeichert ist und wo gespeichert wird, welche Rasse welche besiegen kann
//TODO: Klären, wie eine Kollision mit dem HQ aussieht