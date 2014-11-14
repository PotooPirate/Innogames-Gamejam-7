package de.potoopirate.alf.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.potoopirate.alf.Network.NetworkMessage;

public class ServerSystem extends EntitySystem {

	private Server startServer() {
		Server server = new Server();
		server.start();
		try {
			server.bind(54555, 54777);

		} catch (IOException e) {
			System.err.println("cannot bind Server!! Check Ports");
		}

		// Register Classes
		Kryo kryo = server.getKryo();
		kryo.register(NetworkMessage.class);
		// TODO register all Classes

		return server;
	}

	// Main Listener
	private Listener actionListener() {
		Listener listener = new Listener() {
			public void received(Connection connection, Object object) {

				if (object instanceof NetworkMessage) {
					NetworkMessage request = (NetworkMessage) object;
					System.out.println(NetworkMessage.class + " requesting");
					System.out.println(request.animalType);

					//connection.sendTCP(sendResponse());
				}
			}
		};
		return listener;
	}

	/*private Object sendResponse() {

		NetworkMessage response = new NetworkMessage();
		response.text = "Thanks";

		return response;
	}*/

	@Override
	public void addedToEngine(Engine engine) {
		// TODO Auto-generated method stub
		super.addedToEngine(engine);

		// Start Server
		Server server = startServer();

		// Adding the main Listener to the Server
		server.addListener(actionListener());
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		super.update(deltaTime);
	}

}
