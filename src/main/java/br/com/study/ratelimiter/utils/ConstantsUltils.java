package br.com.study.ratelimiter.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantsUltils implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String RATE_LIMITER_SIMPLE = "simple-rate-limit";

    public static final String RATE_LIMITER_SPECIFIC = "rate-limiter-events-specific";

    public static final String MESSAGE = "message";

}
