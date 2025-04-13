package dim.storage;

import java.util.HashSet;

import dim.DimClient;

public abstract class Storage<T> {
	protected final HashSet<T> hashSet = new HashSet<T>();

	public HashSet<T> getHashSet() {
		return hashSet;
	}
	
	public void init() {
		DimClient.BUS.subscribe(this);
	}
}