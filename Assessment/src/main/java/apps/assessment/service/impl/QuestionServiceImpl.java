package apps.assessment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import apps.assessment.dao.QuestionDao;
import apps.assessment.entity.Exam;
import apps.assessment.entity.Question;
import apps.assessment.entity.Score;
import apps.assessment.service.QuestionService;
import apps.assessment.service.UserService;

@Service("questionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;
    
    @Autowired
    UserService userService;
    
    public List<Question> getQuestions(int examId) {
        return questionDao.getQuestions(examId);
    }

    public boolean insertScore(List<Question> questions) {
        return questionDao.insertScore(questions);
    }

	public void computeScores(Exam exam, List<Question> questions) {
		for (Question question : questions) {
			question.setScore(calculateScore(question, exam));
		}
	}

	private Score calculateScore(Question question, Exam exam) {
		Score score = new Score();
		score.setExamId(exam.getId());
		score.setUserId(userService.getAuthenticatedUser().getIdAsInt());
		score.setQuestionId(question.getId());
		score.setScore(exam.getSocrePerQuestion() * 
				(question.getAnswer() == null ? 0: 
					question.getAnswer().isCorrect() ? 1 : 
						- exam.getNegativeScorePerQuestion()));

		return score;
	}

	public List<Exam> getExams() {
		return covertToUIExams(questionDao.getExams());
	}
	
	public List<Exam> getExams(int userId) {
		return covertToUIExams(questionDao.getExams(userId)); 
		
	}
	
	private List<Exam> covertToUIExams(List<apps.assessment.dao.entity.Exam> exams){
		List<Exam> uiExams = new ArrayList<Exam>();
		
		for (apps.assessment.dao.entity.Exam exam : exams) {
			uiExams.add(new Exam(exam));
		}
		
		return uiExams;
	}

	public int saveExam(apps.assessment.dao.entity.Exam exam) {
		int id = questionDao.saveExam(exam);
		questionDao.insertUserExams(id);
		return id;
	}

	public boolean saveQuestion(Question question, int examId) {
		return questionDao.saveQuestion(question, examId);
	}

}
