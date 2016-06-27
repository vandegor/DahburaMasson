package utils;

import javax.swing.JOptionPane;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.*;

public class GraphPrint {
	
    protected static mxGraph graph = new mxGraph();
    private mxGraphComponent graphComponent;
	
	
	public GraphPrint(){
		
	}

	
	public void DodajGraf(String text){
	        int i = 0;
	        this.getGraph().getModel().beginUpdate();
	        Object parent = this.getGraph().getDefaultParent();
	        Object v1 = this.getGraph().insertVertex(parent, null, text, 30, 30, 40, 40);
	        this.getGraph().getModel().endUpdate();
	    }
	  
	    public void Dodaj_Krawedz(int v, int w){
	        Object parent = this.getGraph().getDefaultParent();
	      //  Object v1 = this.getM().get(v);
	      //  Object v2 = this.getM().get(w);
	        Object v1 = v;
	        Object v2 = w;
	        Object v3 = this.getGraph().insertEdge(parent, null, null, v1, v2);
	    }
	


	public static mxGraph getGraph(){
		return graph;
	}
	

}
