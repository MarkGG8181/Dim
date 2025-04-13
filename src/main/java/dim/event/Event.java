package dim.event;

import dim.DimClient;

public class Event {
	public boolean cancelled;
	
	public void post() {
		DimClient.BUS.publish(this);
	}
}
