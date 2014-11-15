package de.potoopirate.alf.systems;

import java.util.*;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

public class Fight extends EntitySystem {
	/*
	 
	//Mapper for Animals and MainBases
	private ComponentMapper<Player> 	PlayerMapper;
	private ComponentMapper<Transform> 	TransformerMapper;
	
	//Animal-specific Mappers
	private ComponentMapper<Race> 		RaceMapper;
	private ComponentMapper<Path>		PathMapper;
	private ComponentMapper<Collision>	CollisionMapper;
	
	//MainBase-specific Mapper
	private ComponentMapper<Life>		LifeMapper;	
	
	//Array of all Animals that are currenty alive
	private ImmutableArray<Animal> 		animals;
	//Array of all MainBases
	private ImmutableArray<MainBase>	bases;
	//Arrays to sort Animals by players 
	private Array<Animal>				allAnimalsP1;
	private Array<Animal>				allAnimalsP2;
	
	//The Animal-Entities, that are currently checked
	private Animal						AnimalP1;
	private Animal						AnimalP2;
	
	//Path of the two animals, that are currently checked
	private Path						Animal1Path;
	private Path						Animal2Path;
	
	//Race of the two animals, that are currently checked
	private Race						AnimalP1Race;
	private Race						AnimalP2Race;
	
	//Transform (Position) of the two animals, that are currentyl checked
	private Transform					animalP1Position;
	private Transform					animalP2Position;
	
	//Array of animals, that has died in this iteration
	private Array<Animal>				deadAnimals;
	
	//Engine on everything is build
	private Engine engine;
	
	public Fight()
	{
		RaceMapper = ComponentMapper.getFor(Race.class);
		PlayerMapper = ComponentMapper.getFor(Player.class);
		TransformerMapper = ComponentMapper.getFor(Transform.class);
		LifeMapper = ComponentMapper.getFor(Life.class);
		PathMapper = ComponentMapper.getFor(Path.class);
		ColissionMapper = ComponentMapper.getFor(Collision.class);
	}
	
	@Override
	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
		
		animals = engine.getEntitiesFor(Family.getFor(Race.class, Player.class, Transform.class, Path.class, Colission.class));
		bases = engine.getEntitiesFor(Family.getFor(Life.class, Player.class, Transform.class));
	}
	
	@Override
	public void update(float deltaTime)
	{
		//seperate animals per player. This omits of a lot of collisions between friended animals
		for(int e=0;e<animals.size();++e)
		{
			EntityPlayer = PlayerMapper.get(animals.get(e));
			switch(EntityPlayer.getPlayerNumber())
			{
				case 1: {allAnimalsP1.add(animals.get(e));}
				case 2: {allAnimalsP2.add(animals.get(e));}
			}
		}
		
		//pairwise iteration over all animals previously
		for(int e1 = 0;e1<allAnimalsP1.size;++e1)
		{
			AnimalP1 = allAnimalsP1.get(e1);
			Animal1Path = PathMapper.get(AnimalP1);
			animalP1Position = TranformerMapper.get(AnimalP1);
			
			for(int e2 = 0;e2<allAnimalsP2.size;e2++)
			{
				AnimalP2 = allAnimalsP2.get(e2);
				if(Animal1Path == PathMapper.get(AnimalP2))
				{
					
					animalP2Position = TranformerMapper.get(AnimalP2);
					
					float xDiff = animalP1Position.getX() - animalP2Position.getX();
					float yDiff = animalP1Position.getY() - animalP2Position.getY();
					
					float squareNorm = xDiff*xDiff + yDiff*yDiff;
					
					if(sqareNorm < MAXIMUM_COLLISION_RANGE)
					{
						animalP1Race = RaceMapper.get(AnimalP1);
						animalP2Race = RaceMapper.get(AnimalP2);
						
						
						//TODO: check for winner
						//TODO: kill Looser by setting collisionFlag
						
						//TODO: remove Looser from allAnimalsPx-Array
						
						//TODO: Add Looser to deadAnimals-Array
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
		
		
		for(Animal e : deadAnimals)
		{
			engine.removeEntity(e);
		}
		
	}
	*/
}


//TODO: Klarstellen, wie die Race gespeichert ist und wo gespeichert wird, welche Rasse welche besiegen kann
//TODO: Klären, was passiert, wenn zwei EInheiten gleichen Typs aufeinander treffen (Beide überleben? Beide Sterben?)
//TODO: Klären, wie eine Kollision mit dem HQ aussieht