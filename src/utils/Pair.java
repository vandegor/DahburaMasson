package utils;

public class Pair<T, U> {
	T first;
	U last;

	public Pair(final T first, final U last) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((first == null) ? 0 : first.hashCode());
		result = (prime * result) + ((last == null) ? 0 : last.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		final Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		} else if (!first.equals(other.first)) {
			return false;
		}
		if (last == null) {
			if (other.last != null) {
				return false;
			}
		} else if (!last.equals(other.last)) {
			return false;
		}
		return true;
	}

}
