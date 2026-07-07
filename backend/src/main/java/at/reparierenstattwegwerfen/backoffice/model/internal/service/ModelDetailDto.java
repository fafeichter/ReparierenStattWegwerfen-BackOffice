package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ModelDetailDto {

	@EqualsAndHashCode.Include
	@NotNull
	Integer id;

	@NotBlank
	@Size(max = 256)
	String name;

	@NotBlank
	@Size(max = 256)
	String series;

	@NotBlank
	@Size(max = 5)
	String modelNumber;

	@NotBlank
	@Size(max = 256)
	String technicalSpecsUrl;

	@NotNull
	@Positive
	Short releaseYear;

	@NotNull
	@Positive
	Short displaySize;

	@NotNull
	@Positive
	BigDecimal displaySizeExact;

	@NotNull
	@Valid
	List<@NotNull ColorDto> colors;

	@NotNull
	@Valid
	List<@NotNull FeatureDto> features;

	@NotNull
	@Valid
	List<@NotNull SiliconDto> siliconOptions;

	@Value
	@Builder
	@EqualsAndHashCode(onlyExplicitlyIncluded = true)
	public static class ColorDto {
		@EqualsAndHashCode.Include
		@NotNull
		Integer id;

		@NotBlank
		@Size(max = 256)
		String name;
	}

	@Value
	@Builder
	@EqualsAndHashCode(onlyExplicitlyIncluded = true)
	public static class FeatureDto {
		@EqualsAndHashCode.Include
		@NotNull
		Integer id;

		@NotBlank
		@Size(max = 512)
		String name;

		@NotBlank
		@Size(max = 128)
		String category;
	}

	@Value
	@Builder
	@EqualsAndHashCode(onlyExplicitlyIncluded = true)
	public static class SiliconDto {
		@EqualsAndHashCode.Include
		@NotNull
		Integer id;

		@NotBlank
		@Size(max = 256)
		String name;

		@NotBlank
		@Size(max = 64)
		String nameShort;

		@NotNull
		@PositiveOrZero
		Integer numberCpuEfficiencyCores;

		@NotNull
		@Positive
		Integer numberCpuPerformanceCores;

		@NotNull
		@PositiveOrZero
		Integer numberCpuSuperCores;

		@NotNull
		@PositiveOrZero
		Integer numberGpuCores;

		@NotNull
		@Valid
		List<@NotNull SizeDto> storageOptions;

		@NotNull
		@Valid
		List<@NotNull SizeDto> memoryOptions;
	}

	@Value
	@Builder
	@EqualsAndHashCode(onlyExplicitlyIncluded = true)
	public static class SizeDto {
		@EqualsAndHashCode.Include
		@NotNull
		Integer id;

		@NotNull
		@Positive
		Short size;

		@NotBlank
		@Size(max = 2)
		String unit;
	}
}