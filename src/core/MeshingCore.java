package core;

import managers.DisplayManager;
import managers.MeshingManager;

public class MeshingCore implements Runnable{

	public Thread thread;
	
	public static boolean paused = false;
	public static boolean running = false;
	
	@Override
	public void run() {
        
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
	                System.out.println(ticks + " MeshingCore ticks");
	                ticks = 0;
	            }
        	}
        }
		
		stop();
		
	}
	
	private void tick() {
		
		MeshingManager.generateMap();
		
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
