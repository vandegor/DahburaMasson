package utils;

import java.util.Map;
import java.util.TreeMap;

public class Graph {

	public GUI gui = new GUI();
	
	private int Tdiagnosable = 0;
	private Map<Integer, Node> nodes = new TreeMap<Integer, Node>();
	public Node n = new Node();

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

	public int size() {
		return nodes.size();
	}
	

}
