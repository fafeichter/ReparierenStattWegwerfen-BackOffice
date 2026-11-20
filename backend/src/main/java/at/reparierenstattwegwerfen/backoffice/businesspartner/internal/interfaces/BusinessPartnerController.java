package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 16.06.2026
 */
@RestController
@RequestMapping("/api/businesspartners")
@RequiredArgsConstructor
public class BusinessPartnerController {

	private final BusinessPartnerService businessPartnerService;

	@GetMapping("/{businessPartnerId}")
	public BusinessPartnerDetailDto getBusinessPartnerDetails(@PathVariable Integer businessPartnerId) {
		return businessPartnerService.getBusinessPartnerDetails(businessPartnerId);
	}

	@PutMapping("/{businessPartnerId}/scammer-flag")
	public void markBusinessPartnerAsScammer(@PathVariable Integer businessPartnerId,
											 @AuthenticationPrincipal UserDetails currentUser) {
		businessPartnerService.markBusinessPartnerAsScammer(businessPartnerId, currentUser);
	}

	@DeleteMapping("/{businessPartnerId}/scammer-flag")
	public void unmarkBusinessPartnerAsScammer(@PathVariable Integer businessPartnerId,
											   @AuthenticationPrincipal UserDetails currentUser) {
		businessPartnerService.unmarkBusinessPartnerAsScammer(businessPartnerId, currentUser);
	}
}