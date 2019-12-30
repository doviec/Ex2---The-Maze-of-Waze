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
import dataStructure.node_data;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class GraphAlgo implements graph_algorithms{

	private DGraph graph;
	
	public GraphAlgo() {
		
	}
	@Override
	public void init(graph g) {
		this.graph = (DGraph) g;

	}

	@Override
	public void init(String file_name) {
		GraphAlgo saveGraph = new GraphAlgo();
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream input = new ObjectInputStream(file);
			saveGraph = (GraphAlgo) input.readObject();
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
			out.writeObject(graph);
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
		for (node_data node : graph.getV()) {   //checks through every node if its connected to all others.
			src = node.getKey();
			flag = true;
			HashSet<Integer> keySet = new HashSet<>();         
			for (node_data differentNode : graph.getV()) {     
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

		if (graph.getE(src) == null || graph.getE(src).isEmpty()) {   //checks if src has any destinations (has edges)
			return false;
		}
		Collection<edge_data> edges = graph.getE(src);
		for (edge_data dest : edges) {
			if (dest.getDest() == key) {
				return true;
			}
		}
		boolean flag = false;
		boolean temp;
		for (edge_data dest : edges) {

			if ( !( keySet.contains(dest.getDest()))) {          //checks if we've been to this destination.
				keySet.add(dest.getDest());
				temp = isSrcConnected(dest.getDest(), key, keySet);  // if not then we recursively check if this dest is connected to our key;
				flag = flag || temp;
				if (temp == true) {
					return true;
				}
			}
		}
		return flag; 
	}

	@Override
	public double shortestPathDist(int src, int dest) {        //info: visited, weight: cost
		if (graph.getNode(src) == null || graph.getNode(dest) == null || src == dest) {
			throw new RuntimeException("Please use valid nodes for this method");
		}
		ArrayList<node_data> listNotVisited = new ArrayList<node_data>();  //list of all unvisited nodes
		node_data currentNode = null;
		for (node_data node : graph.getV()) {     //initiate all weights to infinity except our current node.
			listNotVisited.add(node);              //adds all nodes to list
			if (node.getKey() == src) {
				node.setWeight(0);                
				currentNode = node;
			}else {
				node.setWeight(999999);
			}
		}
		node_data nextNode;
		int destKey;
		double checkWeight,nodeWeight;
		while(!(listNotVisited.isEmpty())) {
			nextNode = null;
			double currentNodeWeight = currentNode.getWeight();
			double minWeight = 999999;
			for (edge_data neighbour : graph.getE(currentNode.getKey()) ) {
				destKey = neighbour.getDest();     //the key of the edge
				checkWeight = currentNodeWeight + neighbour.getWeight();    //the cost of getting to the neighbor
				nodeWeight = graph.getNode(destKey).getWeight();
				if (checkWeight <= nodeWeight) {   //if the cost is cheaper 
					graph.getNode(destKey).setWeight(checkWeight);         // update the weight
					graph.getNode(destKey).setInfo(currentNode.getInfo() + currentNode.getKey() + "");
				}//if we haven't visited and the node has a minimum weight than update nextNode and minWeight
				if (listNotVisited.contains(graph.getNode(destKey)) && nodeWeight <= minWeight){ 
					nextNode = graph.getNode(destKey);
					minWeight = nodeWeight;
				}
			}
			listNotVisited.remove(currentNode);
			if (nextNode == null && !(listNotVisited.isEmpty())) {
				currentNode = listNotVisited.get(0);
			}else {
				currentNode = nextNode;
			}
		}
		return graph.getNode(dest).getWeight();
	}
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		
		List<node_data> path = new ArrayList<node_data>();
		shortestPathDist(src, dest);
		String stringPath = graph.getNode(dest).getInfo();
		int lengthPath = graph.getNode(dest).getInfo().length();
		int key;
		for (int i = 0; i < lengthPath; i++) {
			key = Character.getNumericValue(stringPath.charAt(i));
			path.add(graph.getNode(key));
		}
		path.add(graph.getNode(dest));
		return path;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public graph copy() {
		return new DGraph(this.graph);

	}

}
