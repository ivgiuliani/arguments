package com.zetapuppis.arguments;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for switch-based command line arguments (arguments in the form
 * of '<code>-c</code>' or '<code>--option</code>').
 */
public class SwitchParser {
    private final static String OPT_LONG_PREFIX = "--";
    private final static String OPT_SHORT_PREFIX = "-";

    private enum State {
        START,
        LONG_OPT,
        SHORT_OPT,
        VALUE
    }

    private final List<SwitchArgument> mSwitchArgumentList = new ArrayList<SwitchArgument>();

    /**
     * Adds a new switch argument.
     * @param switchArgument an instance of {@link SwitchArgument}
     */
    public void addSwitch(final SwitchArgument switchArgument) {
        mSwitchArgumentList.add(switchArgument);
    }

    /**
     * Parses the given switch list.
     * @param args command line arguments
     * @return an instance of {@link ParsedArguments} if parsing completes
     *         without errors
     * @throws SwitchArgumentException if required arguments are missing of arguments are
     *         not formatted properly
     */
    public ParsedArguments parse(@Nonnull final String[] args) throws SwitchArgumentException {
        final ParsedArguments parsed = new ParsedArguments();
        final Map<String, SwitchArgument> switchIndex = preprocessSwitches();

        State currentState = State.START;
        String currentSwitch = "";

        int i = 0;
        while (i < args.length) {
            String arg = args[i];

            switch (currentState) {
                case START:
                    // no known state (start of a new argument)
                    if (arg.startsWith(OPT_LONG_PREFIX)) {
                        currentState = State.LONG_OPT;
                        if (!switchIndex.containsKey(arg)) {
                            throw new SwitchArgumentException(
                                    String.format("%s is an unknown argument", arg));
                        }
                        currentSwitch = switchIndex.get(arg).getName();
                        continue;
                    }

                    if (arg.startsWith(OPT_SHORT_PREFIX)) {
                        currentState = State.SHORT_OPT;
                        if (!switchIndex.containsKey(arg)) {
                            throw new SwitchArgumentException(
                                    String.format("%s is an unknown argument", arg));
                        }
                        currentSwitch = switchIndex.get(arg).getName();
                        continue;
                    }

                    throw new SwitchArgumentException("invalid arguments");

                case LONG_OPT:
                case SHORT_OPT:
                    // recognized argument
                    if (!switchIndex.containsKey(arg)) {
                        throw new SwitchArgumentException(
                                String.format("%s is an unrecognized argument", arg));
                    }

                    if (switchIndex.get(arg).hasValue()) {
                        currentState = State.VALUE;
                        if ((i + 1) == args.length) {
                            throw new SwitchArgumentException(
                                    String.format("missing value for %s", arg));
                        }
                    } else {
                        currentState = State.START;
                    }

                    parsed.set(currentSwitch);
                    i++;
                    break;

                case VALUE:
                    // argument has a value right next to it
                    parsed.set(currentSwitch, arg);
                    currentState = State.START;
                    i++;
                    break;
            }
        }

        checkRequirements(parsed);

        return parsed;
    }

    private Map<String, SwitchArgument> preprocessSwitches() {
        Map<String, SwitchArgument> stringToSwitch = new HashMap<String, SwitchArgument>();

        for (SwitchArgument switchArgument : mSwitchArgumentList) {
            String longOpt = OPT_LONG_PREFIX.concat(switchArgument.getName());
            stringToSwitch.put(longOpt, switchArgument);

            if (switchArgument.hasShortName()) {
                String shortOpt = OPT_SHORT_PREFIX.concat(switchArgument.getShortName());
                stringToSwitch.put(shortOpt, switchArgument);
            }
        }

        return stringToSwitch;
    }

    private void checkRequirements(final ParsedArguments parsed) throws SwitchArgumentException {
        for (SwitchArgument switchArgument : mSwitchArgumentList) {
            if (switchArgument.isRequired() && !parsed.has(switchArgument.getName())) {
                throw new SwitchArgumentException(
                        String.format("%s was a required argument", switchArgument.getName()));
            }
        }
    }
}
