package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Fabian Feichter
 * @since 10.07.2026
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerAddressService {

	private final BusinessPartnerRepository businessPartnerRepository;
	private final BusinessPartnerAddressRepository addressRepository;
	private final BusinessPartnerAddressCountryRepository countryRepository;
	private final BusinessPartnerAddressExtractor addressExtractor;
	private final ApplicationEventPublisher events;

	@Transactional
	public void extractAddressFromImage(Integer businessPartnerId, MultipartFile shippingLabelImage, UserDetails actor) {
		BusinessPartnerAddressExtractResponse address = addressExtractor.extractAddress(shippingLabelImage.getResource());

		BusinessPartner businessPartner = businessPartnerRepository.getById(businessPartnerId);
		BusinessPartnerAddressCountry country = countryRepository.getByCode(address.countryCode());

		businessPartner.setFirstName(address.firstName());
		businessPartner.setLastName(address.lastName());

		BusinessPartnerAddress businessPartnerAddress;
		if (businessPartner.getAddress() == null) {
			businessPartnerAddress = new BusinessPartnerAddress();
		} else {
			businessPartnerAddress = addressRepository.getById(businessPartner.getAddress().getId());
		}

		businessPartnerAddress.setStreet(address.street());
		businessPartnerAddress.setHouseNumber(address.houseNumber());
		businessPartnerAddress.setCity(address.city());
		businessPartnerAddress.setZipCode(address.zipCode());
		businessPartnerAddress.setCountry(country);

		Integer businessPartnerAddressId = addressRepository.save(businessPartnerAddress).getId();
		businessPartner.setAddress(businessPartnerAddress);

		businessPartnerRepository.save(businessPartner);

		BusinessPartnerAddressChanged businessPartnerAddressChangedEvent = BusinessPartnerAddressChanged.builder()
			.source(this)
			.actor(actor)
			.businessPartnerId(businessPartnerId)
			.businessPartnerAddressId(businessPartnerAddressId)
			.build();
		events.publishEvent(businessPartnerAddressChangedEvent);
	}
}