package com.zetapuppis.arguments;

/**
 * A "switched" option. Switched options are options that can be preceeded
 * by one or two dashes.
 * <br/>
 * Long options are preceeded by two dashes (<code>--option</code>) while
 * short options use only one dash and usually are composed of only one
 * letter (<code>-l</code>).
 * <br/>
 * The long name is mandatory, while the short name is optional.
 * Options can be mandatory and been assigned a value.
 */
public final class SwitchArgument implements ArgumentItem {
    private final String mName;
    private final String mShortName;
    private final boolean mRequired;
    private final boolean mHasValue;

    /**
     * Builds a new option instance without a short name.
     * @param name long name
     * @param hasValue requires a value afterwards
     * @param required is mandatory
     */
    public SwitchArgument(final String name,
                          final boolean hasValue,
                          final boolean required) {
        this(name, "", hasValue, required);
    }

    /**
     * Builds a new option instance with a short name.
     * @param name long name
     * @param shortName short name
     * @param hasValue requires a value afterwards
     * @param required is mandatory
     */
    public SwitchArgument(final String name,
                          final String shortName,
                          final boolean hasValue,
                          final boolean required) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("long name can't be empty");
        }

        mName = name;
        mShortName = shortName;
        mHasValue = hasValue;
        mRequired = required;
    }

    /**
     * Returns the option's long name without the preceeding dashes.
     * @return the option's long name
     */
    @Override
    public String getName() {
        return mName;
    }

    /**
     * Returns the option's short name without the preceeding dashes.
     * This might be an empty string if the option doesn't specify a
     * short name.
     * <br/>
     * The presence of the short name can be checked with {@link #hasShortName()}.
     * @return the option's short name or an empty string
     */
    public String getShortName() {
        return mShortName;
    }

    /**
     * Checks whether the option has associated a short name.
     * @return true if the option specifies a short name, false otherwise
     */
    public boolean hasShortName() {
        return !mShortName.isEmpty();
    }

    /**
     * Checks whether the option is marked as required.
     * @return true if the option is required, false otherwise
     */
    public boolean isRequired() {
        return mRequired;
    }

    /**
     * Checks whether the option requires a value afterwards.
     * @return true if the option needs a value, false otherwise
     */
    public boolean hasValue() {
        return mHasValue;
    }
}
