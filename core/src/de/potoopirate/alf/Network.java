package de.potoopirate.alf;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(NetworkMessage.class);
		kryo.register(NetworkReady.class);
	}
	
	static public class NetworkMessage {
		public int animalType;
		public int pathType;
		
		public NetworkMessage(){
			
		}
		public NetworkMessage(int animalType, int pathType ){
			
			this.animalType = animalType;
			this.pathType = pathType;
		}
	}
	
	static public class NetworkReady {
		
	}
}

