package apps.assessment.vm;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import apps.assessment.Constants;
import apps.assessment.service.UserService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Login {
	private String user;
	private String password;
	private String message;
	
	@WireVariable
	private UserService userService; 
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Command
	public void updateTimeZone(@ContextParam(ContextType.TRIGGER_EVENT) ClientInfoEvent event) {
		Sessions.getCurrent().setAttribute(Attributes.PREFERRED_TIME_ZONE, event.getTimeZone());
	}
	
	@Command
	@NotifyChange("message")
	public void login() {
		if(userService.authenticate(getUser(), getPassword())) {
			setMessage("Welcome " + userService.getUserName());
			if(userService.getAuthenticatedUser().hasRole(Constants.ROLE_ADMIN)) {
			    Executions.sendRedirect("/manage.zul");
			} else if(userService.getAuthenticatedUser().hasRole(Constants.ROLE_PARTICIPANT)) {
			    Executions.sendRedirect("/assessment.zul");
			} else {
			    Executions.sendRedirect("/");
			}
		} else {
			setMessage("Authentication failed");
		}
	}
}
