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
    public boolean getCorrect() {
        return getIsCorrect() > 0;
    }
    public void setCorrect(boolean isCorrect) {
        if(isCorrect) {
            this.setIsCorrect(1);
        } else {
            this.setIsCorrect(0);
        }
    }
    
    @Override
    public String toString() {
        return "Option [text=" + text + ", isCorrect=" + getIsCorrect() + "]";
    }
    
    public int getIsCorrect() {
        return isCorrect;
    }
    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }
    
}
