package at.reparierenstattwegwerfen.backoffice.device;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.model.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apple_silicon_id")
    private AppleSilicon appleSilicon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apple_silicon_unified_memory_id")
    private AppleSiliconUnifiedMemory unifiedMemory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_storage_id")
    private ModelStorage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_color_id")
    private ModelColor color;

    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_status_id")
    private DeviceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_business_partner_id")
    private BusinessPartner seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_business_partner_id")
    private BusinessPartner buyer;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_grade_id")
    private DeviceGrade grade;

    @Column(name = "reported_defect")
    private String reportedDefect;

    @Column(name = "diagnosed_defect")
    private String diagnosedDefect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_online_marketplace_id")
    private OnlineMarketplace purchaseOnlineMarketplace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selling_online_marketplace")
    private OnlineMarketplace sellingOnlineMarketplace;

    @Column(name = "selling_date")
    private LocalDate sellingDate;

    @Column(name = "battery_maximum_capacity")
    private Byte batteryMaximumCapacity;

    @Column(name = "battery_cycle_count")
    private Integer batteryCycleCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_battery_status_id")
    private DeviceBatteryStatus batteryStatus;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_invoice_id")
    private DeviceInvoice invoice;
}
