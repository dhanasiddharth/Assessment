package apps.assessment.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import apps.assessment.dao.entity.Exam;
import apps.assessment.entity.Option;
import apps.assessment.entity.Question;
import apps.assessment.entity.Score;

@Repository
public class QuestionDao {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Autowired
    public void setDataSourceToJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public List<Question> getQuestions(int examId) {
        List<Question> questions = getJdbcTemplate()
                .query("SELECT id, question AS text FROM questions WHERE exam_id = ?", 
                        new Object[]{examId}, 
                        new BeanPropertyRowMapper<Question>(Question.class));
        
        for (Question question : questions) {
            question.setOptions(
                    getJdbcTemplate().query("SELECT `option` AS text, is_correct FROM options WHERE question_id = ?",
                            new Object[]{ question.getId()},
                            new BeanPropertyRowMapper<Option>(Option.class)));
        }
        
        return questions;
    }
    
    public boolean insertScore(final List<Question> questions) {
        getJdbcTemplate().batchUpdate(
                "INSERT INTO score (exam_id, question_id, user_id, score) VALUES (?,?,?,?)", 
                new BatchPreparedStatementSetter() {
            
                    public void setValues(PreparedStatement statement, int i) throws SQLException {
                        Score score = questions.get(i).getScore();
                        statement.setInt(1, score.getExamId());
                        statement.setInt(2, score.getQuestionId());
                        statement.setInt(3, score.getUserId());
                        statement.setFloat(4, score.getScore());
                    }
                    
                    public int getBatchSize() {
                        return questions.size();
                    }
                });
        
        return false;
    }
    
    public List<Exam> getExams() {
    	return getJdbcTemplate().query(
    			"SELECT * FROM exam",
    			new BeanPropertyRowMapper<Exam>(Exam.class));
    }
    
    public List<Exam> getExams(int userId) {
    	return getJdbcTemplate().query(
    			"SELECT * FROM exam JOIN user_exam ON exam.id = user_exam.exam_id " +
    			"LEFT OUTER JOIN attempt ON exam.id = attempt.exam_id " + 
    			"WHERE user_exam.user_id = ?", 
    			new Object[]{userId},
    			new BeanPropertyRowMapper<Exam>(Exam.class));
    }

	public int saveExam(apps.assessment.dao.entity.Exam exam) {
	    Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put("name", exam.getName());
        parameters.put("date", exam.getDate());
        parameters.put("start_time", exam.getStartTime());
        parameters.put("end_time", exam.getEndTime());
        parameters.put("time_limit", exam.getTimeLimit());
        parameters.put("no_of_questions", exam.getNumberOfQuestions());
        parameters.put("score_per_question", exam.getScorePerQuestion());
        parameters.put("neg_score_per_question", exam.getNegativeScorePerQuestion());
        
        final Number examId = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                        .withTableName("exam")
                        .usingGeneratedKeyColumns("id")
                        .executeAndReturnKey(parameters);
        
		return examId.intValue();
	}
	
	/**
	 * TODO: Make this dynamic
	 * @return
	 */
	public boolean insertUserExams(int examId) {
		getJdbcTemplate().update("INSERT INTO user_exam (user_id, exam_id) SELECT id, ? FROM users", 
				new Object[]{examId});
		
		return false;
	}

	public boolean saveQuestion(final Question question, int examId) {
		Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put("question", question.getText());
        parameters.put("exam_id", examId);
        
        final Number questionId = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                        .withTableName("questions")
                        .usingGeneratedKeyColumns("id")
                        .executeAndReturnKey(parameters);
        
        getJdbcTemplate().batchUpdate(
                "INSERT INTO options (question_id, `option`, is_correct) VALUES (?,?,?)", 
                new BatchPreparedStatementSetter() {
            
                    public void setValues(PreparedStatement statement, int i) throws SQLException {
                        Option option = question.getOptions().get(i);
                        statement.setInt(1, questionId.intValue());
                        statement.setString(2, option.getText());
                        statement.setInt(3, option.getIsCorrect());
                    }
                    
                    public int getBatchSize() {
                        return question.getOptions().size();
                    }
                });
        			
		return false;
	}
	
	
	public boolean makeAttempt(int userId, int examId) {
	    getJdbcTemplate().update("INSERT INTO `exam`.`attempt` (`user_id`, `exam_id`, `time`) VALUES (?, ?, now())", 
                new Object[]{userId, examId});
	    return false;
	}
}
