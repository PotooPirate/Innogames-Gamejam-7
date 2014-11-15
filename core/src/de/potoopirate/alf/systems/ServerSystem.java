package de.potoopirate.alf.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.potoopirate.alf.Network;
import de.potoopirate.alf.Network.NetworkMessage;
import de.potoopirate.alf.Network.NetworkReady;
import de.potoopirate.alf.interfaces.SpawnListener;

public class ServerSystem extends EntitySystem {

	public static final int MAX_PLAYERS = 2;
	
	private Server server;
	private SpawnListener spawnListener;
	
	public ServerSystem(SpawnListener spawnListener) {
		this.spawnListener = spawnListener;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		try {
			// Start Server
			server = new Server();
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
				handleNetworkMessages(connection, (NetworkMessage) object);
			}
		}

		@Override
		public void connected(Connection connection) {
			if(server.getConnections().length == MAX_PLAYERS) {
				server.sendToAllTCP(new NetworkReady());
			}
			super.connected(connection);
		}
		
		private int path;
		private void handleNetworkMessages(Connection connection, NetworkMessage networkMessage) {
			path = networkMessage.pathType;
			if (connection.getID()-1 == 1) {
				if(networkMessage.pathType == 1) path = 3;
				else if (networkMessage.pathType == 3) path = 1;
			}
			spawnListener.spawnAnimal(connection.getID()-1, path, networkMessage.animalType);
			System.out.println("===============================================================");
			System.out.println("== get NetworkMessage from PlayerId: " + connection.getID());
			System.out.println("== get PathType: " + networkMessage.pathType);
			System.out.println("== get AnimalType: " + networkMessage.animalType);
			System.out.println("===============================================================");
		}
		
	}
}
