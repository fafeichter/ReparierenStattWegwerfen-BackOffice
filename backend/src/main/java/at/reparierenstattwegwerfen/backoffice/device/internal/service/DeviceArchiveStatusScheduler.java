package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Fabian Feichter
 * @since 18.09.2026
 */
@Component
@RequiredArgsConstructor
public class DeviceArchiveStatusScheduler {

	private final DeviceArchivalService deviceArchivalService;

	// Runs daily at 2:00 AM
	@Scheduled(cron = "0 0 2 * * ?")
	public void archiveSoldDevices() {
		deviceArchivalService.archiveSoldDevices();
	}
}