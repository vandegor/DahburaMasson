package utils;

import javax.swing.JOptionPane;

import com.mxgraph.swing.*;
import com.mxgraph.view.*;

public class GraphPrint { 
	
    protected static mxGraph graph = new mxGraph();
    private mxGraphComponent graphComponent;
	
	
	public GraphPrint(){
		
	}

	
	public void DodajGraf(){
	        int i = 0;
	        this.getGraph().getModel().beginUpdate();
	        Object parent = this.getGraph().getDefaultParent();
	        Object v1 = this.getGraph().insertVertex(parent, null, "", 30, 30, 40, 40);
	        this.getGraph().getModel().endUpdate();
	        System.out.println("dodano");
	    }
	  
	    public void Dodaj_Krawedz(int v, int w){
	        Object parent = this.getGraph().getDefaultParent();
	        Object v1 = v;
	        Object v2 = w;
	        
	        System.out.println(v1);
	        Object v3 = this.getGraph().insertEdge(parent, null, "txt", v1, v2);
	        
	        System.out.println("dodano22");
	    }
	


	public static mxGraph getGraph(){
		return graph;
	}
	

}
