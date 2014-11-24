package apps.assessment.entity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class Exam {
    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;
    private int numberOfQuestions;
    private float socrePerQuestion;
    private float negativeScorePerQuestion;
    
    public Exam() {
	}
    
    public Exam(apps.assessment.dao.entity.Exam exam) {
		this.setId(exam.getId());
		this.setName(exam.getName());
		LocalDate date = LocalDateTime.ofInstant(
				Instant.ofEpochMilli(exam.getDate().getTime()), 
				ZoneId.systemDefault())
					.toLocalDate();
		
		LocalTime start = LocalDateTime.ofInstant(
				Instant.ofEpochMilli(exam.getStartTime().getTime()), 
				ZoneId.systemDefault())
					.toLocalTime();
		
		this.setStartTime(LocalDateTime.of(date, start));
		this.setEndTime(LocalDateTime.of(date, exam.getEndTime().toLocalTime()));
		this.setDuration(Duration.ofSeconds(exam.getTimeLimit()));
		
		this.setNumberOfQuestions(exam.getNumberOfQuestions());
		this.setSocrePerQuestion(exam.getScorePerQuestion());
		this.setNegativeScorePerQuestion(exam.getNegativeScorePerQuestion());
	}
    
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public Duration getDuration() {
        return duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
    public float getSocrePerQuestion() {
        return socrePerQuestion;
    }
    public void setSocrePerQuestion(float socrePerQuestion) {
        this.socrePerQuestion = socrePerQuestion;
    }
    public float getNegativeScorePerQuestion() {
        return negativeScorePerQuestion;
    }
    public void setNegativeScorePerQuestion(float negativeScorePerQuestion) {
        this.negativeScorePerQuestion = negativeScorePerQuestion;
    }
	@Override
	public String toString() {
		return "Exam [id=" + id + ", name=" + name + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", duration=" + duration
				+ ", numberOfQuestions=" + numberOfQuestions
				+ ", socrePerQuestion=" + socrePerQuestion
				+ ", negativeScorePerQuestion=" + negativeScorePerQuestion
				+ "]";
	}
}
