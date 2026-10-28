package at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountingInvoice {

	private Integer id;
	private Integer number;
	private LocalDate date;
}