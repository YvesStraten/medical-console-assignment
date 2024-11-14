package com.yvesstraten.medicalconsole.facilities;

public class Procedure implements Comparable<Procedure> {
	private int id; 
	private String name;
	private String description;
	private boolean isElective;
	private double cost;

	public Procedure(int id, String name, String description, boolean isElective, double basicCost){
		setId(id);
		setName(name);
		setDescription(description);
		setIsElective(isElective);
		setCost(basicCost);
	}

	/** 
		* This procedure must have an id, thus, 
		* providing no details is <b>unsupported</b>
		* @see Procedure#Procedure(int, String, String, boolean, double)
		* @throws UnsupportedOperationException
	*/
	public Procedure(){
		throw new UnsupportedOperationException();
	}

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

	@Override 
	public int compareTo(Procedure o){
		if(this == o)
			return 0;
		else return getId() - o.getId(); 
	}

	@Override 
	public String toString() {
		String elective = isElective() ? "Elective" : "Non-elective";
		return new String(elective + " procedure " + getName() + " id " + getId() + " with description " + getDescription() + " and basic cost " + getCost());
	}
}
