package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceNoteDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/{deviceId}")
@RequiredArgsConstructor
public class DeviceNotesController {

	private final DeviceNoteService deviceNoteService;

	@GetMapping("/notes")
	public List<DeviceNoteDto> getDeviceNotes(@PathVariable Integer deviceId) {
		return deviceNoteService.load(deviceId);
	}

	@PostMapping("/notes")
	public void addDeviceNote(@PathVariable Integer deviceId, @RequestBody String text) {
		deviceNoteService.add(deviceId, text);
	}
}