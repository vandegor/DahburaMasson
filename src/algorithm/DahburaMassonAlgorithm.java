package algorithm;

import utils.Graph;
import utils.GraphBuilder;

public class DahburaMassonAlgorithm {

	public DahburaMassonAlgorithm() {

	}

	public Graph calculateLgraph(final Graph graph) {
		return new GraphBuilder().copyGraph(graph).calculateLgraph().getGraph();
	}
}
