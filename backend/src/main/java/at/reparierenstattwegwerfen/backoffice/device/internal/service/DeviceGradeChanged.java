package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceGradeChanged extends ApplicationEvent {

	final Integer deviceId;
	final Integer oldGradeId;
	final Integer newGradeId;

	@Builder
	public DeviceGradeChanged(Object source, Integer deviceId, Integer oldGradeId, Integer newGradeId) {
		super(source);
		this.deviceId = deviceId;
		this.oldGradeId = oldGradeId;
		this.newGradeId = newGradeId;
	}
}