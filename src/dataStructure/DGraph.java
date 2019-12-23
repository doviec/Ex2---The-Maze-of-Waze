package dataStructure;

import java.util.Collection;
import java.util.HashMap;

public class DGraph implements graph{

	private HashMap<Integer,node_data> hash_node;                       
	private HashMap<Integer,HashMap<Integer,Edge>> hash_Edge;     // <src,<des,Edge>>

	public DGraph() {
		hash_node = new HashMap<>();
		hash_Edge = new HashMap<Integer,HashMap<Integer,Edge>>();
	}
	@Override
	public node_data getNode(int key) {
		if (hash_node.containsKey(key)) {
			return hash_node.get(key);
		}else {
			return null;
		}
	}
	@Override
	public edge_data getEdge(int src, int dest) {
		if ( hash_Edge.containsKey(src)) {
			if (hash_Edge.get(src).containsKey(dest)) {
				return new Edge(hash_Edge.get(src).get(dest));
			}else return null;
		}else {
			return null;
		}
	}
	@Override
	public void addNode(node_data n) {
		hash_node.put(n.getKey(), n);

	}

	@Override
	public void connect(int src, int dest, double w) {
		Edge edge = new Edge(src,dest,w);
		
		if (hash_node.containsKey(src)) {         //checks if there is a node exists at the src
			if (hash_node.containsKey(dest)) {     //checks if there is a node exists at the dest
				
				if (hash_Edge.containsKey(src)) {  //checks if the src(which represents a node) has a destination
					if (hash_Edge.get(src).containsKey(dest)) {  //checks if this edge exits already 
						System.out.println("An Edge allready exitst between the src: "+src+" and the dest: " + dest);
					}else {
						hash_Edge.get(src).put(dest, edge); //adds new dest to our given src
					}
				}else {                             //add new src(key) and to src add a new hashmap(des and Edge);
					HashMap<Integer, Edge> temp_edge = new HashMap<>();
					temp_edge.put(dest, edge);
					hash_Edge.put(src, temp_edge);					
				}
			}else {
				System.out.println("**Error** Please create a node for src");
			}
		}else {
			System.out.println("**Error** Please create a node for dest");
		}
	}

	@Override
	public Collection<node_data> getV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public node_data removeNode(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return 0;
	}

}
