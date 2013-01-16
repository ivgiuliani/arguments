package com.zetapuppis.arguments;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Holder of parsed arguments.
 *
 * <p>Provides functions for converting items to the required datatype
 * as well as raw access to the stored arguments.
 */
public class ParsedArguments implements Iterable<Map.Entry<String, String>> {
    final Map<String, String> mOpts = new HashMap<String, String>();

    /* package */ ParsedArguments() {}

    private String get(@Nonnull final String name) throws ArgumentTypeException {
        if (!this.has(name)) {
            throw new ArgumentTypeException(String.format("%s is not a valid argument", name));
        }
        return mOpts.get(name);
    }

    /**
     * Checks if the given argument's name has been parsed.
     * @param name argument's name
     * @return true if the argument has been parsed or false otherwise
     */
    public boolean has(@Nonnull final String name) {
        return mOpts.containsKey(name);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return mOpts.entrySet().iterator();
    }

    /**
     * Set the given argument as existing (doesn't set a value)
     * @param name argument's name
     */
    public void set(@Nonnull final String name) {
        set(name, "");
    }

    /**
     * Set the given argument's value
     * @param name argument's name
     * @param value argument's value
     */
    public void set(@Nonnull final String name, @Nonnull final String value) {
        mOpts.put(name, value);
    }

    /**
     * Returns the value for the given argument parameter converted to a char
     * <p>
     * This also checks that the current value is actually a single char.
     * @param name argument parameter's name
     * @return the value for that argument converted to a char
     * @throws ArgumentTypeException if the parameter cannot be converted to a char
     *         or it doesn't exist
     */
    public char getChar(final String name) throws ArgumentTypeException {
        String value = get(name);
        char[] chars = value.toCharArray();
        if (chars.length > 1) {
            throw new ArgumentTypeException(
                    String.format("value %s for argument %s was not a single character", value, name));
        }
        return chars[0];
    }

    /**
     * Returns the value for the given argument parameter converted to a char
     * <p>
     * This also checks that the current value is actually a single char.
     * @param name argument parameter's name
     * @param defaultValue default value in case the argument is missing
     * @return the value for that argument converted to a char
     * @throws ArgumentTypeException if the parameter cannot be converted to a char
     *         or it doesn't exist
     */
    public char getChar(final String name, final char defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return getChar(name);
        }
        return defaultValue;
    }

    /**
     * Returns the value for the given argument parameter converted to a char.
     * <p>
     * This also checks that the current value is actually a single char and that lies
     * within a specified restricted set of possible choices
     * @param name argument parameter's name
     * @return the value for that argument converted to a char
     * @throws ArgumentTypeException if the parameter cannot be converted to a char
     *         or it doesn't exist
     */
    public char getCharChoice(final String name, final char[] choices) throws ArgumentTypeException {
        String[] strChoices = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            strChoices[i] = String.valueOf(choices[i]);
        }
        return getChoice(name, strChoices).toCharArray()[0];
    }

    /**
     * Returns the value for the given argument parameter converted to a char.
     * <p>
     * This also checks that the current value is actually a single char and that lies
     * within a specified restricted set of possible choices
     * @param name argument parameter's name
     * @param defaultValue default value in case the argument is missing
     * @return the value for that argument converted to a char
     * @throws ArgumentTypeException if the parameter cannot be converted to a char
     *         or it doesn't exist
     */
    public char getCharChoice(final String name, final char[] choices, final char defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return getCharChoice(name, choices);
        }
        return defaultValue;
    }

    /**
     * Returns the value for the given argument parameter converted as string
     * @param name argument parameter's name
     * @return the value for that argument converted to a string
     * @throws ArgumentTypeException if the parameter cannot be converted to a string
     *         or it doesn't exist
     */
    public String getString(final String name) throws ArgumentTypeException {
        return get(name);
    }

    /**
     * Returns the value for the given argument parameter converted as string
     * @param name argument parameter's name
     * @param defaultValue default value in case the argument is missing
     * @return the value for that argument converted to a string
     * @throws ArgumentTypeException if the parameter cannot be converted to a string
     *         or it doesn't exist
     */
    public String getString(final String name, final String defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return get(name);
        }
        return defaultValue;
    }

    /**
     * Returns the value for the given argument parameter checking that it also
     * within a specified restricted set of possible choices
     * @param name argument parameter's name
     * @return the value for that argument converted to a string
     * @throws ArgumentTypeException if the parameter cannot be converted to a string,
     *         it doesn't exist or it's not within the given set of possible choices
     */
    public String getChoice(final String name, final String[] choices) throws ArgumentTypeException {
        final String item = get(name);
        for (String choice : choices) {
            if (item.equals(choice)) {
                return item;
            }
        }

        throw new ArgumentTypeException(
                String.format("value %s for argument %s was not in the allowed choice set", item, name));
    }

    /**
     * Returns the value for the given argument parameter checking that it also
     * within a specified restricted set of possible choices
     * @param name argument parameter's name
     * @param defaultValue default value in case the argument is missing
     * @return the value for that argument converted to a string
     * @throws ArgumentTypeException if the parameter cannot be converted to a string,
     *         it doesn't exist or it's not within the given set of possible choices
     */
    public String getChoice(final String name, final String[] choices, final String defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return getChoice(name, choices);
        }
        return defaultValue;
    }

    /**
     * Returns the value for the given argument parameter converted to an integer
     * @param name argument parameter's name
     * @return the value for that argument converted to a string
     * @throws ArgumentTypeException if the parameter cannot be converted to a string
     *         or it doesn't exist
     */
    public int getInt(final String name) throws ArgumentTypeException {
        final String value = get(name);

        Integer ivalue;
        try {
            ivalue = Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            throw new ArgumentTypeException(
                    String.format("cannot convert %s to integer for argument %s",
                            value, name));
        }
        return ivalue;
    }

    /**
     * Returns the value for the given argument parameter converted to an integer
     * @param name argument parameter's name
     * @param defaultValue default value in case the argument is missing
     * @return the value for that argument converted to a string
     * @throws ArgumentTypeException if the parameter cannot be converted to a string
     *         or it doesn't exist
     */
    public int getInt(final String name, final int defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return getInt(name);
        }
        return defaultValue;
    }

    /**
     * Returns the value for the given argument parameter converted to a File object
     * @param name argument parameter's name
     * @return the value for that argument converted to a File object
     * @throws ArgumentTypeException if the parameter doesn't exist
     */
    public File getFile(final String name) throws ArgumentTypeException {
        final String path = get(name);
        return new File(path);
    }

    /**
     * Returns the value for the given argument parameter converted to a
     * {@link File} object
     * @param name argument parameter's name
     * @param defaultValue default value in case the argument is missing
     * @return the value for that argument converted to a {@link File} object
     * @throws ArgumentTypeException if the parameter doesn't exist
     */
    public File getFile(final String name, final File defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return getFile(name);
        }
        return defaultValue;
    }

    /**
     * Returns the value for the given argument parameter as a {@link File}
     * object if the given file does not exist.
     * @param name argument parameter's name
     * @return the value for that argument converted to a {@link File} object
     * @throws ArgumentTypeException if the parameter doesn't exist or the file
     *         exists already
     */
    public File getNewFile(final String name) throws ArgumentTypeException {
        final File file = getFile(name);

        if (file.exists()) {
            throw new ArgumentTypeException(String.format("file %s already exists", file.getPath()));
        }
        return file;
    }

    /**
     * Returns the value for the given argument parameter as a {@link File}
     * object if the given file does not exist.
     * @param name argument parameter's name
     * @param defaultValue default value in case the argument is missing
     * @return the value for that argument converted to a {@link File} object
     * @throws ArgumentTypeException if the parameter doesn't exist or the file
     *         exists already
     */
    public File getNewFile(final String name, final File defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return getNewFile(name);
        }
        return defaultValue;
    }

    public File getExistingFile(final String name) throws ArgumentTypeException {
        final File file = getFile(name);

        if (!file.exists()) {
            throw new ArgumentTypeException(String.format("file %s doesn't exists", file.getPath()));
        }

        if (file.isDirectory()) {
            throw new ArgumentTypeException(String.format("%s is a directory", file.getPath()));
        }

        return file;
    }

    public File getExistingFile(final String name, final File defaultValue) throws ArgumentTypeException {
        if (has(name)) {
            return getExistingFile(name);
        }
        return defaultValue;
    }
}