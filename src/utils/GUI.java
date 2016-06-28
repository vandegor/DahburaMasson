package utils;

import javax.swing.*;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.*;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.*;

import utils.GraphBuilder.GraphBuilderContainer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GUI extends JFrame {

	private static final long serialVersionUID = 6189586734453132613L;
	private mxGraphComponent graphComponent;
	private mxGraph mxgraph = new mxGraph();
	private GraphBuilderContainer graphBuilder = null;
	private Map<Integer, mxCell> mxCellMap = new TreeMap<Integer, mxCell>();
	private List<mxCell> mxEdgesList = new ArrayList<mxCell>();

	public enum Color {
		RED, GREEN, WHITE
	}

	public GUI() {
		super("Dahbury-Masson");
		init_GUI();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void init_GUI() {
		InitWindow();
		loadWithRandom();
		loadWithFaulty();
		lGraph();
		mGraph();
		labeledGraph();
	}

	public void InitWindow() {
		setSize(700, 500);
		setLocationRelativeTo(null);
		graphComponent = new mxGraphComponent(mxgraph);
		graphComponent.setPreferredSize(new Dimension(670, 380));
		getContentPane().add(graphComponent);
		setPreferredSize(new Dimension(420, 21));
		setLayout(new FlowLayout(FlowLayout.LEFT));

	}

	public void buildMxGraph() {
		Graph algorithmGraph = graphBuilder.getGraph();
		mxCell parent = (mxCell) mxgraph.getDefaultParent();
		mxCellMap.values().forEach(cell -> {
			cell.removeFromParent();
		});
		mxEdgesList.forEach(cell -> {
			cell.removeFromParent();
		});
		mxCellMap.clear();
		mxEdgesList.clear();
		mxgraph.getModel().beginUpdate();
		algorithmGraph.getNodes().forEach((name, node) -> {
			mxCellMap.put(name, (mxCell) mxgraph.insertVertex(parent, null, name, 0, 0, 40, 40));
			Color color = "f".equals(node.getLabel()) ? Color.RED
					: "ff".equals(node.getLabel()) ? Color.GREEN : Color.WHITE;
			setColor(color, name);
		});
		algorithmGraph.getNodes().forEach((name, node) -> {
			node.getNeighboringNodes().forEach((neighbor, value) -> {
				mxEdgesList.add((mxCell) mxgraph.insertEdge(parent, null, value.intValue() == 1 ? 1 : "",
						mxCellMap.get(name), mxCellMap.get(neighbor)));
			});
		});

		mxgraph.getModel().endUpdate();

		mxIGraphLayout layout = new mxCircleLayout(mxgraph);
		layout.execute(mxgraph.getDefaultParent());

	}

	public void setColor(Color color, int name) {
		mxgraph.setCellStyles(mxConstants.STYLE_FILLCOLOR, color.name(), new Object[] { mxCellMap.get(name) });
	}

	private void loadWithRandom() {
		JButton button = new JButton("za³aduj graf z losowym syndromem");
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String dir = "D:\\workspace_mars\\DahburaMasson";
					// String dir = System.getProperty("user.home");
					JFileChooser fileChooser = new JFileChooser(new File(dir));
					fileChooser.showOpenDialog(graphComponent);
					if (fileChooser.getApproveButtonMnemonic() == JFileChooser.APPROVE_OPTION
							&& fileChooser.getSelectedFile() != null) {
						System.out.println((fileChooser.getSelectedFile()));
						graphBuilder = new GraphBuilder()
								.loadFromFileWithoutFaulty((fileChooser.getSelectedFile().getAbsolutePath()))
								.randomFaulty(3);
						buildMxGraph();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void loadWithFaulty() {
		JButton button = new JButton("za³aduj graf z syndromem");
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String dir = "D:\\workspace_mars\\DahburaMasson";
					// String dir = System.getProperty("user.home");
					JFileChooser fileChooser = new JFileChooser(new File(dir));
					fileChooser.showOpenDialog(graphComponent);
					if (fileChooser.getApproveButtonMnemonic() == JFileChooser.APPROVE_OPTION
							&& fileChooser.getSelectedFile() != null) {
						System.out.println((fileChooser.getSelectedFile()));
						graphBuilder = new GraphBuilder()
								.loadFromFileWithFaulty(fileChooser.getSelectedFile().getAbsolutePath());
						buildMxGraph();
					} 
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void lGraph() {
		JButton button = new JButton("licz L-Graph");
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphBuilder.calculateLgraphMatrix();
				buildMxGraph();
			}
		});
	}

	public void mGraph() {
		JButton button = new JButton("Licz M*");
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphBuilder.findMaximumMatching();
				buildMxGraph();
			}
		});
	}

	public void labeledGraph() {
		JButton button = new JButton("Licz label");
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphBuilder.labelGraph();
				buildMxGraph();
			}
		});
	}

}
