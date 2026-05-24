package at.reparierenstattwegwerfen.backoffice.businesspartner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "business_partner")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_partner_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "scammer")
    private Boolean scammer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_partner_address_id")
    private BusinessPartnerAddress address;
}