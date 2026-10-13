package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddressCountryService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerAddressCountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.07.2026
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