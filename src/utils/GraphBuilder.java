package utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class GraphBuilder {

	private static final String SPACE = "\\s+";
	private static final int NIES = 1000000000;
	private Graph graph;

	public class GraphBuilderContainer {

		public Graph dahburaMassonAlgorithm() {
			final Graph Lgraph = calculateLgraph().getGraph();
			new GraphBuilder().copyGraph(Lgraph).findMaximumMatching().labelGraphCombinations(Lgraph);
			return graph;
		}

		public GraphBuilderContainer randomFaulty(int min) {

			// final List<Integer> unusedRandomNumbers = new ArrayList<Integer>(Arrays.asList(0, 2));
			final List<Integer> unusedRandomNumbers = new ArrayList<Integer>(graph.getNodes().keySet());

			int faultyNumber = (int) (Math.random() * (graph.getTdiagnosable() + 1));
			min = min < 0 ? 0 : (min > graph.getTdiagnosable() ? graph.getTdiagnosable() : min);
			faultyNumber = faultyNumber < min ? min : faultyNumber;
			for (int i = 0; i < faultyNumber; i++) {
				final int faulty = unusedRandomNumbers.remove((int) (Math.random() * unusedRandomNumbers.size()));

				if ((i + 1) == faultyNumber) {
					System.out.println(faulty);
				} else {
					System.out.print(faulty + " ");
				}
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

			final Deque<Integer> kolejka = new LinkedList<Integer>();
			final List<Integer> skojarzony_z = new ArrayList<Integer>(Collections.nCopies(graph.size(), NIES));
			final Graph Mgraph = new GraphBuilder().copyGraph0(graph).getGraph();

			for (int i = 0; i < graph.size(); ++i) {
				// Je�li wierzcho�ek jest ju� skojarzony pomijamy go
				if (skojarzony_z.get(i) != NIES) {
					continue;
				}
				final List<Integer> poziom = new ArrayList<Integer>(Collections.nCopies(graph.size(), 0));
				final List<Integer> ojciec = new ArrayList<Integer>(Collections.nCopies(graph.size(), NIES));
				poziom.set(i, 1);
				kolejka.clear();
				kolejka.addLast(i);

				// Ostatni to wierzcho�ek powi�kszaj�cy skojarzenie
				ostatni = NIES;

				while (!kolejka.isEmpty() && (ostatni == NIES)) {
					v = kolejka.peekFirst();
					kolejka.pollFirst();

					if ((poziom.get(v) % 2) == 1) // poziom[v] jest nieparzysty
					{
						for (final Integer j : graph.getNodes().get(v).getNeighboringNodes().keySet()) {
							u = j;
							// Je�li wierzcho�ek ma poziom != zero pomijamy
							// go
							if (poziom.get(u) != 0) {
								continue;
							}
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
				// Je�li znaleziono �cie�k� powi�kszaj�c�, to j�
				// ustawiamy
				if (ostatni != NIES) {
					for (int j = ostatni; j != NIES; j = ojciec.get(ojciec.get(j))) {
						skojarzony_z.set(j, ojciec.get(j));
						skojarzony_z.set(ojciec.get(j), j);
					}
				}

			}

			for (int j = 0; j < skojarzony_z.size(); j++) {
				if (skojarzony_z.get(j) < NIES) {
					final Node node = Mgraph.getNodes().get(j);
					node.getNeighboringNodes().put(skojarzony_z.get(j), 1);
				}
			}
			graph = Mgraph;
			return this;
		}

		public GraphBuilderContainer labelGraph() {
			int i = 0, labeled = 0;
			Set<Integer> faultySuspects = new TreeSet<Integer>();
			ArrayList<Node> faultyFreeNodes = new ArrayList<Node>();
			for (Entry<Integer, Node> entry : graph.getNodes().entrySet()) {
				boolean isFaultyFree = true;
				for (Entry<Integer, Integer> neighbor : entry.getValue().getNeighboringNodes().entrySet()) {
					if (neighbor.getValue().intValue() == 1) {
						faultySuspects.add(neighbor.getKey());
						isFaultyFree = false;
						break;
					}
				}
				if (isFaultyFree) {
					faultyFreeNodes.add(entry.getValue());
				}
			}
			Node node = faultyFreeNodes.get(i);
			node.setLabel("ff");
			while (i < faultyFreeNodes.size() && labeled <= faultySuspects.size() / 2) {
				if ("ff".equals(node.getLabel())) {
					Node suspect = null;
					for (Integer faultySuspect : faultySuspects) {
						if (node.getNeighboringNodes().containsKey(faultySuspect.intValue())) {
							suspect = graph.getNodes().get(faultySuspect);
							if (suspect.getLabel() == null || suspect.getLabel() == "") {
								node = graph.getNodes().get(faultySuspect);
								node.setLabel("f");
								labeled++;
								break;
							} else {
								suspect = null;
								continue;
							}
						}
					}
					if (suspect == null) {
						node = i + 1 < faultyFreeNodes.size() ? faultyFreeNodes.get(++i) : faultyFreeNodes.get(i++);
						node.setLabel("ff");
					}
				} else {
					for (Entry<Integer, Integer> neighbor : node.getNeighboringNodes().entrySet()) {
						if (neighbor.getValue().intValue() == 1) {
							node = graph.getNodes().get(neighbor.getKey());
							node.setLabel("ff");
							break;
						}
					}
				}
			}
			return this;
		}

		@Deprecated
		public GraphBuilderContainer labelGraphCombinations(final Graph Lgraph) {
			final List<List<Integer>> combinations = convertMgraph(graph);
			Pair<Integer, Integer> result = null;
			// System.out.println(combinations);
			for (int i = 0; i < combinations.size(); i++) {
				final List<Integer> list = combinations.get(i);
				final Set<Integer> neighbors = new TreeSet<Integer>();
				for (final Integer integer : list) {
					neighbors.addAll(Lgraph.getNodes().get(integer).getNeighboringNodes().keySet());
				}
				if (result == null) {
					result = new Pair<Integer, Integer>(i, neighbors.size());
				} else if (result.getLast() < neighbors.size()) {
					result = new Pair<Integer, Integer>(i, neighbors.size());
				}
			}

			for (Integer key : graph.getNodes().keySet()) {
				graph.getNodes().get(key).setLabel("faulty-free");
			}
			for (int i = 0; i < combinations.get(result.getFirst()).size(); i++) {
				graph.getNodes().get(combinations.get(result.getFirst()).get(i)).setLabel("faulty");
			}
			// System.out.print(result != null ? combinations.get(result.getFirst()) : null);
			return this;
		}

		@Deprecated
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

		public GraphBuilderContainer calculateLgraphMatrix() {

			final Graph Lgraph = new GraphBuilder().copyGraphNodes(graph).getGraph();
			Macierz x = new Macierz(calculateX(graph));
			Macierz y = new Macierz(calculateY(graph));
			Macierz xt = new Macierz(x.getTablice());
			Macierz z = new Macierz(x.getTablice());
			xt.transponuj();
			z.pomnoz(y);
			z.pomnoz(xt);
			double max = z.getMax();
			double[][] matrixZ = z.getTablice();

			// System.out.println(x);
			// System.out.println(y);
			// System.out.println(xt);
			// System.out.println(z);

			for (int i = 0; i < matrixZ.length; i++) {
				for (int j = 0; j < matrixZ.length; j++) {
					if (matrixZ[i][j] > 0) {
						Node node = Lgraph.getNodes().get(i);
						node.getNeighboringNodes().put(j, 1);
					}
				}
			}
			graph = Lgraph;
			return this;
		}

		public Graph getGraph() {
			return graph;
		}
	}

	private double[][] calculateX(final Graph Lgraph) {
		double[][] matrix = new double[Lgraph.size()][Lgraph.size()];
		Map<Integer, Set<Integer>> zeroLinksMap = new HashMap<Integer, Set<Integer>>();
		graph.getNodes().forEach((name, node) -> {
			check0Links(node, zeroLinksMap);
		});
		for (int i = 0; i < matrix.length; i++) {
			Set<Integer> zeroLinks = zeroLinksMap.get(i);
			for (int j = 0; j < matrix[i].length; j++) {
				if (i == j) {
					matrix[i][j] = 1;
				} else if (zeroLinks != null && zeroLinks.contains(j)) {
					matrix[i][j] = 1;
				}
			}
		}
		return matrix;// calculateMatrix(Lgraph, false);
	}

	private Set<Integer> check0Links(Node node, Map<Integer, Set<Integer>> zeroLinks) {
		Set<Integer> zeroNeighbors = new TreeSet<Integer>();
		for (Entry<Integer, Integer> entry : node.getNeighboringNodes().entrySet()) {
			int neighbor = entry.getKey();
			int value = entry.getValue();
			if (value == 0) {
				if (zeroLinks.containsKey(neighbor)) {
					zeroNeighbors.add(neighbor);
					zeroNeighbors.addAll(zeroLinks.get(neighbor));
					zeroLinks.put(node.getName(), zeroNeighbors);
				} else {
					zeroNeighbors.add(neighbor);
					zeroLinks.put(node.getName(), zeroNeighbors);
					zeroNeighbors.addAll(check0Links(graph.getNodes().get(neighbor), zeroLinks));
				}
			}
		}
		return zeroNeighbors;
	}

	private double[][] calculateY(final Graph Lgraph) {
		double[][] matrix = new double[Lgraph.size()][Lgraph.size()];
		for (int i = 0; i < matrix.length; i++) {
			Node node = Lgraph.getNodes().get(i);
			for (int j = 0; j < matrix[i].length; j++) {
				if (node.getNeighboringNodes().get(j) != null
						&& Integer.compare(node.getNeighboringNodes().get(j), 1) == 0) {

					matrix[i][j] = 1.0;
					matrix[j][i] = 1.0;
				}
			}
		}
		return matrix;
	}

	private List<List<Integer>> convertMgraph(final Graph graph) {
		final List<Pair<Integer, Integer>> pairs = new ArrayList<Pair<Integer, Integer>>();
		graph.getNodes().forEach((name, node) -> {
			node.getNeighboringNodes().forEach((neighbor, faulty) -> {
				if (pairs.stream().filter(pair -> pair.equals(new Pair<Integer, Integer>(name, neighbor))
						|| pair.equals(new Pair<Integer, Integer>(neighbor, name))).count() == 0) {
					pairs.add(new Pair<Integer, Integer>(name, neighbor));
				}
			});
		});

		// drzewo binarne po wszystkim
		final List<List<Integer>> combinations = new ArrayList<List<Integer>>();
		getCombinations(pairs, combinations, 0);
		return combinations;
	}

	private void getCombinations(final List<Pair<Integer, Integer>> pairs, final List<List<Integer>> combinations,
			final int level) {
		if (level >= pairs.size()) {
			return;
		} else if (level < 0) {
			return;
		} else {
			if (level == 0) {
				combinations.add(new ArrayList<Integer>(Collections.nCopies(1, pairs.get(level).getFirst())));
				combinations.add(new ArrayList<Integer>(Collections.nCopies(1, pairs.get(level).getLast())));
				getCombinations(pairs, combinations, level + 1);
			} else {
				final int size = combinations.size();
				for (int i = 0; i < size; i++) {
					final List<Integer> list = new ArrayList<Integer>(combinations.get(i));
					list.add(pairs.get(level).getLast());

					combinations.get(i).add(pairs.get(level).getFirst());
					combinations.add(list);
				}
				getCombinations(pairs, combinations, level + 1);
			}
		}

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

	public GraphBuilderContainer copyGraph0(final Graph graph) {
		this.graph = new Graph(graph.getTdiagnosable());
		graph.getNodes().forEach((name, node) -> {
			this.graph.getNodes().put(name, complexCopyNode0(node));
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

	private Node complexCopyNode0(final Node oldNode) {
		final Node node = new Node();
		node.setName(oldNode.getName());
		node.setLabel(oldNode.getLabel());
		node.getNeighboringNodes().putAll(oldNode.getNeighboringNodes());

		for (Integer neighbor : node.getNeighboringNodes().keySet()) {
			node.getNeighboringNodes().put(neighbor, 0);
		}
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
				node.getNeighboringNodes().put(Math.abs(faluty), skipFaulty ? 0 : Integer.signum(faluty) > 0 ? 0 : 1);
			}
		});
	}
}
