package at.reparierenstattwegwerfen.backoffice.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 21.09.2026
 */
@Getter
public class PreciseTimestampedApplicationEvent extends ApplicationEvent {

	private final LocalDateTime exactTimestamp; // Spring's default getTimestamp() is millis only

	public PreciseTimestampedApplicationEvent(Object source) {
		super(source);
		this.exactTimestamp = LocalDateTime.now();
	}
}