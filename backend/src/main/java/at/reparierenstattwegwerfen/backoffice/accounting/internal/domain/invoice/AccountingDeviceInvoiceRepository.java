package at.reparierenstattwegwerfen.backoffice.accounting.internal.domain.invoice;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface AccountingDeviceInvoiceRepository {
	AccountingDeviceInvoice getReferenceById(Integer id);
}