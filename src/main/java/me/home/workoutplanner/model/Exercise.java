package me.home.workoutplanner.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Exercise {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String name;
	
	private String difficulty;
	
	private double burn;
	
	private double reps;
	
	private String type;
	
//	private List<Day> days;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getReps() {
		return reps;
	}

	public void setReps(Double done) {
		this.reps = done;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public double getBurn() {
		return burn;
	}

	public void setBurn(double burn) {
		this.burn = burn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", name=" + name + ", difficulty=" + difficulty + ", burn=" + burn + ", reps="
				+ reps + ", type=" + type + "]";
	}

	public Exercise(String name, String difficulty, double burn, String type) {
		this.name = name;
		this.difficulty = difficulty;
		this.burn = burn;
		this.type = type;
	}
	
	public Exercise() {}

	public Exercise(String name, String difficulty, double burn, int reps, String type) {
		this.name = name;
		this.difficulty = difficulty;
		this.burn = burn;
		this.reps = reps;
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(burn);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = (int) (prime * result + reps);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exercise other = (Exercise) obj;
		if (Double.doubleToLongBits(burn) != Double.doubleToLongBits(other.burn))
			return false;
		if (difficulty == null) {
			if (other.difficulty != null)
				return false;
		} else if (!difficulty.equals(other.difficulty))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (reps != other.reps)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}