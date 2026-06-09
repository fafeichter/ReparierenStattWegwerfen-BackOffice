package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.unit.DataUnit;

/**
 * @author Fabian Feichter
 */
@Converter(autoApply = false)
public class DataUnitConverter implements AttributeConverter<DataUnit, String> {

    public static String toSuffix(DataUnit unit) {
        return switch (unit) {
            case BYTES -> "B";
            case KILOBYTES -> "KB";
            case MEGABYTES -> "MB";
            case GIGABYTES -> "GB";
            case TERABYTES -> "TB";
        };
    }

    @Override
    public String convertToDatabaseColumn(DataUnit unit) {
        return unit == null ? null : toSuffix(unit);
    }

    @Override
    public DataUnit convertToEntityAttribute(String suffix) {
        return suffix == null ? null : DataUnit.fromSuffix(suffix);
    }
}
