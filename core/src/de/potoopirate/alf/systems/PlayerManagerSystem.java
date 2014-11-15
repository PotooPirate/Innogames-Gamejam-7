package de.potoopirate.alf.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

public class PlayerManagerSystem extends EntitySystem {
	public static int playerOneLife;
	public static int playerTwoLife;
	
	@Override
	public void addedToEngine(Engine engine)
	{
		super.addedToEngine(engine);
		engine.addSystem(this);
		playerOneLife = 10;
		playerTwoLife = 10;
	}
	
	@Override
	public void update(float deltaTime) {
		if(playerOneLife == 0) {
			// PlayerTwo Won!
		}
		if(playerTwoLife == 0) {
			// PlayerOne Won!
		}
	}
}
