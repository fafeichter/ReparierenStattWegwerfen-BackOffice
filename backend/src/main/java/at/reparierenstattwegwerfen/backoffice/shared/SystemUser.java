package at.reparierenstattwegwerfen.backoffice.shared;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
public class SystemUser {

	private static final UserDetails SYSTEM_USER = new User("SYSTEM", "N/A",
		AuthorityUtils.createAuthorityList("ROLE_SYSTEM"));

	private SystemUser() {
	}

	public static UserDetails get() {
		return SYSTEM_USER;
	}
}