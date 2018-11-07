package org.JavaTest.model;

public class Banana {

	private String name;
	private int id;
	private float peice;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPeice() {
		return peice;
	}
	public void setPeice(float peice) {
		this.peice = peice;
	}
	public Banana(String name, int id, float peice) {
		super();
		this.name = name;
		this.id = id;
		this.peice = peice;
	}
	public Banana() {
		super();
	}
	
}
