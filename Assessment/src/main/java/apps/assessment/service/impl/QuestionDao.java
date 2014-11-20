package apps.assessment.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
