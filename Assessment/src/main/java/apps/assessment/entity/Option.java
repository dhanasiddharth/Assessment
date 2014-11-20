package apps.assessment.entity;

public class Option {
    private String text;
    private int isCorrect;
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean isCorrect() {
        return isCorrect > 0;
    }
    public void setCorrect(boolean isCorrect) {
        if(isCorrect) {
            this.isCorrect = 1;
        } else {
            this.isCorrect = 0;
        }
    }
    
    @Override
    public String toString() {
        return "Option [text=" + text + ", isCorrect=" + isCorrect + "]";
    }
    
}
