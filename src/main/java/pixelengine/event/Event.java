package pixelengine.event;

public class Event {

	private final EventType eventType;

	public Event(EventType eventType){
		this.eventType = eventType;
	}

	public EventType getEventType() {
		return eventType;
	}
}
