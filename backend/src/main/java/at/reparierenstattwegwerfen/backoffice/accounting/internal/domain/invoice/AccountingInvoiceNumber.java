package at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Getter
@Setter
@AllArgsConstructor
public class AccountingInvoiceNumber {

	private Integer currentInvoiceNumber;

	public Integer getNextInvoiceNumber() {
		return currentInvoiceNumber + 1;
	}
}