package at.reparierenstattwegwerfen.backoffice.accounting.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "accounting_device_invoice")
@Getter
@Setter
@NoArgsConstructor
public class AccountingDeviceInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accounting_device_invoice_id")
	private Integer id;

	@Column(name = "number")
	private Integer number;

	@Column(name = "device_id")
	private Integer deviceId;

	@Column(name = "date")
	private LocalDate date;
}