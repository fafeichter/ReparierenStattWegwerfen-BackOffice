package at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice;

import at.reparierenstattwegwerfen.backoffice.accounting.AccountingInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 28.10.2026
 */
@Service
@RequiredArgsConstructor
public class AccountingInvoiceServiceImpl implements AccountingInvoiceService {

	private final AccountingInvoiceNumberRepository accountingInvoiceNumberRepository;

	@Transactional
	@Override
	public synchronized Integer requestNextInvoiceNumber() {
		AccountingInvoiceNumber invoiceNumber = accountingInvoiceNumberRepository.getCurrentNumberInvoiceNumber();
		AccountingInvoiceNumber nextInvoiceNumber = new AccountingInvoiceNumber(invoiceNumber.getNextInvoiceNumber());
		accountingInvoiceNumberRepository.save(nextInvoiceNumber);
		return nextInvoiceNumber.getCurrentInvoiceNumber();
	}
}