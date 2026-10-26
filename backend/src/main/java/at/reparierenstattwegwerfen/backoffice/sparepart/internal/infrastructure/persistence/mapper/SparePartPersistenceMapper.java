package at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain.SparePart;
import at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain.SparePartAvailableModelSeries;
import at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.model.SparePartAvailableModelSeriesEntity;
import at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.model.SparePartEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Mapper
public interface SparePartPersistenceMapper {

	SparePart toDomain(SparePartEntity entity);

	SparePartEntity toEntity(SparePart domain);

	SparePartAvailableModelSeries toDomain(SparePartAvailableModelSeriesEntity entity);

	SparePartAvailableModelSeriesEntity toEntity(SparePartAvailableModelSeries domain);
}