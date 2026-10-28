package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface SpringDataAccountingInvoiceRepository extends JpaRepository<AccountingInvoiceEntity, Integer> {
}