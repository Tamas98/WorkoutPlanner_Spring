package me.home.workoutplanner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Exercise {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String name;
	
	private String difficulty;
	
	private double burn;
	
	private int reps;
	
	private String type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
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
}