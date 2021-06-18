package managers;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	private static final int FPS_CAP = 60;

	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay(int WIDTH, int HEIGHT, String TITLE) {
		ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTimeMilis();
	}

	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTimeMilis();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}

	public static float getCurrentTimeSec() {
		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	public static long getCurrentTimeNanos() {
	    return ((Sys.getTime() * 1000) / Sys.getTimerResolution()) * 1000000;
	}
	
	public static long getCurrentTimeMilis() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
}
