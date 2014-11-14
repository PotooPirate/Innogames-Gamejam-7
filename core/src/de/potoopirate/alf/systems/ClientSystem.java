package de.potoopirate.alf.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import de.potoopirate.alf.Network;
import de.potoopirate.alf.Network.NetworkMessage;
import de.potoopirate.alf.Network.NetworkReady;

public class ClientSystem extends EntitySystem {

	private Client client;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		try {
			// Start Client
			client = new Client();
			client.start();
			Network.register(client);

			client.connect(5000, "localhost", 54555);

			// Adding the main Listener to the Client
			client.addListener(new ClientListener());
		} catch (IOException e) {

		}
	}

	class ClientListener extends Listener {

		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof NetworkReady) {
				System.out.println("Starte das Spiel!");
			}
		}

	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		super.update(deltaTime);

		// Action Listener
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			
			System.out.println("Left Path");
			NetworkMessage message = new NetworkMessage(1,1);
			client.sendTCP(message);
		} else if (Gdx.input.isKeyPressed(Keys.UP)) {
			System.out.println("Upper Path");
			client.sendTCP(new NetworkMessage(1, 2));
		} else if (Gdx.input.isKeyPressed(Keys.UP)) {
			System.out.println("Right Path");
			client.sendTCP(new NetworkMessage(1, 3));
		}
	}

}
