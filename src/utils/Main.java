package utils;

import java.io.IOException;

public class Main {

	public static void main(final String[] args) {
		try {
			final Graph graph = new GraphBuilder().loadFromFileWithoutFaulty("testGraph.txt").randomFaulty().getGraph();
			final Graph Lgraph = new GraphBuilder().copyGraph(graph).calculateLgraph().getGraph();
			System.out.println(graph);
			System.out.println(Lgraph);
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

}
