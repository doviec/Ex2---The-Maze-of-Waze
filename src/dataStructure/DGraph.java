package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import utils.Point3D;

public class DGraph implements graph{
	private int edge_counter;
	private Map<Integer,node_data> nodeMap;                       
	private Map<Integer,HashMap<Integer,edge_data>> edgeMap;     // <src,<des,Edge>>
	private int MC;
	public DGraph() {
		edge_counter = 0;
		nodeMap = new HashMap<>();
		edgeMap = new HashMap<>();
		this.MC = 0;
	}

	// deep copy
	public DGraph(DGraph dGraph) {
		this.edge_counter = dGraph.edge_counter;
		this.nodeMap = new HashMap<>(dGraph.nodeMap);
		this.edgeMap = new HashMap<>(dGraph.edgeMap);
		this.MC = dGraph.MC;
	}

	@Override
	public node_data getNode(int key) {

		return nodeMap.get(key);
	}
	@Override
	public edge_data getEdge(int src, int dest) {		
		if (!(edgeMap.get(src) == null)) {
			return edgeMap.get(src).get(dest);
		}
		return null;
	}		
	@Override
	public void addNode(node_data n) {
		if (n == null) {
			throw new RuntimeException("Not allowed to add null");
		}
		this.MC++;
		nodeMap.put(n.getKey(), n);
	}
	@Override
	public void connect(int src, int dest, double w) {
		if (nodeMap.get(src) == null || nodeMap.get(dest) == null) {
			throw new RuntimeException("Not allowed to build edge to a Node that does not exist");
		}
		Edge edge = new Edge(src,dest,w);
		if (this.getEdge(src, dest) != null) {
			edgeMap.get(src).put(dest,edge);
		}else {	
			if (edgeMap.get(src) != null) {  //checks if the src(which represents a node) has a destination
				edgeMap.get(src).put(dest,edge);
				edge_counter++;
			}else {                             //add new src(key) and to src add a new hashmap(des and Edge);
				HashMap<Integer, edge_data> temp_edge = new HashMap<>();
				temp_edge.put(dest, edge);
				edgeMap.put(src, temp_edge);	
				edge_counter++;
			}
			this.MC++;
		}
	}
	@Override
	public Collection<node_data> getV() {
		return nodeMap.values();
	}
	@Override
	public Collection<edge_data> getE(int node_id) {
		if (edgeMap.get(node_id) != null) {
			return edgeMap.get(node_id).values();
		}
		return null;
	}
	@Override
	public node_data removeNode(int key) {

		if (edgeMap.get(key) != null) {
			edge_counter -= edgeMap.get(key).size();
			edgeMap.remove(key);
		}
		for(Map<Integer,edge_data> edge: edgeMap.values() ) {
			if (edge.get(key) != null) {
				edge.remove(key);
				edge_counter--;
			}
		}
		this.MC--;
		return nodeMap.remove(key);
	}
	@Override
	public edge_data removeEdge(int src, int dest) {
		if (nodeMap.get(src) == null || nodeMap.get(dest) == null || src == dest) {
			throw new RuntimeException("invalid input");
		}
		if (edgeMap.get(src) != null) {
			edge_counter--;
			this.MC--;
			return edgeMap.get(src).remove(dest);
		}
		return null;
	}
	@Override
	public int nodeSize() {
		return nodeMap.size();
	}
	@Override
	public int edgeSize() {
		return edge_counter;
	}
	@Override
	public int getMC() {
		return this.MC;
	}
}
