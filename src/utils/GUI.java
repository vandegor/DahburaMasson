package utils;

import javax.swing.*;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.*;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.Map;
import java.util.TreeMap;

public class GUI extends JFrame {

	private static final long serialVersionUID = 6189586734453132613L;
	private JButton add;
	private JButton algo;
	private mxGraphComponent graphComponent;
	private mxGraph graph = new mxGraph();
	private Map<Integer, mxCell> mxCellMap = new TreeMap<Integer, mxCell>();

	public enum Color {
		RED, GREEN, BLUE
	}

	public GUI() {
		super("Dahbury-Masson");
		init_GUI();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void init_GUI() {
		InitWindow();
		add();
		Algorithm();
	}

	public void InitWindow() {
		setSize(700, 500);
		setLocationRelativeTo(null);
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setPreferredSize(new Dimension(670, 380));
		getContentPane().add(graphComponent);
		setPreferredSize(new Dimension(420, 21));
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}

	public void buildMxGraph(Graph algorithmGraph) {
		mxCell parent = (mxCell) graph.getDefaultParent();
		graph.getModel().beginUpdate();
		algorithmGraph.getNodes().forEach((name, node) -> {
			mxCellMap.put(name, (mxCell) graph.insertVertex(parent, null, name, 30, 30, 40, 40));
			Color color = "f".equals(node.getLabel()) ? Color.RED
					: "ff".equals(node.getLabel()) ? Color.GREEN : Color.BLUE;
			setColor(color, name);
		});
		algorithmGraph.getNodes().forEach((name, node) -> {
			node.getNeighboringNodes().forEach((neighbor, value) -> {
				graph.insertEdge(parent, null, "", mxCellMap.get(name), mxCellMap.get(neighbor));
			});
		});

		graph.getModel().endUpdate();

	}

	public void setColor(Color color, int name) {
		graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, color.name(), new Object[] { mxCellMap.get(name) });
	}

	public void add() {
		add = new JButton("Dodaj");
		getContentPane().add(add);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// gr.DodajGraf(text);

			}
		});
	}

	public void Algorithm() {
		algo = new JButton("Algorytm");
		getContentPane().add(algo);
		algo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools |
																				// Templates.
			}
		});
	}

}
