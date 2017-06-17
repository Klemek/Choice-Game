package fr.choicegame.lwjglengine;

import fr.choicegame.lwjglengine.Window.KeyEventListener;

public class GameEngine implements Runnable {

	private final Thread gameLoopThread;

	private static final int TARGET_UPS = 30;
	private static final int TARGET_FPS = 75;

	private IGameLogic gameLogic;
	private Window window;

	public GameEngine(String windowTitle, int width, int height, boolean vsSync, IGameLogic gameLogic,
			KeyEventListener keylistener) throws Exception {
		gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
		window = new Window(windowTitle, width, height, vsSync, keylistener);
		this.gameLogic = gameLogic;
	}

	public void start() {
		gameLoopThread.start();
	}

	@Override
	public void run() {
		String error = null;
		try {
			init();
			gameLoop();
		} catch (Exception e) {
			e.printStackTrace();
			error = e.getMessage();
		} finally {
			try {
				cleanup();
			} catch (Exception e) {
				e.printStackTrace();
				error = e.getMessage();
			}
			if (error != null) {
				//TODO write error file
				System.exit(-1);
			} else {
				System.exit(0);
			}

		}
	}

	protected void init() throws Exception {
		window.init();
		gameLogic.init(window);
	}

	protected void gameLoop() {
		float interval = 1f / TARGET_UPS;
		double previous = getTime();
		double steps = 0.0;
		boolean running = true;
		while (running && !window.windowShouldClose() && !gameLogic.shouldClose()) {
			double current = getTime();
			double elapsed = current - previous;
			previous = current;
			steps += elapsed;

			input();

			while (steps >= interval) {
				update(interval);
				steps -= interval;
			}

			render();
			if (!window.isVsync())
				sync(current);
		}
	}

	protected void input() {
		gameLogic.input(window);
	}

	protected void update(float interval) {
		gameLogic.update(interval);
	}

	protected void render() {
		gameLogic.render(window);
		window.update();
	}

	protected void cleanup() {
		gameLogic.cleanup();
	}

	private void sync(double loopStartTime) {
		float loopSlot = 1f / TARGET_FPS;
		double endTime = loopStartTime + loopSlot;
		while (getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
			}
		}
	}

	private double getTime() {
		return System.currentTimeMillis() / 1000d;
	}

}