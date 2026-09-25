package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Fabian Feichter
 * @since 25.09.2026
 */
@Getter
@RequiredArgsConstructor
public class Price {

	private final Double amount;

	public String formatAsEuro() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		symbols.setCurrencySymbol("€");

		return new DecimalFormat("¤ #,##0.00", symbols)
			.format(amount);
	}
}