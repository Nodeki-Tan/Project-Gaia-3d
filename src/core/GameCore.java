package core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.states.GameState;
import core.states.MenuState;
import core.states.State;
import managers.DisplayManager;
import managers.StateManager;
import toolbox.InputHandler;

public class GameCore implements Runnable{

	public Thread thread;
	
	public static boolean paused = false;
	public static boolean running = false;
	
	//States
	private State gameState;
	private State menuState;
	
	private void init() {
		
		gameState = new GameState();
		menuState = new MenuState();
		StateManager.setCurrentState(gameState);
		
	}
	
	@Override
	public void run() {
        init();
        
		long lastTime = DisplayManager.getCurrentTimeNanos();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;

        long lastTimer = DisplayManager.getCurrentTimeMilis();
        double delta = 0;

        while (running) {
        	
        	if(!paused) {
        		
	            long now = DisplayManager.getCurrentTimeNanos();
	            delta += (now - lastTime) / nsPerTick;
	            lastTime = now;
	
	            while (delta >= 1) {
	                ticks++;
	                tick();
	                delta -= 1;
	            }
	
	            try {
	                Thread.sleep(2);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	
	            if (DisplayManager.getCurrentTimeMilis() - lastTimer >= 1000) {
	                lastTimer += 1000;
	                
	                System.out.println(ticks + " MainCore ticks and " + RenderCore.frames + " fps");
	                
	                RenderCore.frames = 0;
	                ticks = 0;
	            }
        	}
        }
		
		stop();
		
	}
	
	private void tick() {
		
		if(InputHandler.isKeyPressed(Keyboard.KEY_ESCAPE) || Display.isCloseRequested()){
			MapCore.running = false;
			MeshingCore.running = false;
			running = false;
		}
		
		if(StateManager.getCurrentState() != null) {
			StateManager.getCurrentState().tick();
		}
		
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	
	public synchronized void stop(){
		try {
			thread.interrupt();
			thread.join();
		} catch (InterruptedException e) {}
	}
	
}