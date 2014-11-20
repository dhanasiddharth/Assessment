package apps.assessment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import apps.assessment.entity.Question;
import apps.assessment.service.QuestionService;

@Service("questionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;
    
    public List<Question> getQuestions(int examId) {
        return questionDao.getQuestions(examId);
    }

    public boolean insertScore(List<Question> questions) {
        return questionDao.insertScore(questions);
    }

}
