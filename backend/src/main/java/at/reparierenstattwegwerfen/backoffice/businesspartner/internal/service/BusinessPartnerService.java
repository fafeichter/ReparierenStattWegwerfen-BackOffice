package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.BusinessPartnerAddressDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.BusinessPartnerDetailDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.BusinessPartnerDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerService {

	private final BusinessPartnerRepository businessPartnerRepository;

	public List<BusinessPartnerDto> getAllBusinessPartners() {
		return businessPartnerRepository.findAllBusinessPartners()
			.stream()
			.map(businessPartner -> BusinessPartnerDto.builder()
				.id(businessPartner.getId())
				.name(businessPartner.getName())
				.firstName(businessPartner.getFirstName())
				.lastName(businessPartner.getLastName())
				.scammer(businessPartner.getScammer())
				.address(toAddress(businessPartner.getAddress()))
				.build())
			.toList();
	}

	public BusinessPartnerDetailDto getBusinessPartnerDetails(Integer businessPartnerId) {
		BusinessPartner businessPartner = businessPartnerRepository.getBusinessPartnerDetails(businessPartnerId);

		return BusinessPartnerDetailDto.builder()
			.id(businessPartner.getId())
			.name(businessPartner.getName())
			.firstName(businessPartner.getFirstName())
			.lastName(businessPartner.getLastName())
			.telephone(businessPartner.getTelephone())
			.scammer(businessPartner.getScammer())
			.address(toAddress(businessPartner.getAddress()))
			.build();
	}

	private BusinessPartnerAddressDto toAddress(BusinessPartnerAddress address) {
		return address != null ? BusinessPartnerAddressDto
			.builder()
			.id(address.getId())
			.street(address.getStreet())
			.houseNumber(address.getHouseNumber())
			.zipCode(address.getZipCode())
			.city(address.getCity())
			.country(address.getCountry().getName())
			.build() : null;
	}
}
