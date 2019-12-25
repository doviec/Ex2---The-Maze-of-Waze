package algorithms;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Collections;

import java.util.HashSet;
import java.util.List;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{

	private DGraph dGraph;

	@Override
	public void init(graph g) {
		DGraph saveDGraph = new DGraph((DGraph) g);

	}

	@Override
	public void init(String file_name) {
		Graph_Algo saveGraph = new Graph_Algo();
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream input = new ObjectInputStream(file);
			saveGraph = (Graph_Algo) input.readObject();
			input.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(String file_name) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
		boolean flag;
		int src;
		for (node_data node : dGraph.getV()) {   //checks through every node if its connected to all others.
			src = node.getKey();
			flag = true;
			HashSet<Integer> keySet = new HashSet<>();         
			for (node_data differentNode : dGraph.getV()) {     
				if ( differentNode.getKey() != node.getKey()) {   //if both keys arnet the same check if the node in the prior for connects to this one.
					flag = isSrcConnected(src, differentNode.getKey(),keySet);   //returns true if connectes.
				}
				if (!(flag)) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean isSrcConnected(int src, int key, HashSet keySet) {

		if (dGraph.getE(src) == null || dGraph.getE(src).isEmpty()) {   //checks if src has any destinations (has edges)
			return false;
		}
		Collection<edge_data> edges = dGraph.getE(src);
		for (edge_data dest : edges) {
			if (dest.getDest() == key) {
				return true;
			}
		}
		boolean flag = false;
		boolean temp;
		for (edge_data dest : edges) {

			if ( !(checkedPath(keySet, dest.getDest()))) {          //checks if we've been to this destination.
				temp = isSrcConnected(dest.getDest(), key, keySet);  // if not then we recursively check if this dest is connected to our key;
				flag = flag || temp;
				if (temp == true) {
					return true;
				}
			}
		}
		return flag; 
	}
	public boolean checkedPath(HashSet keySet, int dest) {
		if ( keySet.contains(dest)) {
			return true;
		}
		keySet.add(dest);
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public graph copy() {
		return new DGraph(this.dGraph);

	}

}
