package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingDeviceInvoiceNumber;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingDeviceInvoiceNumberRepository;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper.AccountingDeviceInvoiceNumberPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingDeviceInvoiceNumberEntity;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.repository.SpringDataAccountingDeviceInvoiceNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class AccountingDeviceInvoiceNumberRepositoryAdapter implements AccountingDeviceInvoiceNumberRepository {

	private final SpringDataAccountingDeviceInvoiceNumberRepository jpaRepository;
	private final AccountingDeviceInvoiceNumberPersistenceMapper mapper;

	@Override
	public AccountingDeviceInvoiceNumber getCurrentNumberInvoiceNumber() {
		return jpaRepository.findAll()
			.stream()
			.findFirst()
			.map(mapper::toDomain)
			.orElseThrow();
	}

	@Override
	public void save(AccountingDeviceInvoiceNumber number) {
		AccountingDeviceInvoiceNumberEntity entity = mapper.toEntity(number);
		jpaRepository.save(entity);
	}
}