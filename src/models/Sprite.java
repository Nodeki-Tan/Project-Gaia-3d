package models;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import managers.DisplayManager;

public class Sprite extends Image{
	
	private int animationStart, animationEnd;
	private float animationLife;
	
	private int currentFrame;
	
	private float elapsedTime = 0.0f;
	
	public Sprite(int texture, 
			int animationStart, int animationEnd, float animationLife) {
		super(texture);
		this.animationStart = animationStart;
		this.animationEnd = animationEnd;
		this.animationLife = animationLife;
		currentFrame = animationStart;
	}
	
	public int getAnimationStart() {
		return animationStart;
	}

	public void setAnimationStart(int animationStart) {
		this.animationStart = animationStart;
		currentFrame = animationStart;
	}

	public int getAnimationEnd() {
		return animationEnd;
	}

	public void setAnimationEnd(int animationEnd) {
		this.animationEnd = animationEnd;
	}

	public float getAnimationLife() {
		return animationLife;
	}

	public void setAnimationLife(float animationLife) {
		this.animationLife = animationLife;
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	public void tick() {
		if(animationEnd != animationStart) {
		    if (elapsedTime >= animationLife) {
		    	elapsedTime = 0;
		    	currentFrame = currentFrame <= animationEnd - 1 ? currentFrame + 1: animationStart;
		    }
		    elapsedTime += DisplayManager.getCurrentTimeSec();
        }
    }
	
}
