package apps.assessment.service;

import java.util.List;

import apps.assessment.entity.Exam;
import apps.assessment.entity.Question;

public interface QuestionService {
    List<Exam> getExams();
	
	List<Question> getQuestions(int examId);
    
    boolean insertScore(List<Question> questions);
    
    void computeScores(Exam exam, List<Question> questions);
}
