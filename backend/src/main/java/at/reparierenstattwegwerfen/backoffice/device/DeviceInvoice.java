package at.reparierenstattwegwerfen.backoffice.device;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_invoice")
@Getter
@Setter
@NoArgsConstructor
public class DeviceInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_invoice_id")
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "date")
    private LocalDate date;
}