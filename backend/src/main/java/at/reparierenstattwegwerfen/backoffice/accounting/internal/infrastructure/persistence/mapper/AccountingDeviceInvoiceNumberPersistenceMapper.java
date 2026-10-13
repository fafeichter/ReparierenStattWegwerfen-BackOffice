package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice.AccountingDeviceInvoiceNumber;
import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingDeviceInvoiceNumberEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface AccountingDeviceInvoiceNumberPersistenceMapper {
	AccountingDeviceInvoiceNumber toDomain(AccountingDeviceInvoiceNumberEntity entity);

	AccountingDeviceInvoiceNumberEntity toEntity(AccountingDeviceInvoiceNumber domain);
}