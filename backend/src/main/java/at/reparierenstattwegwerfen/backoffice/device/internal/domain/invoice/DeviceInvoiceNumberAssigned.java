package at.reparierenstattwegwerfen.backoffice.device.internal.domain.invoice;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 28.10.2026
 */
@Getter
public class DeviceInvoiceNumberAssigned extends AbstractDeviceActivityEvent {

	final Integer invoiceNumber;

	@Builder
	public DeviceInvoiceNumberAssigned(Object source, UserDetails actor, Integer deviceId, Integer invoiceNumber) {
		super(source, deviceId, actor);
		this.invoiceNumber = invoiceNumber;
	}
}