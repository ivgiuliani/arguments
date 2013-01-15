package com.zetapuppis.arguments;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Collection of commandline-specific utilities.
 */
public class LineUtils {
    /**
     * Shifts an array of strings by one item to the left.
     * @param args array of strings
     * @return the current array shifted by one item or an empty array
     *         if there are no items to shift.
     */
    public static String[] shiftArgs(final String[] args) {
        return shiftArgs(args, 1);
    }

    /**
     * Shifts an array of strings by the specified number of items to
     * the left.
     * @param args array of strings
     * @return the current array shifted by the specified number of
     *         items or an empty array if there aren't enough items
     *         to shift.
     */
    public static String[] shiftArgs(@Nonnull final String[] args,
                                     final int count) {
        if (count > args.length) {
            return new String[] {};
        }
        return Arrays.copyOfRange(args, count, args.length);
    }
}
