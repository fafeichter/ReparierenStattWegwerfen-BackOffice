package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.BusinessPartnerCreationService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.BusinessPartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/businesspartners")
@RequiredArgsConstructor
public class BusinessPartnerCreationController {

	private final BusinessPartnerCreationService businessPartnerCreationService;

	@PostMapping("/")
	public Integer createBusinessPartnerPlaceholder(@Valid @RequestBody CreateBusinessPartnerPlaceholder businessPartnerPlaceholder) {
		return businessPartnerCreationService.createBusinessPartnerPlaceholder(businessPartnerPlaceholder);
	}
}