package utils;

public class Pair<T, U> {
	T first;
	U last;

	public Pair(T first, U last) {
		super();
		this.first = first;
		this.last = last;
	}

	public T getFirst() {
		return first;
	}

	public U getLast() {
		return last;
	}

	@Override
	public String toString() {
		return "[" + first.toString() + ", " + last.toString() + "]";
	}

}
