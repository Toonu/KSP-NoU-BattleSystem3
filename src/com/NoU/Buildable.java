package com.NoU;

/**
 * A base interface for builder implementations
 * @author Toonu
 * @param <T> type of built object
 */
public interface Buildable<T> {
    /**
     * Build an instance of {@link T}
     * @return instance of {@link T}
     */
    T build();
}
