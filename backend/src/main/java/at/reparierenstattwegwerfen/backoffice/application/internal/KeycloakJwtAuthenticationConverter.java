package at.reparierenstattwegwerfen.backoffice.application.internal;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Fabian Feichter
 */
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	@Override
	public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
		// Extract Keycloak roles from 'realm_access.roles'
		Collection<GrantedAuthority> authorities = extractRealmRoles(jwt);

		// Select the principal username
		String username = jwt.getClaimAsString("preferred_username");
		if (username == null) {
			username = jwt.getSubject();
		}
		if (username == null) {
			throw new IllegalArgumentException("Cannot resolve username: JWT missing both 'preferred_username' and 'sub' claims.");
		}

		// Build standard Spring Security UserDetails (empty string for unused password)
		UserDetails userDetails = User.builder()
			.username(username)
			.password("")
			.authorities(authorities)
			.build();

		// Return Authentication token with UserDetails as principal
		return new UsernamePasswordAuthenticationToken(userDetails, jwt, authorities);
	}

	@SuppressWarnings("unchecked")
	private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
		Map<String, Object> realmAccess = jwt.getClaim("realm_access");
		if (realmAccess == null || !realmAccess.containsKey("roles")) {
			return Collections.emptyList();
		}

		List<String> roles = (List<String>) realmAccess.get("roles");
		return roles.stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.collect(Collectors.toList());
	}
}