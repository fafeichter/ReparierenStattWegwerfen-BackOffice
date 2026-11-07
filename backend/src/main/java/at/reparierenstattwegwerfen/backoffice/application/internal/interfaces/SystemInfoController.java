package at.reparierenstattwegwerfen.backoffice.application.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.application.internal.domain.SystemInfoService;
import at.reparierenstattwegwerfen.backoffice.application.internal.interfaces.dtos.SystemInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@RestController
@RequestMapping("/api/system-info")
@RequiredArgsConstructor
public class SystemInfoController {

	private final SystemInfoService systemInfoService;

	@GetMapping("/")
	public SystemInfoDto getSystemInfo() {
		return systemInfoService.getSystemInfo();
	}

	@GetMapping("/is-running-on-prod-db-and-data")
	public Boolean isRunningOnProdDbAndData() {
		return systemInfoService.isRunningOnProdDbAndData();
	}
}