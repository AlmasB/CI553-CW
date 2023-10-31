package events;

public interface Listener<T> {
	
	void onChange(T t);

}
