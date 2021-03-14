package pixelengine;

public class Main {

	private static AsteroidsGame game;

	public static void main(String[] args) {

		Input.init();

		game = new AsteroidsGame();
		game.startRendering();
	}

	public static void quit()  {
		System.exit(0);
	}

}