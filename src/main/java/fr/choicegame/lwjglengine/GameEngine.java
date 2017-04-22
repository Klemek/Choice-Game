package fr.choicegame.lwjglengine;

public class GameEngine implements Runnable {

	private final Thread gameLoopThread;

	private static final int TARGET_UPS = 30;
	private static final int TARGET_FPS = 75;
	
	private IGameLogic gameLogic;
	private Window window;

	public GameEngine(String windowTitle, int width, int height, boolean vsSync, IGameLogic gameLogic)
			throws Exception {
		gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
		window = new Window(windowTitle, width, height, vsSync);
		this.gameLogic = gameLogic;
	}

	public void start() {
		gameLoopThread.start();
	}

	@Override
	public void run() {
		try {
			init();
			gameLoop();
		} catch (Exception excp) {
			excp.printStackTrace();
		} finally{
			cleanup();
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
		while (running && !window.windowShouldClose()) {
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
			if(!window.isVsync())
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
	
	protected void cleanup(){
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