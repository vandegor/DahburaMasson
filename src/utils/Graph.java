package utils;
import java.util.Map;
import java.util.TreeMap;

public class Graph {

	private int Tdiagnosable = 0;
	private Map<Integer, Node> nodes = new TreeMap<Integer, Node>();

	public Graph(int tdiagnosable) {
		super();
		Tdiagnosable = tdiagnosable;
	}

	public Map<Integer, Node> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return nodes.toString();
	}

	public int getTdiagnosable() {
		return Tdiagnosable;
	}

}
