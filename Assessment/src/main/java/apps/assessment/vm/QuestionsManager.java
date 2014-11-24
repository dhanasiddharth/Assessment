package apps.assessment.vm;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Comboitem;

import apps.assessment.entity.Exam;
import apps.assessment.entity.Option;
import apps.assessment.entity.Question;
import apps.assessment.service.QuestionService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class QuestionsManager {
	private List<Exam> exams;
	private Exam selectedExam;
	private List<Question> questions;
	private Question question;
	private List<Option> options;
	private Option option;
	private boolean correctOption;
	
	
	@WireVariable
	private QuestionService questionService;
	
	@Init
	public void init() {
		setExams(questionService.getExams());
		question = new Question();
		option = new Option();
	}
	
	@NotifyChange("questions")
	@Command
	public void selectExam(@ContextParam(ContextType.TRIGGER_EVENT) SelectEvent<Comboitem, Exam> event) {
		setSelectedExam(event.getSelectedObjects().iterator().next());
		System.out.println(getSelectedExam());
		setQuestions(questionService.getQuestions(getSelectedExam().getId()));
	}
	
	@NotifyChange({"options", "option", "correctOption"})
	@Command
	public void addOption(){
		if(options == null) {
			options = new ArrayList<Option>();
		}
		
		if(correctOption) {
		    option.setIsCorrect(1);
		}
		System.out.println("Adding otion" + option);
		
		getOptions().add(option);
		option = new Option();
		correctOption = false;
	}
	
	@NotifyChange("questions")
	@Command
	public void saveQuestion(){
		question.setOptions(getOptions());
		questionService.saveQuestion(question, getSelectedExam().getId());
		
		if(questions == null) {
			questions = new ArrayList<Question>();
		}
		questions.add(question);
		question = new Question();
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
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

    public boolean getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(boolean correctOption) {
        this.correctOption = correctOption;
    }
}
