package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddressCountry;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressCountryRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
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

	@Transactional
	public void extractAddressFromImage(Integer businessPartnerId, MultipartFile shippingLabelImage) {
		BusinessPartnerAddressExtractResponse address = addressExtractor.extractAddress(shippingLabelImage.getResource());

		BusinessPartner businessPartner = businessPartnerRepository.getReferenceById(businessPartnerId);
		BusinessPartnerAddressCountry country = countryRepository.getByCode(address.countryCode());

		businessPartner.setFirstName(address.firstName());
		businessPartner.setLastName(address.lastName());

		BusinessPartnerAddress businessPartnerAddress = null;
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

		addressRepository.save(businessPartnerAddress);
		businessPartner.setAddress(businessPartnerAddress);

		businessPartnerRepository.save(businessPartner);
	}
}