package apps.assessment.vm;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import apps.assessment.entity.Team;
import apps.assessment.entity.User;
import apps.assessment.service.UserService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserManager {
	private String teamName;
	private String userName;
	private String userId;
	private String password;
	
	private List<User> users;
	
	@WireVariable
	private UserService userService;
	
	@NotifyChange({"users", "userId", "userName"})
	@Command
	public void addUser() {
		System.out.println("add user");
		if(users == null) {
			users = new ArrayList<User>();
		}
		User user = new User(userId, userName);
		user.setPassword(getPassword());
		users.add(user);
		setUserId(null);
		setUserName(null);
		setPassword(null);
	}
	
	@NotifyChange({"users", "teamName"})
	@Command
	public void saveTeam() {
		Team team = new Team();
		team.setName(teamName);
		team.setUsers(getUsers());
		
		userService.insertTeam(team);
		setUsers(null);
		setTeamName(null);
	}
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
