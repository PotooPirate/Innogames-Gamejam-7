package de.potoopirate.alf.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.potoopirate.alf.Network;
import de.potoopirate.alf.Network.NetworkMessage;

public class ServerSystem extends EntitySystem {
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		try {
			// Start Server
			Server server = new Server();
			server.start();
			server.bind(54555);
			Network.register(server);
	
			// Adding the main Listener to the Server
			server.addListener(new ServerListener());
		}catch(IOException e) {
			
		}
	}

	class ServerListener extends Listener {

		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof NetworkMessage) {
				NetworkMessage request = (NetworkMessage) object;
				System.out.println(NetworkMessage.class + " requesting");
				System.out.println(request.animalType);

				//connection.sendTCP(sendResponse());
			}
		}
		
	}
}
