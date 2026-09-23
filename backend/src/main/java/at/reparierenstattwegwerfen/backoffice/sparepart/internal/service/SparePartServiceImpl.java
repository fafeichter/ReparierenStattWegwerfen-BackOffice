package at.reparierenstattwegwerfen.backoffice.sparepart.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import at.reparierenstattwegwerfen.backoffice.sparepart.SparePartService;
import at.reparierenstattwegwerfen.backoffice.sparepart.internal.persistence.repository.SparePartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 23.09.2026
 */
@Service
@RequiredArgsConstructor
public class SparePartServiceImpl implements SparePartService {

	private final SparePartRepository sparePartRepository;

	@Override
	public NamedIdDto getSparePartById(Integer sparePartId) {
		return NamedIdDto.from(sparePartRepository.getReferenceById(sparePartId));
	}

	@Override
	public List<NamedIdDto> getSparePartsForModelSeries(Integer modelSeriesId) {
		return sparePartRepository.getSparePartsForModelSeries(modelSeriesId).stream()
			.map(NamedIdDto::from)
			.toList();
	}
}