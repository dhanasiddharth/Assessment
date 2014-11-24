package apps.assessment.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import apps.assessment.dao.entity.Exam;
import apps.assessment.service.QuestionService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ExamMnager {
	private Exam exam;
	
	@WireVariable
	private QuestionService questionService;
	
	@Init
	public void init() {
		setExam(new Exam());
	}
	
	@NotifyChange("exam")
	@Command
	public void saveExam(){
		questionService.saveExam(exam);
		Clients.showNotification("Saved Exam");
		setExam(new Exam());
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}
