package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void extractAddressFromImage(Integer businessPartnerId, Resource shippingLabelImage) {
		BusinessPartnerAddressExtractResponse address = addressExtractor.extractAddress(shippingLabelImage);

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

		BusinessPartnerAddress savedBusinessPartnerAddress = addressRepository.save(businessPartnerAddress);
		businessPartner.setAddress(savedBusinessPartnerAddress);

		businessPartnerRepository.save(businessPartner);

		BusinessPartnerNameChanged businessPartnerNameChangedEvent = BusinessPartnerNameChanged.builder()
			.source(this)
			.actor(SystemUser.get())
			.businessPartnerId(businessPartnerId)
			.firstName(address.firstName())
			.lastName(address.lastName())
			.build();
		BusinessPartnerAddressChanged businessPartnerAddressChangedEvent = BusinessPartnerAddressChanged.builder()
			.source(this)
			.actor(SystemUser.get())
			.businessPartnerId(businessPartnerId)
			.businessPartnerAddressId(savedBusinessPartnerAddress.getId())
			.build();

		events.publishEvent(businessPartnerNameChangedEvent);
		events.publishEvent(businessPartnerAddressChangedEvent);
	}
}