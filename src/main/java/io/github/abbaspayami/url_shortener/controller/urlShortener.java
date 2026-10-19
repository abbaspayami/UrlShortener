package io.github.abbaspayami.url_shortener.controller;

import io.github.abbaspayami.url_shortener.dto.MappingRequest;
import io.github.abbaspayami.url_shortener.dto.MappingResponse;
import io.github.abbaspayami.url_shortener.service.ShortenerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@Validated
public class urlShortener {

    private final ShortenerService service;

    public urlShortener(ShortenerService shortenerService) {
        this.service = shortenerService;
    }

    @PostMapping
    public ResponseEntity<MappingResponse> shorten(@RequestBody @Valid MappingRequest mappingRequest
            , UriComponentsBuilder uriBuilder) {
        MappingResponse response = service.shorten(mappingRequest);
        URI location = uriBuilder.path("/v1/animals/{id}")
                .build(response);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{code}")
    public MappingResponse getUrl(@PathVariable @NotBlank String code) {
        return service.resolve(code);
    }

}
