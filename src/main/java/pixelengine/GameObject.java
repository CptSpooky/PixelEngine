package pixelengine;

public abstract class GameObject {
	protected VectorD position;
	protected VectorD velocity;
	protected double bounciness;

	protected GameObject(VectorD position, VectorD velocity, double bounciness) {
		this.position = position;
		this.velocity = velocity;
		this.bounciness = bounciness;
	}

	public double getBounciness() {
		return bounciness;
	}

	public void setBounciness(double bounciness){
		this.bounciness = bounciness;
	}

	public void update(int canvasWidth, int canvasHeight) {
		velocity = velocity.add(VectorD.GRAVITY).scale(.999);// friction
		position = position.add(velocity);
	}

	public abstract void render(PixelBuffer pixelBuffer);

}
