package com.zetapuppis.arguments;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Parser for positional keywords
 */
public class PositionalParser {
    // use a Map to avoid duplicates
    private Map<Integer, PositionalArgument> mPositions = new HashMap<Integer, PositionalArgument>();

    public void addPositional(final PositionalArgument positionalArgument) {
        mPositions.put(positionalArgument.getPosition(), positionalArgument);
    }

    public ParsedArguments parse(final String[] args) throws PositionalArgumentException {
        final ParsedArguments parsed = new ParsedArguments();
        final TreeSet<PositionalArgument> sortedPositionalArguments = getSorted();

        if (sortedPositionalArguments.size() == 0) {
            return parsed;
        }

        checkArguments();
        if (sortedPositionalArguments.last().getPosition() > args.length) {
            throw new PositionalArgumentException("not enough keyword arguments");
        }

        for (PositionalArgument positionalArgument : sortedPositionalArguments) {
            parsed.set(positionalArgument.getName(), args[positionalArgument.getPosition() - 1]);
        }

        return parsed;
    }

    private void checkArguments() throws PositionalArgumentException {
        TreeSet<PositionalArgument> sortedPositionalArguments = getSorted();
        // keyword indexing starts at 1, *NOT* 0
        int expectedIdx = 1;

        // a TreeSet is expected to automatically keep a sorted list
        for (PositionalArgument k : sortedPositionalArguments) {
            if (k.getPosition() != expectedIdx) {
                throw new PositionalArgumentException(
                        String.format("keyword %s can't be at position %d", k.getName(), k.getPosition()));
            }
            expectedIdx++;
        }
    }

    private TreeSet<PositionalArgument> getSorted() {
        return new TreeSet<PositionalArgument>(mPositions.values());
    }
}
