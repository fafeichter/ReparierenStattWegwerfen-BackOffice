package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingInvoiceNumber;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingInvoiceNumberEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface AccountingInvoiceNumberPersistenceMapper {
	AccountingInvoiceNumber toDomain(AccountingInvoiceNumberEntity entity);

	AccountingInvoiceNumberEntity toEntity(AccountingInvoiceNumber domain);
}