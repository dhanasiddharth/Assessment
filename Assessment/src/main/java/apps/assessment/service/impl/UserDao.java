package apps.assessment.service.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
