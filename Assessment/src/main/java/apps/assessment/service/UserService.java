package apps.assessment.service;

public interface UserService {
	boolean authenticate(String userId, String password);

	boolean insertUser(String userId, String name);

	/**
	 * Doesn't validate whether a valid user is present.
	 * @return
	 */
	String getUserName();
}
