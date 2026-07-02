package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.ApplicationEvent;

/**
 * @author Fabian Feichter
 */
@Getter
@RegisterReflectionForBinding(DeviceCreated.class)
public class DeviceCreated extends ApplicationEvent {

	final Integer deviceId;

	@Builder
	public DeviceCreated(Object source, Integer deviceId) {
		super(source);
		this.deviceId = deviceId;
	}
}
