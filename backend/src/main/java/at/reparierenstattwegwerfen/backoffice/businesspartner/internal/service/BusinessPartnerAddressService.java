package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddressCountry;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressCountryRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event.BusinessPartnerAddressChanged;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Fabian Feichter
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

		BusinessPartner businessPartner = businessPartnerRepository.getReferenceById(businessPartnerId);
		BusinessPartnerAddressCountry country = countryRepository.getByCode(address.countryCode());

		businessPartner.setFirstName(address.firstName());
		businessPartner.setLastName(address.lastName());

		BusinessPartnerAddress businessPartnerAddress;
		if (businessPartner.getAddress() == null) {
			businessPartnerAddress = new BusinessPartnerAddress();
		} else {
			businessPartnerAddress = addressRepository.getReferenceById(businessPartner.getAddress().getId());
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