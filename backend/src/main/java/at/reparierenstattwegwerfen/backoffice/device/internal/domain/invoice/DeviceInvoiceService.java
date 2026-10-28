package at.reparierenstattwegwerfen.backoffice.device.internal.domain.invoice;

import at.reparierenstattwegwerfen.backoffice.accounting.AccountingInvoiceService;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceSold;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Fabian Feichter
 * @since 28.10.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceInvoiceService {

	private final DeviceRepository deviceRepository;
	private final AccountingInvoiceService accountingInvoiceService;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public Integer getDeviceSaleInvoiceNumber(Integer deviceId) {
		return deviceRepository.getById(deviceId).getInvoiceNumber();
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void setDeviceInvoiseNumber(DeviceSold event) {
		Device device = deviceRepository.getById(event.getDeviceId());

		if (device.getInvoiceNumber() == null) {
			Integer nextInvoiceNumber = accountingInvoiceService.requestNextInvoiceNumber();
			device.setInvoiceNumber(nextInvoiceNumber);

			deviceRepository.save(device);

			DeviceInvoiceNumberAssigned deviceInvoiceNumberAssignedEvent = DeviceInvoiceNumberAssigned.builder()
				.source(this)
				.actor(SystemUser.get())
				.deviceId(event.getDeviceId())
				.invoiceNumber(nextInvoiceNumber)
				.build();
			events.publishEvent(deviceInvoiceNumberAssignedEvent);
		}
	}
}