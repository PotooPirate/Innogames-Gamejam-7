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
import de.potoopirate.alf.interfaces.IClientSystem;

public class ClientSystem extends EntitySystem implements IClientSystem {

	private Client client;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		try {
			// Start Client
			client = new Client();
			client.start();
			Network.register(client);

			client.connect(5000, "172.18.11.85", 54555);

			// Adding the main Listener to the Client
			client.addListener(new ClientListener());
		} catch (IOException e) {

		}
	}
	
	@Override
	public void throwSlot(int slotNumber, int activePath) {
		client.sendTCP(new NetworkMessage(slotNumber, activePath));
	}

	class ClientListener extends Listener {

		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof NetworkReady) {
				System.out.println("Starte das Spiel!");
			}
		}

	}
}
