package gui;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import algorithms.Graph_Algo;
import dataStructure.*;

import utils.Point3D;

public class drawGraph extends JFrame implements ActionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID =28022802280228021L;
	private graph graph;
	private Graph_Algo algo;
	private int MC;
	private int nodeCounter;

	public drawGraph() {
		this.graph = new DGraph();
		algo = new Graph_Algo();
		algo.init(graph);
		this.MC = graph.getMC();
		initGUI(1100, 1000);
		nodeCounter = graph.nodeSize();
	}
	public drawGraph(graph gra) {
		this.MC = gra.getMC();
		this.graph = gra;
		algo = new Graph_Algo();
		algo.init(gra);
		initGUI(1100, 1000);
		this.setVisible(true);
		nodeCounter = gra.nodeSize();
	}
	public drawGraph(graph gra, int width, int height)
	{
		this.MC = gra.getMC();
		this.graph = gra;
		algo.init(gra);
		initGUI(1100, 1000);
		initGUI(width, height);
		nodeCounter = gra.nodeSize();
		this.setVisible(true);
	}
/**
 * Initiate the bounds of the frame by a given width and height and adding menus
 */
	private void initGUI(int width, int heigt) 
	{
		this.setSize(width, heigt);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPoints(width, heigt);
		this.setBounds(0,0,width,heigt);
		this.setTitle("Dovies Workshop Welcome"); 

		MenuBar menuBar = new MenuBar(); //menu bar initaite
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		MenuItem loadFile = new MenuItem("Load file");
		loadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				readFileDialog();

			}
		});
		MenuItem saveFile = new MenuItem("Save file");
		saveFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeFileDialog();

			}
		});
		menu.add(saveFile);   
		menu.add(loadFile);

		Menu operations = new Menu("Operations");
		operations.addActionListener(this);

		MenuItem op_addNode = new MenuItem("Add Node");
		op_addNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNode();

			}
		});
		MenuItem op_removeNode = new MenuItem("Remove Node");
		op_removeNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeNode();

			}
		});
		MenuItem op_ConnectEdge = new MenuItem("Connect Edge");
		op_ConnectEdge.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				connectEdge();

			}
		});
		MenuItem op_RemoveEdge = new MenuItem("Remove Edge");
		op_RemoveEdge.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeEdge();

			}
		});
		MenuItem op_shortPath = new MenuItem("Shortest Path");
		op_shortPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				shortPath();

			}
		});
		MenuItem op_IsConnected = new MenuItem("Is Connected");
		op_removeNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isConnected();

			}
		});
		MenuItem op_TSP = new MenuItem("TSP");
		op_removeNode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TSP();
			}
		});
		//add items to menu bar etc
		menuBar.add(operations);
		operations.add(op_addNode);
		operations.add(op_removeNode);
		operations.add(op_ConnectEdge);
		operations.add(op_RemoveEdge);
		operations.add(op_IsConnected);
		operations.add(op_shortPath);
		operations.add(op_TSP);		

		this.addMouseListener(this);
		//incase of changes of MC the graph will be repainted
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					synchronized (this) {
						if (graph.getMC() != MC) {
							repaint();
							MC = graph.getMC();
						}
					}
				}
			}
		});
		thread.start();
		this.setMenuBar(menuBar);
		this.setVisible(true);
		this.addMouseListener(this);
	}//load file
	public void readFileDialog() {
		//		try read from the file by Elizabeth
		FileDialog fd = new FileDialog(this, "Load", FileDialog.LOAD);
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		try {
			algo.init(folder + fileName);
			this.graph = algo.copy();
			repaint();
		} catch (Exception ex) {
			System.out.print("Error reading file " + ex);
		}
	}
	//save a file
	public void writeFileDialog() {
		//		 try write to the file by Elizabeth
		FileDialog fd = new FileDialog(this, "Save", FileDialog.SAVE);
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		try {
			algo.save(folder + fileName); 
			System.out.println("you folder was saved by the name: " + fileName);
		} catch (Exception ex) {
			System.out.print("Error writing file  " + ex);
		}
	}
	//all the functions of the menu arer written from here until the cases method
	public void addNode() {
		JFrame frame = new JFrame("Add Node");
		JLabel addLabel = new JLabel("Add Node");
		JButton button = new JButton("Add");
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setBounds(200, 0, 250, 100);
		frame.add(addLabel);
		frame.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int key = nodeCounter + 1;
					nodeCounter++;
					double x,y, w;
					x = (Math.random() * 800) + 100;
					y = (Math.random() * 700) + 100 ;
					w = Double.POSITIVE_INFINITY;
					node_data node = new Node(key, new Point3D(x,y,0), w, "", -1);
					graph.addNode(node);
					JOptionPane.showMessageDialog(frame, "Succefully add Node number: " + node.getKey());
					repaint();
				}catch (Exception ex) {

				}
			}
		});
	}
	public void removeNode() {
		JFrame frame = new JFrame ("Remove Node");
		JLabel nodeLabel = new JLabel("Node to remove: ");
		JTextField nodeTxt = new JTextField(10);
		JButton button = new JButton ("Remove");
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setBounds(200, 0, 250, 100);
		frame.add(nodeLabel);
		frame.add(nodeTxt);
		frame.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int keyNode = Integer.parseInt(nodeTxt.getText());
					if (graph.getNode(keyNode) == null) {
						JOptionPane.showMessageDialog(frame, "Please insert a valid Node");
					}else {
						graph.removeNode(keyNode);
						JOptionPane.showMessageDialog(frame, "Node: " + keyNode + "was removed");
						repaint();
						frame.setVisible(false);
					}
				}catch(Exception ex) {
				}
			}
		});
	}
	public void connectEdge() {
		JFrame frame = new JFrame("Connect an Edge");
		JLabel srcLabel = new JLabel("Src: ");
		JTextField srcTxt = new JTextField(10);
		JLabel destLabel = new JLabel("Dest: ");
		JTextField destTxt = new JTextField(10);
		JLabel weightLabel = new JLabel("weight: ");
		JTextField weightTxt = new JTextField(15);
		JButton button = new JButton("Add Edge");
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setBounds(150, 0, 600, 120);
		frame.add(srcLabel);
		frame.add(srcTxt);
		frame.add(destLabel);
		frame.add(destTxt);
		frame.add(weightLabel);
		frame.add(weightTxt);
		frame.add(button);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int src = Integer.parseInt(srcTxt.getText());
					int dest = Integer.parseInt(destTxt.getText());
					double weight = Double.parseDouble(weightTxt.getText());
					graph.connect(src, dest, weight);
					repaint();
					frame.setVisible(false);
				}catch (Exception ex) {
				}
			}
		});
	}
	public void removeEdge() {
		JFrame frame = new JFrame("Delete an Edge");
		JLabel srcLabel = new JLabel("Src: ");
		JTextField srcTxt = new JTextField(10);
		JLabel destLabel = new JLabel("Dest: ");
		JTextField destTxt = new JTextField(10);
		JButton button = new JButton("Remove Edge");
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setBounds(150, 0, 600, 120);
		frame.add(srcLabel);
		frame.add(srcTxt);
		frame.add(destLabel);
		frame.add(destTxt);
		frame.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int src = Integer.parseInt(srcTxt.getText());
					int dest = Integer.parseInt(destTxt.getText());
					if (graph.getNode(src) == null || graph.getNode(dest) == null) {
						JOptionPane.showMessageDialog(frame,"Please insert valid nodes for src & dest");
					}else if (graph.getEdge(src, dest) == null) {
						JOptionPane.showMessageDialog(frame,"Edge does not exist");
					}else {
						graph.removeEdge(src, dest);
						repaint();
						JOptionPane.showMessageDialog(frame,"deleted edge: " + src + "-->" + dest);
						frame.setVisible(false);
					}
				}
				catch (Exception ex) {

				}
			}
		});
	}
	public void isConnected() {
		JFrame frame = new JFrame("Is Connected");
		JLabel connectLabel = new JLabel("Check if this graph Is Connected: ");

		JButton button = new JButton("Check");
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setBounds(150, 0, 600, 120);
		frame.add(connectLabel);
		frame.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (algo.isConnected()) {
						JOptionPane.showMessageDialog(frame,"This graph is connected");
					}
					else {
						JOptionPane.showMessageDialog(frame,"This graph is not connected");
					}
				}
				catch (Exception ex) {

				}
			}
		});
	}
	public void shortPath() {
		JFrame frame = new JFrame("Short Path");
		JLabel srcLabel = new JLabel("Src: ");
		JTextField srcTxt = new JTextField(10);
		JLabel destLabel = new JLabel("Dest: ");
		JTextField destTxt = new JTextField(10);
		JButton button = new JButton("Calculate");
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setBounds(150, 0, 600, 120);
		frame.add(srcLabel);
		frame.add(srcTxt);
		frame.add(destLabel);
		frame.add(destTxt);
		frame.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int src = Integer.parseInt(srcTxt.getText());
					int dest = Integer.parseInt(destTxt.getText());
					if (graph.getNode(src) == null || graph.getNode(dest) == null) {
						JOptionPane.showMessageDialog(frame,"Please insert valid nodes for src & dest");
					}else {
						double value = algo.shortestPathDist(src, dest);
						repaint();
						JOptionPane.showMessageDialog(frame,"The price from: " + src + " to " + dest +" is: " + value);
						frame.setVisible(false);
					}

				}
				catch (Exception ex) {

				}
			}
		});
	}
	public void TSP() {
		JFrame frame = new JFrame(" TSP");
		JLabel connectLabel = new JLabel("Check for TSP ");
		JLabel elementsLabel = new JLabel("elements: ");
		JTextField addIntTxt = new JTextField(10);
		JButton addButton = new JButton("add");
		JButton checkButton = new JButton("Check");

		frame.setLayout(new FlowLayout());
		frame.setBounds(150, 0, 600, 120);
		frame.add(connectLabel);
		frame.add(elementsLabel);
		frame.add(addIntTxt);
		frame.add(addButton);			
		frame.add(checkButton);
		frame.setVisible(true);

		List<Integer> Intlist = new ArrayList<Integer>(); 
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int element = Integer.parseInt(addIntTxt.getText());
					Intlist.add(element);
				}
				catch (Exception ex) {
				}
			}
		});
		checkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String path= "";
					List<node_data> nodeList = algo.TSP(Intlist);
					int [] arr = new int [nodeList.size()];
					for(int i=0; i<nodeList.size(); i++) {
						arr[i] = nodeList.get(i).getKey();
						path += ", " + arr[i];
					}
					JOptionPane.showMessageDialog(frame,"The path is: " +path);
					frame.setVisible(false);
				}catch(Exception ex) {

				}

			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		switch (action) {
		case "Save file" : writeFileDialog();
		case "Load file" : readFileDialog();
		case "Connect Edge" : connectEdge();
		case "Remove Node" : removeNode();
		case "Add Node" : addNode();
		case "Remove Edge" : removeEdge();
		case "Is Connected" : isConnected();
		case "Shortest Path" : shortPath();
		case "TSP" : TSP();
		}

	}
	/**
	 * this method sets random locations for the nodes in the graph
	 * @param width
	 * @param height
	 */
	private void setPoints(int width, int height) {

		double x, y;
		Collection<node_data> nodes = this.graph.getV();
		for (node_data node : nodes) {
			x = Math.random() * width;
			y = Math.random() * (height / 1.5) ;
			y+=100;
			x+=100;
			Point3D point = new Point3D (x,y);
			node.setLocation(point);
		}
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		for (node_data node : graph.getV()) {
			int x = node.getLocation().ix();
			int y = node.getLocation().iy();
			g.setColor(Color.BLUE);
			g.fillOval(x - 5, y - 5, 10, 10);
			g.setColor(Color.black);
			g.setFont(new Font("David",Font.ITALIC, 18));
			g.drawString(String.valueOf(node.getKey()), x+3, y + 3);
			if ((graph.getE(node.getKey()) != null)) {
				for (edge_data edge : graph.getE(node.getKey())) {

					node_data destNode = graph.getNode(edge.getDest());
					g.setColor(Color.red);
					int xDest = destNode.getLocation().ix();
					int yDest = destNode.getLocation().iy();
					g.drawLine(x, y, xDest, yDest);
					g.setFont(new Font("David", Font.ITALIC, 15));
					g.drawString(String.valueOf(edge.getWeight()), (x + xDest) / 2, (y + yDest) / 2);
					g.setColor(Color.orange);
					//to draw the way of the edge iv simply divided 4 times in a row the middle location of the edge.
					int directionX = (((((((x + xDest) /2) + xDest) /2)+ xDest) /2) + xDest)/2  ;
					int directionY = (((((((y + yDest) /2) + yDest) /2)+ yDest) /2) + yDest)/2  ;

					g.fillOval(directionX, directionY, 9, 9);
				}
			}

		}
	}//opps by yael landau
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouseReleased");
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouseEntered");
	}
	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouseExited");
	}
	public static void main(String[] args) {
		DGraph graph = new DGraph();
		int j = 0;
		for (int i = 0; i < 6; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}
		for (int i = 0; i < 6; i++) {
			j += i;
			if (((i - 3) > 0) && j < 5) {
				graph.connect(i, j, 9999);
			}
		}
		graph.connect(0, 1, 7);
		graph.connect(1, 2, 2);
		graph.connect(2, 3, 5);
		graph.connect(3, 4, 3);
		graph.connect(4, 5, 3);
		graph.connect(5, 4 , 4);
		graph.connect(4, 5, 2);;
		graph.connect(4, 3, 1);
		graph.connect(3, 2 , 4);
		graph.connect(2, 1, 2);
		graph.connect(1, 0, 1);
		drawGraph gg = new drawGraph(graph);

	}
}
