package at.reparierenstattwegwerfen.backoffice.sale.internal;

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
@Table(name = "sale_number")
@Getter
@Setter
@NoArgsConstructor
public class SaleInvoiceNumber {

	@Id
	@Column(name = "current_sale_number")
	private Integer currentInvoiceNumber;
}