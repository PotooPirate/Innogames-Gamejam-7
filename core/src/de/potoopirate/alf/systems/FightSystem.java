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
	private ImmutableArray<Entity> 				animals;
	//Array of all MainBases
	private ImmutableArray<Entity>				bases;
	
	private MainBaseEntity 						baseP1;
	private MainBaseEntity						baseP2;
	private TransformComponent 					baseP1Transform;
	private TransformComponent					baseP2Transform; 
	
	//Arrays to sort Animals by players 
	private Array<AnimalEntity>					allAnimalsP1;
	private Array<AnimalEntity>					allAnimalsP2;
	
	private PlayerComponent						player;
	
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
	private Array<AnimalEntity>					deadAnimals;
	
	//Engine on everything is build
	private Engine engine;
	
	public FightSystem()
	{
		PlayerMapper = ComponentMapper.getFor(PlayerComponent.class);
		TransformerMapper = ComponentMapper.getFor(TransformComponent.class);
		
		RaceMapper = ComponentMapper.getFor(RaceComponent.class);
		PathMapper = ComponentMapper.getFor(PathComponent.class);
		CollisionMapper = ComponentMapper.getFor(CollisionComponent.class);
		StatusMapper = ComponentMapper.getFor(StatusComponent.class);

		LifeMapper = ComponentMapper.getFor(LifeComponent.class);
	}
	
	@Override
	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
		
		animals = engine.getEntitiesFor(Family.getFor(RaceComponent.class, PlayerComponent.class, TransformComponent.class, PathComponent.class, CollisionComponent.class));
		bases = engine.getEntitiesFor(Family.getFor(LifeComponent.class, PlayerComponent.class, TransformComponent.class));
		for(int hq = 0;hq<bases.size();++hq)
		{
			player = PlayerMapper.get(bases.get(hq));
			if(player.id == 1)
			{
				baseP1 = (MainBaseEntity) bases.get(hq);
				baseP1Transform = TransformerMapper.get(baseP1);
			}
			else if(player.id == 2)
			{
				baseP2 = (MainBaseEntity) bases.get(hq);
				baseP2Transform = TransformerMapper.get(baseP2);
			}
		}
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
					
					
					if(xDiff*xDiff + yDiff*yDiff < MAXIMUM_COLLISION_RANGE)
					{
						animalP1Race= RaceMapper.get(AnimalP1);
						animalP2Race = RaceMapper.get(AnimalP2);
						
						//Check which animal wins the fight
						//0 wins over 1
						//1 wins over 2
						//2 wins over 0
						
						if((NaturalSelection.getStatusOfRace(animalP1Race.race) + 1) % 3 == NaturalSelection.getStatusOfRace(animalP2Race.race))
						{
							CollisionMapper.get(AnimalP2).dead=true;
							allAnimalsP2.removeIndex(e2);
							deadAnimals.add(AnimalP2);
							--e2;
						}
						if(NaturalSelection.getStatusofRace(animalP2Race.race)+1 % 3 == NaturalSelection.getStatusOfRace(animalP1Race.race))
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
			float xDiff = TransformerMapper.get(allAnimalsP1.get(e1)).getPosition().x - baseP2Transform.getPosition().x;
			float yDiff = TransformerMapper.get(allAnimalsP1.get(e1)).getPosition().y - baseP2Transform.getPosition().y;
			if(xDiff*xDiff + yDiff*yDiff < INVADE_HQ_RANGE)
			{
				LifeMapper.get(baseP2).looseLife();
			}
		}
		
		for(int e2 = 0; e2<allAnimalsP2.size;++e2)
		{
			float xDiff = TransformerMapper.get(allAnimalsP2.get(e2)).getPosition().x - baseP1Transform.getPosition().x;
			float yDiff = TransformerMapper.get(allAnimalsP2.get(e2)).getPosition().y - baseP1Transform.getPosition().y;
			if(xDiff*xDiff + yDiff*yDiff < INVADE_HQ_RANGE)
			{
				LifeMapper.get(baseP1).looseLife();
			}
		}
		
		
		for(AnimalEntity e : deadAnimals)
		{
			engine.removeEntity(e);
		}
		
	}
	*/
}