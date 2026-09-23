package at.reparierenstattwegwerfen.backoffice.accounting.internal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "accounting_device_invoice_number")
@Getter
@Setter
@NoArgsConstructor
public class AccountingDeviceInvoiceNumber {

	@Id
	@Column(name = "current_device_invoice_number")
	private Integer currentDeviceInvoiceNumber;
}