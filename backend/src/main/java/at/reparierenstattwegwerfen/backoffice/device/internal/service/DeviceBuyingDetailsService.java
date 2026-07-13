package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBaseDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceBuyingDetailsService {

	private final DeviceBaseDetailsRepository deviceRepository;
	private final BusinessPartnerDetailsService businessPartnerDetailsService;

	@Transactional(readOnly = true)
	public DeviceBuyingDetailsDto load(Integer deviceId) {
		Device device = deviceRepository.getByIdWithRelations(deviceId);

		return DeviceBuyingDetailsDto.builder()
			.url(device.getUrl())
			.price(device.getPurchasePrice())
			.seller(businessPartnerDetailsService.getBusinessPartner(device.getSellerBusinessPartnerId()))
			.date(device.getBuyingDate())
			.build();
	}
}