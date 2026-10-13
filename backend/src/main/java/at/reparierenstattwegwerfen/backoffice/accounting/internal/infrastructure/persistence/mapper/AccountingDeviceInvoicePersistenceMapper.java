package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingDeviceInvoice;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingDeviceInvoiceEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface AccountingDeviceInvoicePersistenceMapper {
	AccountingDeviceInvoice toDomain(AccountingDeviceInvoiceEntity entity);

	AccountingDeviceInvoiceEntity toEntity(AccountingDeviceInvoice domain);
}