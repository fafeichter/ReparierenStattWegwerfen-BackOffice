package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingInvoice;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingInvoiceRepository;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper.AccountingInvoicePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.repository.SpringDataAccountingInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class AccountingInvoiceRepositoryAdapter implements AccountingInvoiceRepository {

	private final SpringDataAccountingInvoiceRepository jpaRepository;
	private final AccountingInvoicePersistenceMapper mapper;

	@Override
	public AccountingInvoice getById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}
}