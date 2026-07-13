package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.CreateBusinessPartnerDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.CreateBusinessPartnerPlaceholder;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressCountryRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerCreationService implements BusinessPartnerService {

	private final BusinessPartnerRepository businessPartnerRepository;
	private final BusinessPartnerAddressRepository businessPartnerAddressRepository;
	private final BusinessPartnerAddressCountryRepository businessPartnerAddressCountryRepository;

	@Transactional
	public Integer createBusinessPartnerPlaceholder(CreateBusinessPartnerPlaceholder businessPartnerPlaceholder) {
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(businessPartnerPlaceholder.getFirstName());
		businessPartner.setLastName(businessPartnerPlaceholder.getLastName());

		return businessPartnerRepository.save(businessPartner).getId();
	}

	@Transactional
	public Integer createBusinessPartner(CreateBusinessPartnerDto businessPartnerDto) {
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

		return businessPartnerRepository.save(businessPartner).getId();
	}
}