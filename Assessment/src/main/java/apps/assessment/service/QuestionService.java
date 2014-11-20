package apps.assessment.service;

import java.util.List;

import apps.assessment.entity.Question;

public interface QuestionService {
    List<Question> getQuestions(int examId);
    
    boolean insertScore(List<Question> questions);
}
