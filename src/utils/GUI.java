package utils;

import javax.swing.*;
import com.mxgraph.swing.*;
import com.mxgraph.view.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private JButton add;
    private JButton algo;
    private mxGraphComponent graphComponent;
    protected static mxGraph graph = new mxGraph();
    public GraphPrint gr = new GraphPrint();
    
    public GUI(){
        super("Dahbury-Masson");
        init_GUI();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void init_GUI(){
        InitWindow();
        add();
        Algorithm();
    }
     
    public void InitWindow(){
        setSize(700, 500);
        setLocationRelativeTo(null);
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(670, 380));
        getContentPane().add(graphComponent);
        setPreferredSize(new Dimension(420, 21));
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    
    public void add(){
        add = new JButton("Dodaj");
        getContentPane().add(add);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
    //        	gr.DodajGraf(text);
            	
            }
        });        
    }
    
    public void Algorithm(){
        algo = new JButton("Algorytm");
        getContentPane().add(algo);
        algo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    
}
