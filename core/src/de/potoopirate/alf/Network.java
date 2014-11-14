package de.potoopirate.alf;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(NetworkMessage.class);
	}
	
	static public class NetworkMessage {
		public int playerId;
		public int animalType;
		public int pathType;
	}
}

