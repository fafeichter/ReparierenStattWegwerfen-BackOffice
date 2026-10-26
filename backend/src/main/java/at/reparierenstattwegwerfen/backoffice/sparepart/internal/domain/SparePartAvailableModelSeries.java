package at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class SparePartAvailableModelSeries {

	private Integer id;
	private SparePart sparePart;
	private Integer modelSeriesId;
}