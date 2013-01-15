package com.zetapuppis.arguments;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Combined PositionalArgument and SwitchArgument parsers.
 * This is a high level parsing utility and it's the one that should
 * always be used, regardless of the type of parsing needed.
 */
public class CommandLineParser {
    private final String[] mArgs;
    private final PositionalParser mPositionalParser = new PositionalParser();
    private final SwitchParser mSwitchParser = new SwitchParser();
    private final Set<String> mArgumentNameSet = new HashSet<String>();
    private int mPositionalArgumentsCount = 0;

    private CommandLineParser(@Nonnull final String[] args) {
        mArgs = new String[args.length];
        System.arraycopy(args, 0, mArgs, 0, args.length);
    }

    public static CommandLineParser from(final String[] args) {
        return new CommandLineParser(args);
    }

    public CommandLineParser addPositional(final String name, final int position) throws CmdLineException {
        return addPositional(new PositionalArgument(name, position));
    }

    public CommandLineParser addPositional(final PositionalArgument positionalArgument) throws CmdLineException {
        if (mArgumentNameSet.contains(positionalArgument.getName())) {
            throw new CmdLineException(
                    String.format("'%s' is a duplicate argument name for positional argument", positionalArgument.getName()));
        }
        mArgumentNameSet.add(positionalArgument.getName());
        mPositionalParser.addPositional(positionalArgument);
        mPositionalArgumentsCount++;

        return this;
    }

    public CommandLineParser addSwitch(final String name,
                                       final boolean hasValue,
                                       final boolean isRequired) throws CmdLineException {
        return addSwitch(new SwitchArgument(name, hasValue, isRequired));
    }

    public CommandLineParser addSwitch(final String name,
                                       final String shortName,
                                       final boolean hasValue,
                                       final boolean isRequired) throws CmdLineException {
        return addSwitch(new SwitchArgument(name, shortName, hasValue, isRequired));
    }

    public CommandLineParser addSwitch(final SwitchArgument switchArgument) throws CmdLineException {
        if (mArgumentNameSet.contains(switchArgument.getName())) {
            throw new CmdLineException(
                    String.format("'%s' is a duplicate argument name for keyword", switchArgument.getName()));
        }
        mArgumentNameSet.add(switchArgument.getName());
        mSwitchParser.addSwitch(switchArgument);
        return this;
    }

    public ParsedArguments parse() throws SwitchArgumentException, PositionalArgumentException {
        final ParsedArguments parsed = new ParsedArguments();
        String[] argsCopy = new String[mArgs.length];
        System.arraycopy(mArgs, 0, argsCopy, 0, mArgs.length);

        final ParsedArguments parsedPositions = mPositionalParser.parse(argsCopy);
        for (Map.Entry<String, String> k : parsedPositions) {
            parsed.set(k.getKey(), k.getValue());
        }

        argsCopy = LineUtils.shiftArgs(argsCopy, mPositionalArgumentsCount);

        final ParsedArguments parsedSwitches = mSwitchParser.parse(argsCopy);
        for (Map.Entry<String, String> o : parsedSwitches) {
            parsed.set(o.getKey(), o.getValue());
        }

        return parsed;
    }
}
