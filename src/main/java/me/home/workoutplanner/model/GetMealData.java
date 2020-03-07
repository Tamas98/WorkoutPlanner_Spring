package me.home.workoutplanner.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GetMealData {
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private Meal meal;
	
	private double eaten;
	
	private long id;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public double getEaten() {
		return eaten;
	}

	public void setEaten(double eaten) {
		this.eaten = eaten;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
