package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivityType;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerActivityTypeEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface BusinessPartnerActivityTypePersistenceMapper {
	BusinessPartnerActivityType toDomain(BusinessPartnerActivityTypeEntity entity);

	BusinessPartnerActivityTypeEntity toEntity(BusinessPartnerActivityType domain);
}