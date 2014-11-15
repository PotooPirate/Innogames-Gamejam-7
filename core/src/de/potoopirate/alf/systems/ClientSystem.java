package de.potoopirate.alf.systems;

import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import de.potoopirate.alf.Network;
import de.potoopirate.alf.Network.NetworkMessage;
import de.potoopirate.alf.Network.NetworkReady;
import de.potoopirate.alf.interfaces.ClientListener;

public class ClientSystem extends EntitySystem implements ClientListener {

	private Client client;
	private boolean started;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);

		started = false;
		
		try {
			// Start Client
			client = new Client();
			client.start();
			Network.register(client);

			client.connect(5000, "172.18.11.197", 54555);

			//client.connect(5000, "172.18.11.197", 54555);
			//client.connect(5000, "127.0.0.1", 54555);
			// Adding the main Listener to the Client
			client.addListener(new ClientListener());
		} catch (IOException e) {

		}
	}
	
	@Override
	public void throwSlot(int slotNumber, int activePath) {
		client.sendTCP(new NetworkMessage(slotNumber, activePath));
	}

	@Override
	public boolean isStarted() {
		return started;
	}



	class ClientListener extends Listener {

		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof NetworkReady) {
				System.out.println("Starte das Spiel!");
				started = true;
			}
		}

	}
}