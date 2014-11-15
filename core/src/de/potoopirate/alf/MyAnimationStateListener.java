package de.potoopirate.alf;

import com.esotericsoftware.spine.AnimationState.AnimationStateListener;

import de.potoopirate.alf.entities.AnimalEntity;

public interface MyAnimationStateListener extends AnimationStateListener {
	void OnDyAnimationEnd(AnimalEntity animal);
}
