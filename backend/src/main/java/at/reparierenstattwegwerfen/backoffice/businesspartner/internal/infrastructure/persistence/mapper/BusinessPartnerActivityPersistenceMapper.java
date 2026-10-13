package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivity;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerActivityEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface BusinessPartnerActivityPersistenceMapper {
	BusinessPartnerActivity toDomain(BusinessPartnerActivityEntity entity);

	BusinessPartnerActivityEntity toEntity(BusinessPartnerActivity domain);
}