package io.github.abbaspayami.url_shortener.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUrlRepositoryImpl implements UrlRepository {

    private final Map<String, UrlMapping> byCode = new ConcurrentHashMap<>();
    private final Map<String, UrlMapping> byUrl = new ConcurrentHashMap<>();

    @Override
    public Optional<UrlMapping> findByCode(String code) {
        return Optional.ofNullable(byCode.get(code));
    }

    @Override
    public Optional<UrlMapping> findByUrl(String url) {
        return Optional.ofNullable(byUrl.get(url));
    }

    @Override
    public void save(UrlMapping urlMapping) {
        byCode.put(urlMapping.code(), urlMapping);
        byUrl.put(urlMapping.url(), urlMapping);
    }
}
