package algorithm;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import utils.GUI;
import utils.Graph;
import utils.GraphBuilder;
import utils.GraphPrint;
import utils.Node;

public class Main {

	public static void main(final String[] args) {
		try {

			final Graph graph = new GraphBuilder().loadFromFileWithoutFaulty("testGraph.txt").getGraph();
			// System.out.println(graph);
			final Graph syndrome = new GraphBuilder().copyGraph(graph).randomFaulty(1).getGraph();
			// System.out.println(syndrome);
			 final Graph Lgraph2 = new GraphBuilder().copyGraph(syndrome).calculateLgraph().getGraph();
			// System.out.println(Lgraph2);
			final Graph Lgraph = new GraphBuilder().copyGraph(syndrome).calculateLgraphMatrix().getGraph();
			System.out.println(Lgraph);
			final Graph Mgraph = new GraphBuilder().copyGraph(Lgraph).findMaximumMatching().getGraph();
			System.out.println(Mgraph);
			final Graph labeled = new GraphBuilder().copyGraph(Mgraph).labelGraph().getGraph();
			System.out.println(labeled);
			final GraphPrint gr = new GraphPrint();

			// new GraphBuilder().copyGraph(syndrome).dahburaMassonAlgorithm();
			// graph.getAlgo();
			for (int i = 0; i < Lgraph.size(); i++) {
				System.out.println("--------------------");
				gr.DodajGraf(null);
				for (Entry<Integer, Integer> entry : Lgraph.getNodes().get(i).getNeighboringNodes().entrySet()) {
					System.out.println(entry.getKey());
					gr.Dodaj_Krawedz(i, entry.getKey());
					
				}
			}

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
