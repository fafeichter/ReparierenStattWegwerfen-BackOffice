package at.reparierenstattwegwerfen.backoffice.device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "invoice_number_counter")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceNumberCounter {

    @Id
    @Column(name = "current_invoice_number")
    private Integer currentInvoiceNumber;
}