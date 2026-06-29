package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}