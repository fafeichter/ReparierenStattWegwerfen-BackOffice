package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingInvoiceNumber;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingInvoiceNumberRepository;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper.AccountingInvoiceNumberPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingInvoiceNumberEntity;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.repository.SpringDataAccountingInvoiceNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class AccountingInvoiceNumberRepositoryAdapter implements AccountingInvoiceNumberRepository {

	private final SpringDataAccountingInvoiceNumberRepository jpaRepository;
	private final AccountingInvoiceNumberPersistenceMapper mapper;

	@Override
	public AccountingInvoiceNumber getCurrentNumberInvoiceNumber() {
		return jpaRepository.findAll()
			.stream()
			.findFirst()
			.map(mapper::toDomain)
			.orElseThrow();
	}

	@Override
	public void save(AccountingInvoiceNumber number) {
		AccountingInvoiceNumberEntity entity = mapper.toEntity(number);
		jpaRepository.save(entity);
	}
}