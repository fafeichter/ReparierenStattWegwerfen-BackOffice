package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerDetailsService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerDetailsServiceImpl implements BusinessPartnerDetailsService {

	private final BusinessPartnerRepository businessPartnerRepository;

	@Override
	public NamedIdDto getBusinessPartner(Integer businessPartnerId) {
		return NamedIdDto.from(businessPartnerRepository.getBusinessPartner(businessPartnerId));
	}
}