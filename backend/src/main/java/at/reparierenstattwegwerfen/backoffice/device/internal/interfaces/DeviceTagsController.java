package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTagsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 11.10.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/tags")
@RequiredArgsConstructor
public class DeviceTagsController {

	private final DeviceTagsService deviceTagsService;

	@GetMapping("/available")
	public List<NamedIdDto> getAvailableTags(@PathVariable Integer deviceId) {
		return deviceTagsService.getAvailableTagsForDevice(deviceId);
	}

	@PostMapping("/")
	public void addTag(@PathVariable Integer deviceId, @RequestBody Integer newTagId,
					   @AuthenticationPrincipal UserDetails currentUser) {
		deviceTagsService.addTag(deviceId, newTagId, currentUser);
	}

	@DeleteMapping("/")
	public void removeTag(@PathVariable Integer deviceId, @RequestBody Integer tagId,
						  @AuthenticationPrincipal UserDetails currentUser) {
		deviceTagsService.removeTag(deviceId, tagId, currentUser);
	}
}