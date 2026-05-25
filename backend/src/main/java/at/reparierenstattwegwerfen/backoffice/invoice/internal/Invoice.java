package at.reparierenstattwegwerfen.backoffice.invoice.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "date")
    private LocalDate date;
}