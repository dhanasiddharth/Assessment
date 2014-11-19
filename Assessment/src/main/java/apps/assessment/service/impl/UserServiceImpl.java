package apps.assessment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import apps.assessment.Constants;
import apps.assessment.entity.Team;
import apps.assessment.entity.User;
import apps.assessment.service.UserService;

@Service("userService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	public boolean authenticate(String userId, String password) {
		Session session = Sessions.getCurrent();
		User user;
		
		if("admin".equals(userId) && "1234".equals(password)) {
			user = new User(userId, "Administrator");
			user.addRole(Constants.ROLE_ADMIN);
			session.setAttribute(Constants.USER, user);
			return true;
		} else if(isInteger(userId) 
				&& password.equals(userDao.getPassword(Integer.parseInt(userId)))) {
			user = new User(userId, userDao.getUserName(Integer.parseInt(userId)));
			user.addRole(Constants.ROLE_PARTICIPANT);
			session.setAttribute(Constants.USER, user);
			return true;
		}
		return false;
	}

	public String getUserName() {
		if(Sessions.getCurrent().getAttribute(Constants.USER) != null) {
			return ((User)Sessions.getCurrent().getAttribute(Constants.USER)).getName();
		}
		return "";
	}
	
	public boolean isAuthenticated() {
		if(Sessions.getCurrent().getAttribute(Constants.USER) != null) {
			return true;
		}
		return false;
	}
	
	private boolean isInteger(String userId) {
		try {
			Integer.parseInt(userId);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

    public boolean insertTeam(Team team) {
        // TODO Auto-generated method stub
        return false;
    }

}
