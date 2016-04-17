package sk.hackcraft.util;

public class Pair<T1, T2> {
	
	private T1 first;
	
	private T2 second;
	
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	public T1 getFirst() {
		return first;
	}
	
	public T2 getSecond() {
		return second;
	}
	
	@Override
	public int hashCode() {
		return first.hashCode()+second.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Pair<?, ?>)) {
			return false;
		}
		Pair<?, ?> p = (Pair<?, ?>)o;
		return p.first == first && p.second == second;
	}
}
