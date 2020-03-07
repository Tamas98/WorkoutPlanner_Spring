package me.home.workoutplanner.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GetExerciseData {
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private Exercise exercise;
	
	private double reps;
	
	private long id;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public double getReps() {
		return reps;
	}

	public void setReps(double reps) {
		this.reps = reps;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
