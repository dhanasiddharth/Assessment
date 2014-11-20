package apps.assessment.entity;

import java.util.List;

public class Question {
    @Override
    public String toString() {
        return "Question [id=" + id + ", text=" + text + ", options=" + options + ", score=" + score + "]";
    }
    private int id;
    private String text;
    private List<Option> options;
    private Score score;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
    public List<Option> getOptions() {
        return options;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
    public Score getScore() {
        return score;
    }
    public void setScore(Score score) {
        this.score = score;
    }
}
