package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface BusinessPartnerPersistenceMapper {
	BusinessPartner toDomain(BusinessPartnerEntity entity);

	BusinessPartnerEntity toEntity(BusinessPartner domain);
}