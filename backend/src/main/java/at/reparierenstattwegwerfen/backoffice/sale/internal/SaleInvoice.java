package at.reparierenstattwegwerfen.backoffice.sale.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "sale_invoice")
@Getter
@Setter
@NoArgsConstructor
public class SaleInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sale_id")
	private Integer id;

	@Column(name = "number")
	private Integer number;

	@Column(name = "device_id")
	private Integer deviceId;

	@Column(name = "date")
	private LocalDate date;
}