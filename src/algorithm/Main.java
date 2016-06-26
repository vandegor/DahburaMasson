package algorithm;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import utils.Graph;
import utils.GraphBuilder;
import utils.Node;

public class Main {

	public static void main(final String[] args) {
		try {
			final Graph graph = new GraphBuilder().loadFromFileWithoutFaulty("testGraph.txt").getGraph();
			final Graph syndrome = new GraphBuilder().copyGraph(graph).randomFaulty(2).getGraph();
			final Graph Lgraph2 = new GraphBuilder().copyGraph(syndrome).calculateLgraph().getGraph();
			final Graph Lgraph = new GraphBuilder().copyGraph(syndrome).calculateLgraphMatrix().getGraph();
			final Graph Mgraph = new GraphBuilder().copyGraph(Lgraph).findMaximumMatching().getGraph();
			final Graph labeled = new GraphBuilder().copyGraph(Mgraph).labelGraph(Lgraph).getGraph();
			// System.out.println(graph);
			// System.out.println(syndrome);
			 System.out.println(Lgraph);
			 System.out.println(Lgraph2);
			// System.out.println(Mgraph);
			System.out.println(labeled);
			// new GraphBuilder().copyGraph(syndrome).dahburaMassonAlgorithm();

		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	public static void testMgraph(Graph graph) {
		int max = 0;
		int max2 = 0;
		for (int i = 0; i < 1000; i++) {
			final Graph graph2 = new GraphBuilder().copyGraph(graph).randomFaulty(0).calculateLgraph()
					.findMaximumMatching().getGraph();
			Set<Integer> neighbors = new TreeSet<Integer>();
			for (Entry<Integer, Node> entry : graph2.getNodes().entrySet()) {
				for (Integer neighbor : entry.getValue().getNeighboringNodes().keySet()) {
					neighbors.add(neighbor);
				}
				if (max2 < entry.getValue().getNeighboringNodes().size())
					max2 = entry.getValue().getNeighboringNodes().size();
			}
			if (max < neighbors.size())
				max = neighbors.size();
		}
		System.out.println(max);
		System.out.println(max2);
	}

}
