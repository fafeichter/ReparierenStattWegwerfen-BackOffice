package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerAddressEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface BusinessPartnerAddressPersistenceMapper {
	BusinessPartnerAddress toDomain(BusinessPartnerAddressEntity entity);

	BusinessPartnerAddressEntity toEntity(BusinessPartnerAddress domain);
}