package io.github.abbaspayami.url_shortener.exception;

public class UrlNotFoundException extends RuntimeException{
    public UrlNotFoundException(String code) {
        super("URL not found for code: " + code);
    }
}
