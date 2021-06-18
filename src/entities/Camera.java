package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import managers.DisplayManager;
import renderEngine.Renderer;
import toolbox.InputHandler;
import toolbox.physics.Raycaster;

public class Camera {
	
	private Vector3f position = new Vector3f(0,96,0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float cameraSpeed = 0.1f;
	private float cameraSensibility = 0.1f;
	
	private float moveAtX = 0f, moveAtZ = 0f;
	private float speed = 20f;
	
	private float elapsedTime = 0.0f;

	public Raycaster ray;

	public Camera(){
		ray = new Raycaster(Renderer.getProjectionMatrix());
	}
	
	public void tick() {
		if(InputHandler.isKeyDown(Keyboard.KEY_LCONTROL)) {
			cameraSpeed = 0.025f;
			speed = 100f;
		}else {
			cameraSpeed = 0.05f;
			speed = 20f;
		}

		translate(DisplayManager.getCurrentTimeSec());
		
	    if (elapsedTime >= cameraSpeed) {
	    	elapsedTime = 0;
	    	move();
	    	
	    	//System.out.println(position);
	    }

	    elapsedTime += DisplayManager.getCurrentTimeSec();
		
	}

	public void translate(float delta){

		if(InputHandler.isKeyDown(Keyboard.KEY_W)){
			moveAtZ = -speed * delta;
		}else if(InputHandler.isKeyDown(Keyboard.KEY_S)){
			moveAtZ = speed * delta;
		} else {
			moveAtZ = 0;
		}

		if(InputHandler.isKeyDown(Keyboard.KEY_D)){
			moveAtX = speed * delta;
		}else if(InputHandler.isKeyDown(Keyboard.KEY_A)){
			moveAtX = -speed * delta;
		} else {
			moveAtX = 0;
		}

		if(InputHandler.isKeyDown(Keyboard.KEY_SPACE)){
			position.y+= speed * delta;
		}else if(InputHandler.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y-= speed * delta;
		}

		//moving
		float dx = (float) -(moveAtZ * Math.sin(Math.toRadians(yaw)));
		float dz = (float) (moveAtZ * Math.cos(Math.toRadians(yaw)));

		//strafing
		float sx = (float) -(moveAtX * Math.sin(Math.toRadians(yaw - 90.f)));
		float sz = (float) (moveAtX * Math.cos(Math.toRadians(yaw - 90.f)));

		position.x += dx + sx;
		position.z += dz + sz;

	}

	public void move(){
		
		yaw += Mouse.getDX() * cameraSensibility;
		pitch += -Mouse.getDY() * cameraSensibility;
		

		
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
}
