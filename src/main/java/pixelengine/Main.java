package pixelengine;

public class Main {

	private static Game game;

	public static void main(String[] args) {
		game = new Game();
		game.startRendering();
	}

	public static void quit()  {
		System.exit(0);
	}

}