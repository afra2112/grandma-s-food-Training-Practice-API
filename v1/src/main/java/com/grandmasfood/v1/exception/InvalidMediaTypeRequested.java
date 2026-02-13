package com.grandmasfood.v1.exception;

public class InvalidMediaTypeRequested extends RuntimeException {
    public InvalidMediaTypeRequested(String mediaType) {
        super("Media type: " + mediaType + " requested is not supported.");
    }
}
