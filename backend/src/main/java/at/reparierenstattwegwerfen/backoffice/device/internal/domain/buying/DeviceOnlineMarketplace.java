package at.reparierenstattwegwerfen.backoffice.device.internal.domain.buying;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceOnlineMarketplace implements NamedId {

	private Integer id;
	private String name;
	private String url;
}