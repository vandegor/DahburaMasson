package utils;

import java.util.Map;
import java.util.TreeMap;

public class Node {
	private int name;
	private String label = "";
	private Map<Integer, Integer> neighboringNodes = new TreeMap<Integer, Integer>();

	public Node() {

	}

	public Node(int name) {
		this.name = name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label == null ? "" : label;
	}

	public Map<Integer, Integer> getNeighboringNodes() {
		return neighboringNodes;
	}

	@Override
	public String toString() {
		return neighboringNodes.toString() + " " + label + "\n";
	}

	public int size() {
		return neighboringNodes.size();
	}
	
}
