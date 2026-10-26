package at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import at.reparierenstattwegwerfen.backoffice.sparepart.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Service
@RequiredArgsConstructor
public class SparePartServiceImpl implements SparePartService {

	private final SparePartRepository sparePartRepository;

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getSparePartById(Integer sparePartId) {
		return NamedIdDto.from(sparePartRepository.getSparePartById(sparePartId));
	}

	@Transactional(readOnly = true)
	@Override
	public List<NamedIdDto> getSparePartsForModelSeriesExcluding(Integer modelSeriesId, Set<Integer> excludeIds) {
		return sparePartRepository.getSparePartsForModelSeriesExcluding(modelSeriesId, excludeIds)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}
}