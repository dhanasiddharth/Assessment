package apps.assessment.entity;

import java.util.HashSet;
import java.util.Set;

public class User {
	private String id;
	private String name;
	private String password;
	private Set<String> roles;
	
	public User(String userId, String name) {
		setId(userId);
		setName(name);
	}

	public boolean hasRole(String role) {
		if(roles != null) {
			return roles.contains(role);
		}
		return false;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getIdAsInt() {
        return Integer.parseInt(id);
    }

	public void addRole(String role) {
		if(roles == null) {
			roles = new HashSet<String>();
		}
		roles.add(role);
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
