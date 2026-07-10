package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceBusinessPartnerService {

	private final DeviceRepository deviceRepository;

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
}