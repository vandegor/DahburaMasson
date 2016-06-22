package utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {

	public class GraphBuilderContainer {

		public GraphBuilderContainer randomFaulty() {

			final List<Integer> unusedRandomNumbers = new ArrayList<Integer>(graph.getNodes().keySet());

			final int faultyNumber = (int) (Math.random() * (graph.getTdiagnosable() + 1));
			for (int i = 0; i < faultyNumber; i++) {
				final int faulty = unusedRandomNumbers.remove((int) (Math.random() * unusedRandomNumbers.size()));
				graph.getNodes().forEach((nodeName, node) -> {
					if (nodeName.equals(faulty)) {
						node.getNeighboringNodes().keySet().forEach(neighbor -> {
							node.getNeighboringNodes().put(neighbor, (int) (Math.random() * 2));
						});
					} else if (node.getNeighboringNodes().containsKey(faulty)) {
						node.getNeighboringNodes().put(faulty, 1);
					}

				});
			}
			System.out.println(faultyNumber);
			return this;
		}

		public GraphBuilderContainer calculateLgraph() {

			final Graph Lgraph = copyGraphNodesPrivate(graph);

			graph.getNodes().forEach((name, node) -> {
				node.getNeighboringNodes().forEach((neighbor, faulty) -> {
					if (faulty == 1) {
						Lgraph.getNodes().get(name).getNeighboringNodes().put(neighbor, faulty);
						Lgraph.getNodes().get(neighbor).getNeighboringNodes().put(name, faulty);
					}
				});
			});
			graph = Lgraph;
			return this;
		}

		public Graph getGraph() {
			return graph;
		}
	}

	private static final String SPACE = "\\s+";
	private Graph graph;

	public GraphBuilderContainer loadFromFileWithoutFaulty(final String syndromeFile) throws IOException {
		return loadFromFile(new LineNumberReader(new FileReader(syndromeFile)), true);
	}

	public GraphBuilderContainer loadFromFileWithFaulty(final String syndromeFile) throws IOException {
		return loadFromFile(new LineNumberReader(new FileReader(syndromeFile)), false);
	}

	public GraphBuilderContainer copyGraph(final Graph graph) {
		this.graph = new Graph(graph.getTdiagnosable());
		graph.getNodes().forEach((name, node) -> {
			this.graph.getNodes().put(name, complexCopyNode(node));
		});
		return new GraphBuilderContainer();
	}

	private Graph copyGraphNodesPrivate(final Graph oldGraph) {
		final Graph graph = new Graph(oldGraph.getTdiagnosable());
		oldGraph.getNodes().forEach((name, node) -> {
			graph.getNodes().put(name, simpleCopyNode(node));
		});
		return graph;
	}

	public GraphBuilderContainer copyGraphNodes(final Graph graph) {
		this.graph = new Graph(graph.getTdiagnosable());
		graph.getNodes().forEach((name, node) -> {
			this.graph.getNodes().put(name, simpleCopyNode(node));
		});
		return new GraphBuilderContainer();
	}

	private Node simpleCopyNode(final Node oldNode) {
		final Node node = new Node();
		node.setName(oldNode.getName());
		node.setLabel(oldNode.getLabel());
		return node;
	}

	private Node complexCopyNode(final Node oldNode) {
		final Node node = new Node();
		node.setName(oldNode.getName());
		node.setLabel(oldNode.getLabel());
		node.getNeighboringNodes().putAll(oldNode.getNeighboringNodes());
		return node;
	}

	private GraphBuilderContainer loadFromFile(final LineNumberReader ln, final boolean skipFaulty) throws IOException {
		loadHeading(ln);
		loadBody(ln, skipFaulty);
		return new GraphBuilderContainer();
	}

	private void loadHeading(final LineNumberReader ln) throws IOException {

		final String[] splitedLine = ln.readLine().split(SPACE);
		final int Tdiagnosable = Integer.parseInt(splitedLine[0]);
		final int nodes = Integer.parseInt(splitedLine[1]);
		graph = new Graph(Tdiagnosable);
		for (int i = 0; i < nodes; i++) {
			graph.getNodes().put(i, new Node(i));
		}
	}

	private void loadBody(final LineNumberReader ln, final boolean skipFaulty) throws IOException {
		ln.lines().forEach(line -> {
			final String[] splitedLine = line.split(SPACE);
			final Node node = graph.getNodes().get(Math.abs(Integer.parseInt(splitedLine[0])));
			for (int i = 1; i < splitedLine.length; i++) {
				final int faluty = Integer.parseInt(splitedLine[i]);
				node.getNeighboringNodes().put(Math.abs(faluty), skipFaulty ? 0 : Integer.signum(faluty));
			}
		});
	}
}
