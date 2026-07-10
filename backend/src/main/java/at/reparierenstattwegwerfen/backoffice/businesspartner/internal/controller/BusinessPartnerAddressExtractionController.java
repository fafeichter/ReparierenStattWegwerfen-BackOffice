package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.BusinessPartnerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/businesspartners/{businessPartnerId}")
@RequiredArgsConstructor
public class BusinessPartnerAddressExtractionController {

	private final BusinessPartnerAddressService businessPartnerAddressService;

	@PostMapping(value = "/address", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void extractAddressFromImage(@PathVariable Integer businessPartnerId, @RequestPart("shippingLabelImage") MultipartFile shippingLabelImage) {
		businessPartnerAddressService.extractAddressFromImage(businessPartnerId, shippingLabelImage);
	}
}