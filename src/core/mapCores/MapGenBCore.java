package core.mapCores;

import core.MapCore;
import core.RenderCore;
import managers.DisplayManager;

public class MapGenBCore implements Runnable{

    public Thread thread;

    public static boolean paused = false;
    public static boolean running = false;

    public static int ticks = 0;

    @Override
    public void run() {

        long lastTime = DisplayManager.getCurrentTimeNanos();
        double nsPerTick = 1000000000D / 60D;

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
                    ticks = 0;
                }
            }
        }

        stop();

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

    public void tick() {

        int posX = (int) RenderCore.camera.getPosition().x / MapCore.CHUNK_WIDTH;
        int posZ = (int) RenderCore.camera.getPosition().z / MapCore.CHUNK_WIDTH;

        for(int x = posX; x < posX + (MapCore.CHUNK_LOAD_DISTANCE); x++) {
            for(int z = posZ; z < posZ + (MapCore.CHUNK_LOAD_DISTANCE); z++) {

                MapCore.generateData(x,z,posX,posZ);

            }
        }

    }
}
