package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.util.HashSet;
import java.util.List;

import org.w3c.dom.Node;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node;
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
		try { 
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(dGraph);
			out.close();
			file.close();
		} catch (IOException e) {
			System.err.println("**exception**");
		}

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
	public double shortestPathDist(int src, int dest) {        //info: visited, weight: cost
		int Infinity = (int) Double.POSITIVE_INFINITY;
		Collection<node_data> nodes = dGraph.getV(); 
		ArrayList<node_data> listNotVisited = new ArrayList<node_data>();  //list of all unvisited nodes
		node_data currentNode = null;
		
		for (node_data node : dGraph.getV()) {     //initiate all weights to infinity except our current node.
			listNotVisited.add(node);              //adds all nodes to list
			if (node.getKey() == src) {
				node.setWeight(0);                
				currentNode = node;
			}else {
				node.setWeight(Infinity);
			}
		}
		node_data nextNode = null;
		int destKey;
		double checkWeight;
		while(!(listNotVisited.isEmpty())) {
			
			double min = Double.POSITIVE_INFINITY;
			Collection<edge_data> edges = dGraph.getE(currentNode.getKey());  //edges of our current node;
			double currentNodeWeight = currentNode.getWeight();
			int minWeight = Infinity;
			for (edge_data neighbour : edges ) {
				destKey = neighbour.getDest();     //the key of the edge
				checkWeight = currentNodeWeight + neighbour.getWeight();      //the cost of getting to the neigbour
				double nodeWeight = dGraph.getNode(destKey).getWeight();
				if (checkWeight <= nodeWeight) {   //if the cost is cheaper 
					dGraph.getNode(destKey).setWeight(checkWeight);           // update it
				}
				if (!(listNotVisited.contains(dGraph.getNode(destKey))) && nodeWeight <= minWeight){
					nextNode = dGraph.getNode(destKey);
				}
			}listNotVisited.remove(currentNode);
			if (nextNode == null) {
				nextNode = listNotVisited.get(0);
			}
		}
		return dGraph.getNode(dest).getWeight();
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
