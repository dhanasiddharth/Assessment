package apps.assessment;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import apps.assessment.entity.User;

public class Authnticator implements Initiator {

	public void doInit(Page page, Map<String, Object> arg) throws Exception {
		User user = (User) Sessions.getCurrent().getAttribute(Constants.USER);
		
		if(user == null) {
			Executions.sendRedirect("/login.zhtml");
			return;
		}
	}

}
