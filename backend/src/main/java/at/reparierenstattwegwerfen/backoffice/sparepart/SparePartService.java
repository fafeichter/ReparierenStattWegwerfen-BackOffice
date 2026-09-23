package at.reparierenstattwegwerfen.backoffice.sparepart;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 23.09.2026
 */
public interface SparePartService {

	NamedIdDto getSparePartById(Integer sparePartId);

	List<NamedIdDto> getSparePartsForModelSeries(Integer modelSeriesId);
}