package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes.DeviceNoteService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceNoteDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/notes")
@RequiredArgsConstructor
public class DeviceNotesController {

	private final DeviceNoteService deviceNoteService;

	@GetMapping("/")
	public List<DeviceNoteDto> getDeviceNotes(@PathVariable Integer deviceId) {
		return deviceNoteService.load(deviceId);
	}

	@PostMapping("/")
	public void addDeviceNote(@PathVariable Integer deviceId, @RequestBody String text,
							  @AuthenticationPrincipal UserDetails currentUser) {
		deviceNoteService.addNote(deviceId, text, currentUser);
	}

	@PutMapping("/{deviceNoteId}")
	public void editDeviceNote(@PathVariable Integer deviceId,
							   @PathVariable Integer deviceNoteId,
							   @RequestBody @Valid @NotEmpty String noteNewText,
							   @AuthenticationPrincipal UserDetails currentUser) {
		deviceNoteService.editNote(deviceId, deviceNoteId, noteNewText, currentUser);
	}

	@DeleteMapping("/{deviceNoteId}")
	public void deleteDeviceNote(@PathVariable Integer deviceId,
								 @PathVariable Integer deviceNoteId,
								 @AuthenticationPrincipal UserDetails currentUser) {
		deviceNoteService.deleteNote(deviceId, deviceNoteId, currentUser);
	}
}