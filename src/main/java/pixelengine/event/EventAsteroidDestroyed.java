package pixelengine.event;

import pixelengine.entities.Asteroid;

public class EventAsteroidDestroyed extends Event{

	private final Asteroid asteroid;

	public EventAsteroidDestroyed(Asteroid asteroid) {
		super(EventType.ASTEROIDDESTROYED);
		this.asteroid = asteroid;
	}

	public Asteroid getAsteroid() {
		return asteroid;
	}
}
