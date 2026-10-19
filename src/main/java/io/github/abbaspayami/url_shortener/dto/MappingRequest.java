package io.github.abbaspayami.url_shortener.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record MappingRequest(
        @NotBlank String url,
        @NotNull @Min(4) @Max(10) @DefaultValue() Integer length
) { }