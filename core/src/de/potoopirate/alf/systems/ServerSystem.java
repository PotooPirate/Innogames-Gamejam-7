package de.potoopirate.alf.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryonet.Server;

public class ServerSystem extends EntitySystem {

	private void startServer(){
		 Server server = new Server();
		    server.start();
		    try {
				server.bind(54555, 54777);
			
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		// TODO Auto-generated method stub
		super.addedToEngine(engine);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		super.update(deltaTime);
	}

	
}
