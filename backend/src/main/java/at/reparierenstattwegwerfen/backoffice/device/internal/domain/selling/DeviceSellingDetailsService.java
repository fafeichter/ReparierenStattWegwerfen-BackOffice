package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceSold;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingDetailsDto;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceSellingDetailsService {

	private final DeviceRepository deviceRepository;
	private final BusinessPartnerDetailsService businessPartnerDetailsService;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public DeviceSellingDetailsDto load(Integer deviceId) {
		Device device = deviceRepository.getByIdWithRelations(deviceId);

		return DeviceSellingDetailsDto.builder()
			.price(device.getSellingPrice())
			.buyer(businessPartnerDetailsService.getBusinessPartner(device.getBuyerBusinessPartnerId()))
			.date(device.getSellingDate())
			.build();
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void setDeviceSellingDate(DeviceSold event) {
		Device device = deviceRepository.getById(event.getDeviceId());

		LocalDate sellingDate = LocalDate.now();
		device.setSellingDate(sellingDate);
		deviceRepository.save(device);

		DeviceSellingDateChanged sellingDateChangedEvent = DeviceSellingDateChanged.builder()
			.source(this)
			.actor(SystemUser.get())
			.deviceId(event.getDeviceId())
			.sellingDate(sellingDate)
			.build();
		events.publishEvent(sellingDateChangedEvent);
	}
}