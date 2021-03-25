package pixelengine.event;

import pixelengine.GameBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventManager {
	private final GameBase game;

	private Map<EventType, ArrayList<IEventListener>> listeners = new HashMap<>();

	private ArrayList<Event> queuedEvents = new ArrayList<>();

	public EventManager(GameBase game){
		this.game = game;
	}

	public GameBase getGame() {
		return game;
	}

	public void post(Event event){
		ArrayList<IEventListener> list = listeners.get(event.getEventType());

		if(list != null){
			list.forEach(l -> l.invoke(event) );
		}
	}

	public void queue(Event event){
		queuedEvents.add(event);
	}

	public void update(){
		for(Event event : queuedEvents){
			post(event);
		}

		queuedEvents.clear();
	}

	public void registerListener(EventType type, IEventListener listener){
		if(!listeners.containsKey(type)){
			ArrayList<IEventListener> newList = new ArrayList<>();
			listeners.put(type, newList);
		}

		ArrayList<IEventListener> list = listeners.get(type);
		list.add(listener);
	}
}
