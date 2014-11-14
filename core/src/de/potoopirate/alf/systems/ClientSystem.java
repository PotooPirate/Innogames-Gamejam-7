package de.potoopirate.alf.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.potoopirate.alf.Network;
import de.potoopirate.alf.Network.NetworkMessage;

public class ClientSystem extends EntitySystem {

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		try {
			// Start Client
			Client client = new Client();
			client.start();
			Network.register(client);
			client.connect(5000, "172.18.11.85", 54555);
	
			// Adding the main Listener to the Client
			client.addListener(new ClientListener());
		}catch(IOException e) {
			
		}
	}

	class ClientListener extends Listener {

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
