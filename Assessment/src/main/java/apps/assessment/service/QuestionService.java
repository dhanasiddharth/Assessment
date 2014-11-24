package apps.assessment.service;

import java.util.List;

import apps.assessment.entity.Exam;
import apps.assessment.entity.Question;

public interface QuestionService {
    List<Exam> getExams(int userId);
	
	List<Question> getQuestions(int examId);
    
    boolean insertScore(List<Question> questions);
    
    void computeScores(Exam exam, List<Question> questions);

	int saveExam(apps.assessment.dao.entity.Exam exam);

	boolean saveQuestion(Question question, int examId);

	List<Exam> getExams();
}
