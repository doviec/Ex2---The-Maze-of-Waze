package functionsGui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JFrame;

import dataStructure.*;
import utils.Point3D;

public class DrawDGraph extends JFrame implements ActionListener, MouseListener
{
	private graph graph;


	public DrawDGraph() {
		this.graph = null;
		initGUI(1000, 1000);
	}
	public DrawDGraph(graph gra) {
		this.graph = gra;
		initGUI(1000, 1000);
		this.setVisible(true);
	}

	public DrawDGraph(graph gra, int width, int height)
	{
		this.graph = gra;
		initGUI(width, height);
	}

	private void initGUI(int width, int heigt) 
	{
		this.setSize(width, heigt);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPoints(width, heigt);
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		Menu operations = new Menu("Operations");
		operations.addActionListener(this);

		MenuItem op_shortPath = new MenuItem("Shortest Path");
		op_shortPath.addActionListener(this);
		MenuItem op_addNode = new MenuItem("add Node");
		op_addNode.addActionListener(this);
		MenuItem op_removeNode = new MenuItem("remove Node");
		op_removeNode.addActionListener(this);

		menu.add(operations);
		operations.add(op_shortPath);
		operations.add(op_addNode);
		operations.add(op_removeNode);

		this.addMouseListener(this);
	}

	private void setPoints(int width, int height) {

		double x, y;
		Collection<node_data> nodes = this.graph.getV();
		for (node_data node : nodes) {
			x = Math.random() * width;
			y = Math.random() * (height / 1.5) ;
			Point3D point = new Point3D (x,y);
			node.setLocation(point);
			System.out.println("x: " +x+" y: "+y);
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
			g.setColor(Color.green);
			g.setFont(new Font("David",Font.ITALIC, 15));
			g.drawString(String.valueOf(node.getKey()), x, y + 1);
			if ((graph.getE(node.getKey()) != null)) {
				for (edge_data edge : graph.getE(node.getKey())) {

					node_data destNode = graph.getNode(edge.getDest());
					g.setColor(Color.red);
					int xDest = destNode.getLocation().ix();
					int yDest = destNode.getLocation().iy();
					g.drawLine(x, y, xDest, yDest);
					g.setFont(new Font("David", Font.ITALIC, 10));
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
	public void actionPerformed(ActionEvent e) 
	{


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
