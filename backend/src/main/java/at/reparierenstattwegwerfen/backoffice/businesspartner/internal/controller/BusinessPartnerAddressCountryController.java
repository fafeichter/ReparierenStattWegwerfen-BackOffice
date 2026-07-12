package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.BusinessPartnerAddressCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/businesspartners/countries")
@RequiredArgsConstructor
public class BusinessPartnerAddressCountryController {

	private final BusinessPartnerAddressCountryService addressCountryService;

	@GetMapping("/")
	public List<BusinessPartnerAddressCountryDto> getAllCountries() {
		return addressCountryService.getAllCountries();
	}
}