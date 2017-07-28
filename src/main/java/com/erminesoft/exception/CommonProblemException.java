package com.erminesoft.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonProblemException extends RuntimeException {
    private final static Logger logger = LoggerFactory.getLogger(CommonProblemException.class);

    public CommonProblemException() {
        logger.warn("CommonProblemException thrown here!");
    }

    public CommonProblemException(String message) {
        super(message);
        logger.warn("CommonProblemException thrown here, cause: {}", message);
    }
}
