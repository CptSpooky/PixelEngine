package pixelengine.event;

import pixelengine.entities.Ship;

public class EventShipDestroyed extends Event{

	private final Ship ship;

	public EventShipDestroyed(Ship ship) {
		super(EventType.SHIPDESTROYED);
		this.ship = ship;
	}

	public Ship getShip() {
		return ship;
	}
}
