package at.reparierenstattwegwerfen.backoffice.model.internal.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * @author Fabian Feichter
 */
@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ModelDto {

    @EqualsAndHashCode.Include
    @NotNull
    Integer id;

    @NotBlank
    @Size(max = 256)
    String name;

    @NotBlank
    @Size(max = 5)
    String modelNumber;

    @NotNull
    @Positive
    Short releaseYear;

    @NotNull
    @Positive
    Short releaseMonth;
}
