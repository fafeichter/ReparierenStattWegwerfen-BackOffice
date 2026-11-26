package at.reparierenstattwegwerfen.backoffice.application.internal.infrastructure;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Fabian Feichter
 * @since 26.11.2026
 */
@Configuration
@RegisterReflectionForBinding(classes = {SimpleGrantedAuthority.class, User.class})
class NativeHintsConfig {
}