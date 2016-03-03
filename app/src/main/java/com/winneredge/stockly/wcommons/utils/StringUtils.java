package com.winneredge.stockly.wcommons.utils;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class StringUtils {
    /**
     * Hide the default constructor. Instantiating utility classes does not make sense.
     */
    private StringUtils() {

    }

    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }


}
