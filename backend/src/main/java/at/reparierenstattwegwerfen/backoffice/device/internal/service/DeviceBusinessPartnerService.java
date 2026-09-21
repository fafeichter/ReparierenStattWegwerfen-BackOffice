package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.CreateBusinessPartnerDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.event.DeviceBusinessPartnerSetAsBuyer;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceBusinessPartnerService {

	private final DeviceRepository deviceRepository;
	private final BusinessPartnerService businessPartnerService;
	private final ApplicationEventPublisher events;

	public DeviceBusinesspartnerDto getDevicesOfBusinessPartner(Integer businessPartnerId) {
		List<NamedIdDto> soldDevices = deviceRepository.findBySellerBusinessPartnerId(businessPartnerId).stream()
			.map(NamedIdDto::from)
			.toList();
		List<NamedIdDto> boughtDevices = deviceRepository.findByBuyerBusinessPartnerId(businessPartnerId).stream()
			.map(NamedIdDto::from)
			.toList();

		return DeviceBusinesspartnerDto.builder()
			.soldDevices(soldDevices)
			.boughtDevices(boughtDevices)
			.build();
	}

	@Transactional
	public void createBuyerBusinessPartnerForDevice(CreateBuyerBusinessPartnerForDeviceDto buyerBusinessPartnerForDevice,
													UserDetails actor) {
		CreateBusinessPartnerDto createBusinessPartnerDto = new CreateBusinessPartnerDto();
		BeanUtils.copyProperties(buyerBusinessPartnerForDevice, createBusinessPartnerDto);

		Integer buyerBusinessPartnerId = businessPartnerService.createBusinessPartner(createBusinessPartnerDto, actor);

		Integer deviceId = buyerBusinessPartnerForDevice.getDeviceId();
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setBuyerBusinessPartnerId(buyerBusinessPartnerId);
		deviceRepository.save(device);

		DeviceBusinessPartnerSetAsBuyer businessPartnerSetAsBuyerEvent = DeviceBusinessPartnerSetAsBuyer.builder()
			.source(this)
			.actor(actor)
			.buyerBusinessPartnerId(buyerBusinessPartnerId)
			.deviceId(deviceId)
			.build();
		events.publishEvent(businessPartnerSetAsBuyerEvent);
	}
}