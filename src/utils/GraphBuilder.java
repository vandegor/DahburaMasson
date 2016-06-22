package utils;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {

	public class GraphBuilderContainer {

		public GraphBuilderContainer randomFaulty() {

			List<Integer> unusedRandomNumbers = new ArrayList<Integer>(graph.getNodes().keySet());

			int faultyNumber = (int) (Math.random() * (graph.getTdiagnosable() + 1));
			for (int i = 0; i < faultyNumber; i++) {
				int faulty = unusedRandomNumbers.remove((int) (Math.random() * unusedRandomNumbers.size()));
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

		public Graph getGraph() {
			return graph;
		}
	}

	private static final String SPACE = "\\s+";
	private Graph graph;

	public GraphBuilderContainer loadFromFileWithoutFaulty(String syndromeFile) throws IOException {
		return loadFromFile(new LineNumberReader(new FileReader(syndromeFile)), true);
	}

	public GraphBuilderContainer loadFromFileWithFaulty(String syndromeFile) throws IOException {
		return loadFromFile(new LineNumberReader(new FileReader(syndromeFile)), false);
	}

	private GraphBuilderContainer loadFromFile(LineNumberReader ln, boolean skipFaulty) throws IOException {
		loadHeading(ln);
		loadBody(ln, skipFaulty);
		return new GraphBuilderContainer();
	}

	private void loadHeading(LineNumberReader ln) throws IOException {

		String[] splitedLine = ln.readLine().split(SPACE);
		int Tdiagnosable = Integer.parseInt(splitedLine[0]);
		int nodes = Integer.parseInt(splitedLine[1]);
		graph = new Graph(Tdiagnosable);
		for (int i = 0; i < nodes; i++) {
			graph.getNodes().put(i, new Node(i));
		}
	}

	private void loadBody(LineNumberReader ln, boolean skipFaulty) throws IOException {
		ln.lines().forEach(line -> {
			String[] splitedLine = line.split(SPACE);
			Node node = graph.getNodes().get(Math.abs(Integer.parseInt(splitedLine[0])));
			for (int i = 1; i < splitedLine.length; i++) {
				int faluty = Integer.parseInt(splitedLine[i]);
				node.getNeighboringNodes().put(Math.abs(faluty), skipFaulty ? 0 : Integer.signum(faluty));
			}
		});
	}
}
