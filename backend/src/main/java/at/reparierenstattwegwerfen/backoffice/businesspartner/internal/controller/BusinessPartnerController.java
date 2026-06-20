package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.BusinessPartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/businesspartners")
@RequiredArgsConstructor
public class BusinessPartnerController {

	private final BusinessPartnerService businessPartnerService;

	@GetMapping("/")
	public List<BusinessPartnerDto> getAllBusinessPartners() {
		return businessPartnerService.getAllBusinessPartners();
	}

	@GetMapping("/{businessPartnerId}")
	public BusinessPartnerDetailDto getBusinessPartnerDetails(@PathVariable Integer businessPartnerId) {
		return businessPartnerService.getBusinessPartnerDetails(businessPartnerId);
	}
}