package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddressCountry;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerAddressCountryEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Mapper
public interface BusinessPartnerAddressCountryPersistenceMapper {
	BusinessPartnerAddressCountry toDomain(BusinessPartnerAddressCountryEntity entity);

	BusinessPartnerAddressCountryEntity toEntity(BusinessPartnerAddressCountry domain);
}