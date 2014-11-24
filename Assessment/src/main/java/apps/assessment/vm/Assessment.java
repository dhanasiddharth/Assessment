package apps.assessment.vm;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import apps.assessment.entity.Exam;
import apps.assessment.entity.Question;
import apps.assessment.service.QuestionService;
import apps.assessment.service.UserService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Assessment {
	private List<Exam> exams;
	private Exam selectedExam;
    private List<Question> questions;
    private boolean showQuestions;
    private Duration timeout;
    private String formattedTimeout;
        
    @WireVariable
    private QuestionService questionService;
    
    @WireVariable
    private UserService userService;
    
    @Init
    public void init() {
        setExams(questionService.getExams(userService.getAuthenticatedUser().getIdAsInt()));
        
        for (Exam exam : getExams()) {
			if (exam.getStartTime().isBefore(LocalDateTime.now()) 
					&& exam.getEndTime().isAfter(LocalDateTime.now())) {
				setSelectedExam(exam);
				break;
			}
		}
    }
 
    @NotifyChange({"showQuestions","questions"})
    @Command
    public void start() {
    	setQuestions(questionService.getQuestions(getSelectedExam().getId()));
    	setShowQuestions(true);
    	setTimeout(getSelectedExam().getDuration());
    }
    
    @Command
    public void complete() {
    	questionService.computeScores(getSelectedExam(), questions);
    	questionService.insertScore(questions);
    }
    
    @NotifyChange("formattedTimeout")
    @Command
    public void changeTimeout(){
    	if(!getShowQuestions()) {
    		return;
    	}
    	setTimeout(timeout.minusSeconds(1));
		setFormattedTimeout((timeout.getSeconds()/60) + ":" + timeout.getSeconds()%60);
		
		if(timeout.isZero()) {
			complete();
		}
    }
    
    public List<Question> getQuestions() {
    	return questions;
    }
    
    public void setQuestions(List<Question> questions) {
    	this.questions = questions;
    }

	public boolean getShowQuestions() {
		return showQuestions;
	}

	public void setShowQuestions(boolean showQuestions) {
		this.showQuestions = showQuestions;
	}

	public Duration getTimeout() {
		return timeout;
	}

	public void setTimeout(Duration timeout) {
		this.timeout = timeout;
	}

	public String getFormattedTimeout() {
		return formattedTimeout;
	}

	public void setFormattedTimeout(String formattedTimeout) {
		this.formattedTimeout = formattedTimeout;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public Exam getSelectedExam() {
		return selectedExam;
	}

	public void setSelectedExam(Exam selectedExam) {
		this.selectedExam = selectedExam;
	}
}
