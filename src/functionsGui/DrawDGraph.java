package functionsGui;

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
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import algorithms.GraphAlgo;
import dataStructure.*;

import utils.Point3D;

public class DrawDGraph extends JFrame implements ActionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID =28022802280228021L;
	private graph graph;
	private GraphAlgo algo;
	private int MC;


	public DrawDGraph() {
		this.graph = new DGraph();
		algo = new GraphAlgo();
		algo.init(graph);
		this.MC = graph.getMC();
		initGUI(1100, 1000);
	}
	public DrawDGraph(graph gra) {
		this.MC = gra.getMC();
		this.graph = gra;
		algo = new GraphAlgo();
		algo.init(gra);
		initGUI(1100, 1000);
		this.setVisible(true);
	}

	public DrawDGraph(graph gra, int width, int height)
	{
		this.MC = gra.getMC();
		this.graph = gra;
		algo.init(gra);
		initGUI(1100, 1000);
		initGUI(width, height);
		this.setVisible(true);
	}

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

		Menu loadFile = new Menu("Load file");
		loadFile.addActionListener(this);
		Menu saveFile = new Menu("Save file");
		saveFile.addActionListener(this);
		menu.add(saveFile);   
		menu.add(loadFile);

		Menu operations = new Menu("Operations");
		operations.addActionListener(this);

		MenuItem op_addNode = new MenuItem("Add Node");
		op_addNode.addActionListener(this);
		MenuItem op_removeNode = new MenuItem("Remove Node");
		op_removeNode.addActionListener(this);
		MenuItem op_ConnectEdge = new MenuItem("Connect Edge");
		op_ConnectEdge.addActionListener(this);
		MenuItem op_RemoveEdge = new MenuItem("Remove Edge");
		op_RemoveEdge.addActionListener(this);
		MenuItem op_shortPath = new MenuItem("Shortest Path");
		op_shortPath.addActionListener(this);
		MenuItem op_IsConnected = new MenuItem("Is connected");
		op_removeNode.addActionListener(this);
		MenuItem op_TSP = new MenuItem("TSP");
		op_removeNode.addActionListener(this);
		//add items to menu bar etc
		menuBar.add(operations);
		operations.add(op_shortPath);
		operations.add(op_addNode);
		operations.add(op_removeNode);
		operations.add(op_RemoveEdge);
		operations.add(op_ConnectEdge);
		operations.add(op_IsConnected);
		operations.add(op_TSP);		
		//action listener
		op_ConnectEdge.addActionListener(this);
		op_IsConnected.addActionListener(this);
		op_RemoveEdge.addActionListener(this);
		op_removeNode.addActionListener(this);
		op_shortPath.addActionListener(this);
		op_addNode.addActionListener(this);
		op_TSP.addActionListener(this);
		this.addMouseListener(this);

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
	}
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
	public void connectEdge() {
		JFrame frame = new JFrame("Connect an Edge");
		JLabel srcLabel = new JLabel("Src: ");
		JTextField srcTxt = new JTextField(10);
		JLabel destLabel = new JLabel("Dest: ");
		JTextField destTxt = new JTextField(10);
		JLabel weightLabel = new JLabel("weight: ");
		JTextField weightTxt = new JTextField(15);
		JButton addButton = new JButton("Add Edge");
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setBounds(150, 0, 600, 120);
		frame.add(srcLabel);
		frame.add(srcTxt);
		frame.add(destLabel);
		frame.add(destTxt);
		frame.add(weightLabel);
		frame.add(weightTxt);
		frame.add(addButton);
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int src = Integer.parseInt(srcTxt.getText());
					int dest = Integer.parseInt(destTxt.getText());
					double weight = Double.parseDouble(weightTxt.getText());
					graph.connect(src, dest, weight);
					repaint();
				}catch (Exception ex) {
					
				}
			}
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		switch (action) {
		case "Connect Edge" : connectEdge();
		}

	}
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

					int directionX = (((((((x + xDest) /2) + xDest) /2)+ xDest) /2) + xDest)/2  ;
					int directionY = (((((((y + yDest) /2) + yDest) /2)+ yDest) /2) + yDest)/2  ;

					g.fillOval(directionX, directionY, 8, 8);
				}
			}

		}
	}
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
		for (int i = 0; i < 50; i++) {
			node_data node = new Node(i,new Point3D(0,0,0), 0,"", 0);
			graph.addNode(node);
		}
		for (int i = 0; i < 50; i++) {
			j += i;
			if (((i - 3) > 0) && j < 50) {
				graph.connect(i, j, 9999);
			}
		}
		graph.connect(0, 5, 10);
		graph.connect(0, 2, 20);
		graph.connect(1, 4, 25);
		graph.connect(1, 6, 7);
		graph.connect(2, 3, 30);
		graph.connect(3, 4 , 4);
		graph.connect(4, 5, 2);
		graph.connect(4, 6, 1);
		DrawDGraph gg = new DrawDGraph(graph);

	}
}
