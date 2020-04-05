package me.home.workoutplanner.model;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Day {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	private double inTake = 0;
	
	private double burn = 0;
	
	private double result;
	
	@ElementCollection
	@JsonIgnore
	@CollectionTable(name="meals_eaten")
	@MapKeyColumn(name="day_id")
	private Map<Meal,Double> meals;
	
	@ElementCollection
	@JsonIgnore
	@CollectionTable(name="exercises_done")
	@MapKeyColumn(name="day_id")
	private Map<Exercise,Double> repsDone;
	
	public Day(LocalDate date, Map<Exercise,Double> exercises,Map<Meal,Double> meals) {
		this.date = date;
		this.repsDone = exercises;
		this.meals = meals;
	}
	
	public Day() {
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@JsonProperty("intake")
	public double getInTake() {
		return this.inTake;
	}

	public void setInTake(double inTake) {
		this.inTake += inTake;
	}

	public double getBurn() {
		return this.burn;
	}

	public void setBurn(double burn) {
		this.burn += burn;
	}

	public double getResult() {
		return this.burn - this.inTake;
	}

	public void setResult(double result) {
		this.result = result;
	}
	
	public Map<Meal, Double> getMeals() {
		return this.meals;
	}

	public Map<Exercise, Double> getRepsDone() {
		return repsDone;
	}

	public void setRepsDone(Map<Exercise, Double> repsDone) {
		this.repsDone = repsDone;
	}

	@Override
	public String toString() {
		return "Day [id=" + id + ", date=" + date + ", inTake=" + inTake + ", burn=" + burn + ", result=" + result
				+ "]";
	}

}
