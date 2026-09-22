package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 06.07.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/base")
@RequiredArgsConstructor
public class DeviceBaseController {

	private final DeviceBaseDetailsService baseDetailsService;
	private final DeviceStatusService deviceStatusService;

	@GetMapping("/")
	public DeviceBaseDetailsDto getDeviceBaseDetails(@PathVariable Integer deviceId) {
		return baseDetailsService.load(deviceId);
	}

	@PostMapping("/hardware-config")
	public void updateHardwareConfig(@PathVariable Integer deviceId,
									 @Valid @RequestBody UpdateHardwareConfigDto updateHardwareConfigDto,
									 @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateHardwareConfigOfDevice(deviceId, updateHardwareConfigDto, currentUser);
	}

	@PostMapping("/status")
	public void updateStatus(@PathVariable Integer deviceId, @RequestBody Integer newStatusId,
							 @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateStatusOfDevice(deviceId, newStatusId, currentUser);
	}

	@PostMapping("/serial-number")
	public void updateSerialNumber(@PathVariable Integer deviceId, @RequestBody(required = false) String newSerialNumber,
								   @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateSerialNumber(deviceId, newSerialNumber, currentUser);
	}

	@PostMapping("/battery")
	public void updateBattery(@PathVariable Integer deviceId, @Valid @RequestBody BatteryHealthDto newDeviceBaseBattery,
							  @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateBattery(deviceId, newDeviceBaseBattery, currentUser);
	}

	@PostMapping("/battery-status")
	public void updateBatteryStatus(@PathVariable Integer deviceId, @RequestBody Integer newBatteryStatusId,
									@AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateBatteryStatus(deviceId, newBatteryStatusId, currentUser);
	}

	@PostMapping("/grade")
	public void updateGrade(@PathVariable Integer deviceId, @RequestBody Integer newGradeId,
							@AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateGrade(deviceId, newGradeId, currentUser);
	}

	@PostMapping("/available-tags")
	public List<NamedIdDto> getAvailableTags(@PathVariable Integer deviceId) {
		return deviceStatusService.getAvailableTagsForDevice(deviceId);
	}

	@PostMapping("/tags")
	public void addTag(@PathVariable Integer deviceId, @RequestBody Integer newTagId,
					   @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.addTag(deviceId, newTagId, currentUser);
	}

	@DeleteMapping("/tags")
	public void removeTag(@PathVariable Integer deviceId, @RequestBody Integer tagId,
						  @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.removeTag(deviceId, tagId, currentUser);
	}
}