package at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DeviceSpareParts {

	private Integer id;
	private Integer deviceId;
	private Integer sparePartId;
	private Double priceNetto;
	private LocalDateTime date;
}