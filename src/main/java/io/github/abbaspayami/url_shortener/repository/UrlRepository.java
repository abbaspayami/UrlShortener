package io.github.abbaspayami.url_shortener.repository;

import java.util.Optional;

public interface UrlRepository {
    Optional<UrlMapping> findByCode(String code);
    Optional<UrlMapping> findByUrl(String url);
    void save(UrlMapping urlMapping);
}
