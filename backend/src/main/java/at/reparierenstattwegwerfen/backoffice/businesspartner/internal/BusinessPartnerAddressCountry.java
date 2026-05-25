package at.reparierenstattwegwerfen.backoffice.businesspartner.internal;

import at.reparierenstattwegwerfen.backoffice.shared.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "business_partner_address_country")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerAddressCountry implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_partner_address_country_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;
}