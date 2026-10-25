package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import org.springframework.util.unit.DataUnit;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
public final class DataUnits {

	private DataUnits() {
	}

	public static String toSuffix(DataUnit unit) {
		return switch (unit) {
			case BYTES -> "B";
			case KILOBYTES -> "KB";
			case MEGABYTES -> "MB";
			case GIGABYTES -> "GB";
			case TERABYTES -> "TB";
		};
	}
}
