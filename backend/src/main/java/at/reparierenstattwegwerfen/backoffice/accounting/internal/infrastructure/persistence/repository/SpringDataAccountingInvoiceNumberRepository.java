package at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.accounting.internal.infrastructure.persistence.model.AccountingInvoiceNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface SpringDataAccountingInvoiceNumberRepository extends JpaRepository<AccountingInvoiceNumberEntity, Integer> {
}