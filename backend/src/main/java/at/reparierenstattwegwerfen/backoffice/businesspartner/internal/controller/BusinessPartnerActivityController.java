package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.BusinessPartnerActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 16.06.2026
 */
@RestController
@RequestMapping("/api/businesspartners/{businessPartnerId}/activities")
@RequiredArgsConstructor
public class BusinessPartnerActivityController {

	private final BusinessPartnerActivityService deviceActivityService;

	@GetMapping("/")
	public List<BusinessPartnerActivityDto> getActivities(@PathVariable Integer businessPartnerId) {
		return deviceActivityService.getActivitiesForBusinessPartner(businessPartnerId);
	}
}