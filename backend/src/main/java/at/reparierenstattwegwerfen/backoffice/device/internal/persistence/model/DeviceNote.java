package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

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
public class DeviceNote {

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
}