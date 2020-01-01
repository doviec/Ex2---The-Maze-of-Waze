package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import dataStructure.DGraph;
import dataStructure.Node;
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
			for (node_data differentNode : graph.getV()) {   
				HashSet<Integer> keySet = new HashSet<>(); 
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
	/**
	 * This recursive method returns true if a src node is connected to a dest or if one
	 * of its edges is connected to the dest and so on.
	 * @param src
	 * @param key
	 * @param keySet
	 * @return true if src connected to dest (not only direct)
	 */
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
			if (graph.getE(currentNode.getKey()) != null) {
				for (edge_data neighbour : graph.getE(currentNode.getKey()) ) {
					destKey = neighbour.getDest();     //the key of the edge
					checkWeight = currentNodeWeight + neighbour.getWeight();    //the cost of getting to the neighbor
					nodeWeight = graph.getNode(destKey).getWeight();
					if (checkWeight <= nodeWeight) {   //if the cost is cheaper 
						graph.getNode(destKey).setWeight(checkWeight);         // update the weight
						String newInfo = currentNode.getInfo() + ">" + currentNode.getKey() + "";
						graph.getNode(destKey).setInfo(newInfo); //saves the whole path
					}//if we haven't visited and the node has a minimum weight than update nextNode and minWeight
					if (listNotVisited.contains(graph.getNode(destKey)) && nodeWeight <= minWeight){ 
						nextNode = graph.getNode(destKey);
						minWeight = nodeWeight;
					}
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
		int lengthPath = graph.getNode(dest).getInfo().length(); //length of the path
		int key = 0;
		for (int i = 1; i < lengthPath; i++) {  
			if (stringPath.charAt(i) == '>') {  
				node_data node = new Node((Node) graph.getNode(key));
				path.add(node);
				key = 0;
			}else {
				key = key*10 + Character.getNumericValue(stringPath.charAt(i)); //key will be equal to the node in the string before the '>' 
			}
		}
		node_data node1 = new Node((Node) graph.getNode(key));
		path.add(node1);
		node_data node2 = new Node((Node) graph.getNode(dest));
		path.add(node2);
		return path;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		if (targets.size() <= 1) {
			throw new RuntimeException("Please enter at least two nodes");
		}
		List<node_data> path = new ArrayList<node_data>();
		List<node_data> temp;
		int src; 
		int dest; 
		String nodePath;
		String target;
		while (targets.size() > 1) {
			src = targets.get(0);
			dest = targets.get(1);
			targets.remove(0);
			temp = shortestPath(src, dest);
			path.addAll(temp);
			nodePath = this.graph.getNode(dest).getInfo();  //the path to dest Node
			for (int i = 1; i < targets.size(); i++) {     //checks for all the 
				target = String.valueOf(targets.get(i));
				if (contains(nodePath, target)) {
					targets.remove(i);
					i--;
				}
			}
		}		
		return path;
	}
	/**
	 * checks if string s1 contains string s2
	 * @param s1
	 * @param s2
	 * @return true if positive answer
	 */
	public boolean contains(String s1, String s2) {
		String [] str = s1.split(">");
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(s2)) {
				return true;
			}
		}return false;
	}

	@Override
	public graph copy() {
		return new DGraph(this.graph);

	}

}
