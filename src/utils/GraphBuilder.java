package utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GraphBuilder {

	private static final String SPACE = "\\s+";
	private static final int NIES = 1000000000;
	private Graph graph;

	public class GraphBuilderContainer {

		public Graph dahburaMassonAlgorithm() {
			Graph Lgraph = this.calculateLgraph().getGraph();
			new GraphBuilder().copyGraph(Lgraph).findMaximumMatching().labelGraph(Lgraph);
			return graph;
		}

		public GraphBuilderContainer randomFaulty() {

			final List<Integer> unusedRandomNumbers = new ArrayList<Integer>(graph.getNodes().keySet());

			final int faultyNumber = (int) (Math.random() * (graph.getTdiagnosable() + 1));
			for (int i = 0; i < faultyNumber; i++) {
				final int faulty = unusedRandomNumbers.remove((int) (Math.random() * unusedRandomNumbers.size()));

				if (i + 1 == faultyNumber)
					System.out.println(faulty);
				else
					System.out.print(faulty + " ");
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
			return this;
		}

		public GraphBuilderContainer findMaximumMatching() {

			int v, u, ostatni;

			Deque<Integer> kolejka = new LinkedList<Integer>();
			List<Integer> skojarzony_z = new ArrayList<Integer>(Collections.nCopies(graph.size(), NIES));
			final Graph Mgraph = new GraphBuilder().copyGraphNodes(graph).getGraph();

			for (int i = 0; i < graph.size(); ++i) {
				// Jeœli wierzcho³ek jest ju¿ skojarzony pomijamy go
				if (skojarzony_z.get(i) != NIES) {
					continue;
				}
				List<Integer> poziom = new ArrayList<Integer>(Collections.nCopies(graph.size(), 0));
				List<Integer> ojciec = new ArrayList<Integer>(Collections.nCopies(graph.size(), NIES));
				poziom.set(i, 1);
				kolejka.clear();
				kolejka.addLast(i);

				// Ostatni to wierzcho³ek powiêkszaj¹cy skojarzenie
				ostatni = NIES;

				while (!kolejka.isEmpty() && ostatni == NIES) {
					v = kolejka.peekFirst();
					kolejka.pollFirst();

					if (poziom.get(v) % 2 == 1) // poziom[v] jest nieparzysty
					{
						for (Integer j : graph.getNodes().get(v).getNeighboringNodes().keySet()) {
							u = j;
							// Jeœli wierzcho³ek ma poziom != zero pomijamy go
							if (poziom.get(u) != 0)
								continue;
							// u jest nieskojarzony
							if (skojarzony_z.get(u) == NIES) {
								ojciec.set(u, v);
								ostatni = u;
								break;
							} else if (skojarzony_z.get(u) != v) {
								// u skojarzony, ale nie z v
								poziom.set(u, poziom.get(v) + 1);
								ojciec.set(u, v);
								kolejka.addLast(u);
							}
						}
					} else {// poziom[v] jest parzysty
						poziom.set(skojarzony_z.get(v), poziom.get(v) + 1);
						ojciec.set(skojarzony_z.get(v), v);
						kolejka.addLast(skojarzony_z.get(v));
					}
				}
				// Jeœli znaleziono œcie¿kê powiêkszaj¹c¹, to j¹ ustawiamy
				if (ostatni != NIES) {
					for (int j = ostatni; j != NIES; j = ojciec.get(ojciec.get(j))) {
						skojarzony_z.set(j, ojciec.get(j));
						skojarzony_z.set(ojciec.get(j), j);
					}
				}

			}

			for (int j = 0; j < skojarzony_z.size(); j++) {
				if (skojarzony_z.get(j) < NIES) {
					Node node = Mgraph.getNodes().get(j);
					node.getNeighboringNodes().put(skojarzony_z.get(j), 1);
				}
			}
			graph = Mgraph;
			return this;
		}

		public GraphBuilderContainer labelGraph(Graph Lgraph) {
			List<Pair<Integer, Integer>> pairs = convertMgraph(graph);
			System.out.println(pairs);
			pairs.forEach(pair -> {
			});
			return this;
		}

		public GraphBuilderContainer calculateLgraph() {

			final Graph Lgraph = new GraphBuilder().copyGraphNodes(graph).getGraph();

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

	private List<Pair<Integer, Integer>> convertMgraph(Graph graph) {
		List<Pair<Integer, Integer>> pairs = new ArrayList<Pair<Integer, Integer>>();
		graph.getNodes().forEach((name, node) -> {
			node.getNeighboringNodes().forEach((neighbor, faulty) -> {
				if (pairs.stream().filter(pair -> pair.getFirst() == name && pair.getLast() == neighbor
						|| pair.getFirst() == neighbor && pair.getLast() == name).count() == 0) {
					pairs.add(new Pair<Integer, Integer>(name, neighbor));
				}
			});
		});

		return pairs;

	}

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
