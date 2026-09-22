package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.CreateBusinessPartnerDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.CreateBusinessPartnerPlaceholderDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressCountryRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event.BusinessPartnerAddressChanged;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event.BusinessPartnerCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 24.06.2026
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerCreationService implements BusinessPartnerService {

	private final BusinessPartnerRepository businessPartnerRepository;
	private final BusinessPartnerAddressRepository businessPartnerAddressRepository;
	private final BusinessPartnerAddressCountryRepository businessPartnerAddressCountryRepository;
	private final ApplicationEventPublisher events;

	@Transactional
	@Override
	public Integer createBusinessPartnerPlaceholder(CreateBusinessPartnerPlaceholderDto businessPartnerPlaceholder, UserDetails actor) {
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(businessPartnerPlaceholder.getFirstName());
		businessPartner.setLastName(businessPartnerPlaceholder.getLastName());

		Integer businessPartnerId = businessPartnerRepository.save(businessPartner).getId();

		BusinessPartnerCreated businessPartnerCreatedEvent = BusinessPartnerCreated.builder()
			.source(this)
			.actor(actor)
			.businessPartnerId(businessPartnerId)
			.build();
		events.publishEvent(businessPartnerCreatedEvent);

		return businessPartnerId;
	}

	@Transactional
	@Override
	public Integer createBusinessPartner(CreateBusinessPartnerDto businessPartnerDto, UserDetails actor) {
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(businessPartnerDto.getFirstName());
		businessPartner.setLastName(businessPartnerDto.getLastName());

		BusinessPartnerAddress businessPartnerAddress = new BusinessPartnerAddress();
		businessPartnerAddress.setStreet(businessPartnerDto.getStreet());
		businessPartnerAddress.setHouseNumber(businessPartnerDto.getHouseNumber());
		businessPartnerAddress.setZipCode(businessPartnerDto.getZipCode());
		businessPartnerAddress.setCity(businessPartnerDto.getCity());
		businessPartnerAddress.setStreet(businessPartnerDto.getStreet());
		businessPartnerAddress.setCountry(businessPartnerAddressCountryRepository.getReferenceById(businessPartnerDto.getCountryId()));

		BusinessPartnerAddress address = businessPartnerAddressRepository.save(businessPartnerAddress);
		businessPartner.setAddress(address);

		Integer businessPartnerId = businessPartnerRepository.save(businessPartner).getId();

		BusinessPartnerCreated businessPartnerCreatedEvent = BusinessPartnerCreated.builder()
			.source(this)
			.actor(actor)
			.businessPartnerId(businessPartnerId)
			.build();
		BusinessPartnerAddressChanged businessPartnerAddressChangedEvent = BusinessPartnerAddressChanged.builder()
			.source(this)
			.actor(actor)
			.businessPartnerId(businessPartnerId)
			.businessPartnerAddressId(address.getId())
			.build();

		events.publishEvent(businessPartnerCreatedEvent);
		events.publishEvent(businessPartnerAddressChangedEvent);

		return businessPartnerId;
	}
}