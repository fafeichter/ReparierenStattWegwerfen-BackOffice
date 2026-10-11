package at.reparierenstattwegwerfen.backoffice.device.internal.domain.buying;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceBuyingDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceBuyingDetailsService {

	private final DeviceRepository deviceRepository;
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