package dim.event.impl;

import dim.event.Event;

public class KeyEvent extends Event {
	public final int key;

	public KeyEvent(int key) {
		super();
		this.key = key;
	}
}