package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.DataUnits;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.unit.DataUnit;

/**
 * @author Fabian Feichter
 * @since 09.06.2026
 */
@Converter
public class DataUnitConverter implements AttributeConverter<DataUnit, String> {

	@Override
	public String convertToDatabaseColumn(DataUnit unit) {
		return unit == null ? null : DataUnits.toSuffix(unit);
	}

	@Override
	public DataUnit convertToEntityAttribute(String suffix) {
		return suffix == null ? null : DataUnit.fromSuffix(suffix);
	}
}
