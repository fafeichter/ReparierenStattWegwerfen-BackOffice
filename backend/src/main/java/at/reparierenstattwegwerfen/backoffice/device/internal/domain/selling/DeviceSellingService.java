package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingDetailsDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceSellingService {

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

	@Transactional
	public void setSellingDetails(Integer deviceId, DeviceSellingDto sellingDto, UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);

		device.setSellingDate(sellingDto.getSellingDate());
		device.setSellingPrice(sellingDto.getSellingPrice());
		deviceRepository.save(device);

		DeviceSellingDateChanged sellingDateChangedEvent = DeviceSellingDateChanged.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.sellingDate(sellingDto.getSellingDate())
			.build();
		DeviceSellingPriceChanged sellingPriceChangedEvent = DeviceSellingPriceChanged.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.sellingPrice(sellingDto.getSellingPrice())
			.build();
		events.publishEvent(sellingDateChangedEvent);
		events.publishEvent(sellingPriceChangedEvent);
	}
}