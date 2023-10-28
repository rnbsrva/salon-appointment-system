package com.akerke.authservice.common.utils;


/**
 * A simple data structure representing a pair of key and value.
 * This record is used to hold two related pieces of data together.
 *
 * @param key   The key of the pair.
 * @param value The value associated with the key.
 */
public record Pair(
        /**
         * The key of the pair.
         */
        String key,

        /**
         * The value associated with the key.
         */
        Object value
) {
}
