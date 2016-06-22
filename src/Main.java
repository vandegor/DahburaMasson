import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			System.out.println(new GraphBuilder().loadFromFileWithoutFaulty("testGraph.txt").randomFaulty().getGraph());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
