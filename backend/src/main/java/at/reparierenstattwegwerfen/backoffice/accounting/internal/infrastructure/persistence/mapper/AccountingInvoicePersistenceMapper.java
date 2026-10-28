package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingInvoice;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingInvoiceEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface AccountingInvoicePersistenceMapper {
	AccountingInvoice toDomain(AccountingInvoiceEntity entity);

	AccountingInvoiceEntity toEntity(AccountingInvoice domain);
}