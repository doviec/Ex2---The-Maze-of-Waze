package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algorithms.GraphAlgo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class testGraphAlgo {

	@Test
	void testIsConeccted() {
		graph graph = new DGraph();
		for (int i = 0; i < 7; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(2, 0, 4);
		graph.connect(3, 4, 4);
		graph.connect(4, 0, 4);
		graph.connect(4, 6, 4);
		graph.connect(6, 2, 4);
		graph.connect(4, 0, 4);
		graph.connect(5, 6, 4);
		graph.connect(2, 5, 4);

		GraphAlgo algo = new GraphAlgo();
		algo.init(graph);
		assertTrue(algo.isConnected());
	}	@Test
	void testIsNotConeccted() {
		graph graph1 = new DGraph();
		for (int i = 0; i < 6; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph1.addNode(node);
		}	
		graph1.connect(0, 1, 2);
		graph1.connect(1, 2, 3);
		graph1.connect(2, 3, 4);
		graph1.connect(3, 0, 1);
		graph1.connect(0, 3, 4);
		graph1.connect(2, 0, 1);
		graph1.connect(3, 4, 3);
		graph1.connect(4, 5, 4);
		graph1.connect(2, 4, 1);

		GraphAlgo algo1 = new GraphAlgo();
		algo1.init(graph1);
		assertFalse(algo1.isConnected());		
	}
	@Test
	void testShortestPathDist() {

		graph graph = new DGraph();
		for (int i = 0; i < 6; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 3, 4);
		graph.connect(2, 0, 1);
		graph.connect(3, 4, 3);
		graph.connect(4, 5, 4);
		graph.connect(5, 0, 1);
		graph.connect(2, 4, 1);

		GraphAlgo algo = new GraphAlgo();
		algo.init(graph);
		assertEquals(6,algo.shortestPathDist(0, 4));
	}
	@Test
	void testShortestPathList() {
		graph graph = new DGraph();
		for (int i = 0; i < 6; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}	
		graph.connect(0, 1, 2);
		graph.connect(1, 2, 3);
		graph.connect(2, 3, 4);
		graph.connect(3, 0, 1);
		graph.connect(0, 3, 4);
		graph.connect(2, 0, 1);
		graph.connect(3, 4, 3);
		graph.connect(4, 5, 4);
		graph.connect(5, 0, 1);
		graph.connect(2, 4, 1);
		GraphAlgo algo = new GraphAlgo();
		algo.init(graph);
		List<node_data> path = algo.shortestPath(0, 4);
		String string = "";
		for (int i = 0; i < path.size()-1 ;i++) {
			string += (path.get(i).getKey()+"" + " > ");
		}
		string += (path.get(path.size()-1).getKey() + "");
		assertEquals("0 > 1 > 2 > 4", string);
	}
}
