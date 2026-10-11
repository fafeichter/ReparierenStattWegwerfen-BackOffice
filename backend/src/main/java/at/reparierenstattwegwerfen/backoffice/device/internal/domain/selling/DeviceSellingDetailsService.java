package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatusChanged;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingDetailsDto;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.util.Objects;

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
	public void setDeviceSellingDate(DeviceStatusChanged event) {
		Device device = deviceRepository.getReferenceById(event.getDeviceId());

		Integer newStatusId = event.getNewStatusId();
		if (!Objects.equals(newStatusId, device.getStatus().getId()) && newStatusId == 6) {
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
}