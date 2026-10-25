package at.reparierenstattwegwerfen.backoffice.model.internal.domain;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelAvailableAppleSilicon;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelColor;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class Model implements NamedId {

	private Integer id;
	private String name;
	private ModelSeries modelSeries;
	private String modelNumber;
	private String technicalSpecsUrl;
	private Short releaseYear;
	private Short releaseMonth;
	private Short displaySize;
	private BigDecimal displaySizeExact;
	private List<ModelColor> availableColors;
	private List<ModelFeature> availableFeatures;
	private List<ModelAvailableAppleSilicon> availableAppleSilicons;
}