package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.CreateBusinessPartnerPlaceholder;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.CreateBuyerBusinessPartnerForDeviceDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressCountryRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.device.DeviceBuyingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerCreationService {

	private final BusinessPartnerRepository businessPartnerRepository;
	private final BusinessPartnerAddressRepository businessPartnerAddressRepository;
	private final BusinessPartnerAddressCountryRepository businessPartnerAddressCountryRepository;
	private final DeviceBuyingService deviceBuyingService;

	@Transactional
	public Integer createBusinessPartnerPlaceholder(CreateBusinessPartnerPlaceholder businessPartnerPlaceholder) {
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(businessPartnerPlaceholder.getFirstName());
		businessPartner.setLastName(businessPartnerPlaceholder.getLastName());

		return businessPartnerRepository.save(businessPartner).getId();
	}

	@Transactional
	public void createBuyerBusinessPartnerForDevice(@Valid CreateBuyerBusinessPartnerForDeviceDto buyerBusinessPartnerForDevice) {
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(buyerBusinessPartnerForDevice.getFirstName());
		businessPartner.setLastName(buyerBusinessPartnerForDevice.getLastName());

		BusinessPartnerAddress businessPartnerAddress = new BusinessPartnerAddress();
		businessPartnerAddress.setStreet(buyerBusinessPartnerForDevice.getStreet());
		businessPartnerAddress.setHouseNumber(buyerBusinessPartnerForDevice.getHouseNumber());
		businessPartnerAddress.setZipCode(buyerBusinessPartnerForDevice.getZipCode());
		businessPartnerAddress.setCity(buyerBusinessPartnerForDevice.getCity());
		businessPartnerAddress.setStreet(buyerBusinessPartnerForDevice.getStreet());
		businessPartnerAddress.setCountry(businessPartnerAddressCountryRepository.getReferenceById(buyerBusinessPartnerForDevice.getCountryId()));

		BusinessPartnerAddress address = businessPartnerAddressRepository.save(businessPartnerAddress);
		businessPartner.setAddress(address);

		BusinessPartner savedBusinessPartner = businessPartnerRepository.save(businessPartner);

		deviceBuyingService.setBuyerAddressForDevice(buyerBusinessPartnerForDevice.getDeviceId(), savedBusinessPartner.getId());
	}
}