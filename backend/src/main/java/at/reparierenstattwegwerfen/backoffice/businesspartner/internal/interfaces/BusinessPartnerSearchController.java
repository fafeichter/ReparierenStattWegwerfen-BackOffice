package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerSearchService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author Fabian Feichter
 * @since 20.11.2026
 */
@RestController
@RequestMapping("/api/businesspartners")
@RequiredArgsConstructor
public class BusinessPartnerSearchController {

	private final BusinessPartnerSearchService businessPartnerSearchService;

	@GetMapping("/")
	public Page<BusinessPartnerDto> search(@PageableDefault(direction = DESC) Pageable page) {
		return businessPartnerSearchService.search(page);
	}
}