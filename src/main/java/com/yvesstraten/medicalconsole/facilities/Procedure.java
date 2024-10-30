package com.yvesstraten.medicalconsole.facilities;

public class Procedure {
	private int id; 
	private String name;
	private String description;
	private boolean isElective;
	private double cost;

	public int getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}

	public String getDescription(){
		return this.description;
	}

	public boolean isElective(){
		return this.isElective;
	}

	public double getCost(){
		return this.cost;
	}

	private void setId(int id){
		this.id = id;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public void setIsElective(boolean elective){
		this.isElective = elective;
	}

	public void setCost(double cost){
		this.cost = cost;
	}

	@Override
	public boolean equals(Object other){
		if(this == other)
			return true;
		else {
			if(other instanceof Procedure){
				return this.getId() == ((Procedure) other).getId();
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	// TODO: Check implementation
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new CloneNotSupportedException("Cloning is not supported");
	} 
}
