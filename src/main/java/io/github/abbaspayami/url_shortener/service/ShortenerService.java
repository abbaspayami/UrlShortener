package io.github.abbaspayami.url_shortener.service;

import io.github.abbaspayami.url_shortener.dto.MappingRequest;
import io.github.abbaspayami.url_shortener.dto.MappingResponse;
import io.github.abbaspayami.url_shortener.exception.UrlNotFoundException;
import io.github.abbaspayami.url_shortener.repository.InMemoryUrlRepositoryImpl;
import io.github.abbaspayami.url_shortener.repository.UrlMapping;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ShortenerService {

    private final InMemoryUrlRepositoryImpl inMemoryUrlRepository;
    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuABCDEFGHIJKLMNOPQRSTUWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public ShortenerService(InMemoryUrlRepositoryImpl inMemoryUrlRepository) {
        this.inMemoryUrlRepository = inMemoryUrlRepository;
    }

    public MappingResponse shorten(MappingRequest request) {
        return inMemoryUrlRepository.findByUrl(request.url())
                .map(m -> new MappingResponse(
                        m.url(),
                        m.code()
                ))
                .orElseGet(() ->
                        createMapping(request.url(), request.length())
                );
    }

    public MappingResponse resolve(String code) {
        return inMemoryUrlRepository.findByCode(code)
                .map(m-> new MappingResponse(
                        m.url(),
                        m.code()
                ))
                .orElseThrow(() -> new UrlNotFoundException(code));
    }

    private MappingResponse createMapping(String url, Integer length) {
        String code = generateCode(url, length);

        UrlMapping mapping = new UrlMapping(code, url);
        inMemoryUrlRepository.save(mapping);

        return new MappingResponse(mapping.url(), mapping.code());
    }

    public String generateCode(String url, int length) {

        StringBuilder builder = new StringBuilder();
        for (int i =0; i < length; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            builder.append(ALPHANUMERIC.charAt(index));
        }
        return builder.toString();
    }

}
