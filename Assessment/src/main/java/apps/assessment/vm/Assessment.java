package apps.assessment.vm;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import apps.assessment.entity.Question;
import apps.assessment.service.QuestionService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Assessment {
    private List<Question> questions;
    
    @WireVariable
    private QuestionService questionService;
    
    @Init
    public void init() {
        setQuestions(questionService.getQuestions(1));
        
        System.out.println(getQuestions());
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
}
