package apps.assessment.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import apps.assessment.entity.Team;
import apps.assessment.entity.User;

@Repository
public class UserDao {
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
    
    public String getPassword(int userId) {
		return getJdbcTemplate().queryForObject("SELECT password FROM users WHERE id = ?", 
				new Object[] {userId}, String.class);
	}

	public String getUserName(int userId) {
		return getJdbcTemplate().queryForObject("SELECT name FROM users WHERE id = ?", 
				new Object[] {userId}, String.class);
	}
	
	public boolean insertTeam(final Team team) {
	    //Inserting teams
	    Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put("name", team.getName());
        
        final Number tableId = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                        .withTableName("team")
                        .usingGeneratedKeyColumns("id")
                        .executeAndReturnKey(parameters);
        
        team.setId(tableId.intValue());
	    
        //Inserting Users
        getJdbcTemplate().batchUpdate("INSERT INTO users (id, name, password, team_id) VALUES (?,?,?, ?)", 
        		new BatchPreparedStatementSetter() {
            
		            public void setValues(PreparedStatement statement, int i) throws SQLException {
		                User user = team.getUsers().get(i);
		                statement.setInt(1, user.getIdAsInt());
		                statement.setString(2, user.getName());
		                statement.setString(3, user.getPassword());
		                statement.setInt(4, tableId.intValue());
		            }
		            
		            public int getBatchSize() {
		                return team.getUsers().size();
		            }
		        });
        
	    return true;
    }
}
