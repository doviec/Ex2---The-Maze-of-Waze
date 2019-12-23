package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DGraph implements graph{
	private int node_counter;
	private int edge_counter;
	private HashMap<Integer,node_data> hash_node;                       
	private HashMap<Integer,HashMap<Integer,edge_data>> hash_Edge;     // <src,<des,Edge>>

	public DGraph() {
		node_counter = 0;
		edge_counter = 0;
		hash_node = new HashMap<>();
		hash_Edge = new HashMap<Integer,HashMap<Integer,edge_data>>();
	}
	@Override
	public node_data getNode(int key) {
		return hash_node.get(key);
	}
	@Override
	public edge_data getEdge(int src, int dest) {
		if ( hash_Edge.get(src) == null) {
			return null;
		}
		return hash_Edge.get(src).get(dest);
	}		
	@Override
	public void addNode(node_data n) {
		hash_node.put(n.getKey(), n);
		node_counter++;
	}
	@Override
	public void connect(int src, int dest, double w) {
		Edge edge = new Edge(src,dest,w);
		if (hash_Edge.get(src) != null) {  //checks if the src(which represents a node) has a destination
			hash_Edge.get(src).put(dest,edge);
		}else {                             //add new src(key) and to src add a new hashmap(des and Edge);
			HashMap<Integer, edge_data> temp_edge = new HashMap<>();
			temp_edge.put(dest, edge);
			hash_Edge.put(src, temp_edge);	
			edge_counter++;
		}
	}
	@Override
	public Collection<node_data> getV() {
		return hash_node.values();
	}
	@Override
	public Collection<edge_data> getE(int node_id) {
		return hash_Edge.get(node_id).values();
	}
	@Override
	public node_data removeNode(int key) {
		node_data removed_node = hash_node.get(key);
		if (hash_node.get(key) != null) {
			hash_node.remove(key);	
			node_counter--;
		}
		if (hash_Edge.get(key) != null) {
			edge_counter -= hash_Edge.get(key).size();
			hash_Edge.remove(key);
		}
		for(HashMap<Integer,edge_data> edge: hash_Edge.values() ) {
			if (edge.get(key) != null) {
				hash_Edge.values().remove(key);
				edge_counter--;
			}
		}
		return removed_node;
	}
	@Override
	public edge_data removeEdge(int src, int dest) {

		edge_data edge = hash_Edge.get(src).get(dest);
		if (hash_Edge.get(src) != null) {
			hash_Edge.get(src).remove(dest);
			edge_counter--;
		}
		return edge;
	}
	@Override
	public int nodeSize() {
		return hash_node.size();
	}

	@Override
	public int edgeSize() {
		return edge_counter;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return 0;
	}
}
