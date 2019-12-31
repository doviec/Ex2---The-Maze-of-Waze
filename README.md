# Ex2 The-Maze-of-Waze
Ex2 this project represents a data structure infrastructure for the next projects to come. In addition this project develops a graph data structure that allows the user to operate a variety of algorithms towards the graph. The graph will be visual by using class Gui.
  ## Fundamental Classes of Ex2
### Class Node 
This class represents the set of operations applicable on a node (vertex) in a (directional) weighted graph

### Class Edge 
This class represents the set of operations applicable on a directional edge(src,dest) in a (directional) weighted graph.

### Class DGraph
This class represents a directional weighted graph. The interface supports a large number of nodes.
Dgraph is represented bye three elements; interger counter, HashMap of Nodes and a HashMap of a Hashmap.

### Class GraphAlgo 
This interface represents the "regular" Graph Theory algorithms including:
 *  clone();
 *  init(String file_name)
 *  save(String file_name)
 *  isConnected()
 *  double shortestPathDist(int src, int dest)
 *  List<Node> shortestPath(int src, int dest)

### Class DrawDGraph
This class represents a graph by a graphic window.  In addition it may read a file and operate the algorithms that are relevant in the graphic window. 
