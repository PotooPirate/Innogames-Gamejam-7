package de.potoopirate.alf.components;

import com.badlogic.ashley.core.Component;

public class PathComponent extends Component {
	public int pathNumber;
	public int currentTargetIndex;
	
	public int getPathNumber()
	{
		return this.pathNumber;
	}
}
