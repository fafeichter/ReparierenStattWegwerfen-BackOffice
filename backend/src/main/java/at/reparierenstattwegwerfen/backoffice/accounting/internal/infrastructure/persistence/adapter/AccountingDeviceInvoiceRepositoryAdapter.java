package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingDeviceInvoice;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingDeviceInvoiceRepository;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper.AccountingDeviceInvoicePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.repository.SpringDataAccountingDeviceInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class AccountingDeviceInvoiceRepositoryAdapter implements AccountingDeviceInvoiceRepository {

	private final SpringDataAccountingDeviceInvoiceRepository jpaRepository;
	private final AccountingDeviceInvoicePersistenceMapper mapper;

	@Override
	public AccountingDeviceInvoice getReferenceById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}
}