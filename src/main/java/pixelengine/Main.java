package pixelengine;

public class Main {

	private static GameBase game;

	public static void main(String[] args) {

		//game = new ChickenGame();
		game = new AsteroidsGame();
		game.startRendering();
	}

	public static void quit()  {
		System.exit(0);
	}

}