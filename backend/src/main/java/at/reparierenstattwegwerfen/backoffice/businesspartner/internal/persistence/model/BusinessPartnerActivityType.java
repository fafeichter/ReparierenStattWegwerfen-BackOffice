package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "business_partner_activity_type")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerActivityType implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_partner_activity_type_id")
	private Integer id;

	@Column(name = "name")
	private String name;
}