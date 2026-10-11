package at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceActivityType implements NamedId {

	private Integer id;
	private String name;
}