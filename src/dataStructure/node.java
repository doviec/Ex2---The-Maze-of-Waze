package dataStructure;

import utils.Point3D;

public class node implements node_data {

	private int key;
	private Point3D location;
	private double weight;
	private String info;
	private int tag;

	public node() {
		int infinty = 99999999;
		key = infinty;
		location = new Point3D(infinty,infinty,infinty);
		weight = infinty;
		info = "";
		tag = infinty;

	}
	public node(int key, Point3D location, double weight, String info, int tag) {
		this.key = key;
		this.location = location;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}
	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public Point3D getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(Point3D p) {
		this.location = new Point3D(p);
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}

}
