package apps.assessment.service;

import apps.assessment.entity.Team;
import apps.assessment.entity.User;

public interface UserService {
	boolean authenticate(String userId, String password);

	boolean insertTeam(Team team);

	/**
	 * Doesn't validate whether a valid user is present.
	 * @return
	 */
	String getUserName();
	
	User getAuthenticatedUser();
}
