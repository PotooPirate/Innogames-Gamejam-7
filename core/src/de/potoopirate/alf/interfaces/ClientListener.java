package de.potoopirate.alf.interfaces;

public interface ClientListener {
	/**
	 * Throws the slot number X in the game
	 * @param number
	 */
	public void throwSlot(int slotNumber, int activePath);
	
	public boolean isStarted();
}
