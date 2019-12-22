package dataStructure;

public class Edge implements edge_data {
	private int src;
	private int des;
	private double weight;
	private String info;
	private int tag;

	public Edge() {

	}
	public Edge(int src, int des, double weight, String info, int tag) {
		this.src = src;
		this.des = des;
		this.weight =weight;
		this.info = info;
		this.tag = tag;
	}

	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.des;
	}

	@Override
	public double getWeight() {
		return this.weight;
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
