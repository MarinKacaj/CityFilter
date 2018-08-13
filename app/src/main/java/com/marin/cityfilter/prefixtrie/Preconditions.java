package com.marin.cityfilter.prefixtrie;

/**
 * Runtime precondition checks
 *
 * @author Marin Kacaj
 */
public class Preconditions {

    private Preconditions() {
    }

    /**
     * Checks object ref for null
     *
     * @param ref          The object reference to check for null
     * @param errorMessage The message in the eventual exception
     * @throws NullPointerException if the object ref is null
     */
    public static void checkNotNull(Object ref, String errorMessage) {
        if (null == ref) {
            throw new NullPointerException(errorMessage);
        }
    }

    /**
     * Checks if object is string
     *
     * @param ref          The object to check if is string
     * @param errorMessage The message in the eventual exception
     * @throws ClassCastException if the object is not a string
     */
    public static void checkIsString(Object ref, String errorMessage) {
        if (!(ref instanceof String)) {
            throw new ClassCastException(errorMessage);
        }
    }
}
