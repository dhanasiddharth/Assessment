package apps.assessment.dao.entity;

import java.sql.Date;
import java.sql.Time;

public class Exam {
    private int id;
    private String name;
    private Date date;
    private Time startTime;
    private Time endTime;
    private long timeLimit;
    private int numberOfQuestions;
    private float socrePerQuestion;
    private float negativeScorePerQuestion;
    
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public long getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
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
}
