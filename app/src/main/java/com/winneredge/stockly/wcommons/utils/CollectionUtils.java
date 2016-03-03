package com.winneredge.stockly.wcommons.utils;

import java.util.Collection;

/**
 * Created by Manikanta on 2/28/2016.
 */
public class CollectionUtils {

    /**
     * Hide the default constructor. Instantiating utility classes does not make sense.
     */
    private CollectionUtils() {

    }

    public static boolean isNotEmpty(Collection input) {
        return input != null && !input.isEmpty();
    }

    public static boolean isEmpty(Collection input) {
        return input == null || input.isEmpty();
    }

}
