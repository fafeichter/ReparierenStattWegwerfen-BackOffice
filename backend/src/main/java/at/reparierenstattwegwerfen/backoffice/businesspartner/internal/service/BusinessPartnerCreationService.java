package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.CreateBusinessPartnerPlaceholder;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
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

	@Transactional
	public Integer createBusinessPartnerPlaceholder(CreateBusinessPartnerPlaceholder businessPartnerPlaceholder) {
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(businessPartnerPlaceholder.getFirstName());
		businessPartner.setLastName(businessPartnerPlaceholder.getLastName());
		businessPartner.setScammer(false);

		return businessPartnerRepository.save(businessPartner).getId();
	}
}