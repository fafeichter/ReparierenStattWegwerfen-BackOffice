package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_note")
@Getter
@Setter
@NoArgsConstructor
public class DeviceNote implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_note_id")
	private Integer id;

	@Column(name = "device_id")
	private Integer deviceId;

	@Column(name = "text")
	private String text;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public String getName() {
		return text;
	}
}