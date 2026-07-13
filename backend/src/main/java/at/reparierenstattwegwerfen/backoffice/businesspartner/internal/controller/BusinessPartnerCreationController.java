package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.BusinessPartnerCreationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/businesspartners")
@RequiredArgsConstructor
public class BusinessPartnerCreationController {

	private final BusinessPartnerCreationService businessPartnerCreationService;

	@PostMapping("/placeholder")
	public Integer createBusinessPartnerPlaceholder(@Valid @RequestBody CreateBusinessPartnerPlaceholder businessPartnerPlaceholder) {
		return businessPartnerCreationService.createBusinessPartnerPlaceholder(businessPartnerPlaceholder);
	}
}