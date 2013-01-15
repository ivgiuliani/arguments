package com.zetapuppis.arguments;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeywordParsingTest {
    @Test
    public void testEmpty() {
        final PositionalParser parser = new PositionalParser();
        try {
            parser.parse(new String[] {});
        } catch (PositionalArgumentException e) {
            fail("cannot parse empty parsing spec");
        }
    }

    @Test
    public void testSimpleKeywordParsing() throws ArgumentTypeException {
        PositionalParser parser = new PositionalParser();
        parser.addPositional(new PositionalArgument("command", 1));
        parser.addPositional(new PositionalArgument("type", 2));

        String[] args = new String[] {
            "commandValue",
            "typeValue"
        };

        ParsedArguments parsedArguments;

        try {
            parsedArguments = parser.parse(args);
        } catch (PositionalArgumentException e) {
            fail("cannot parse a valid keyword sequence");
            return;
        }

        assertEquals(parsedArguments.getString("command"), "commandValue");
        assertEquals(parsedArguments.getString("type"), "typeValue");
    }

    @Test
    public void testMissingItems() {
        PositionalParser parser = new PositionalParser();
        parser.addPositional(new PositionalArgument("cmd1", 1));
        parser.addPositional(new PositionalArgument("cmd2", 2));
        parser.addPositional(new PositionalArgument("cmd5", 5));

        String[] args = new String[] {
                "cmd1",
                "cmd2",
                "cmd3",
                "cmd4",
                "cmd5",
        };

        try {
            parser.parse(args);
        } catch (PositionalArgumentException e) {
            return;
        }

        fail("didn't recognize missing items");
    }

    @Test
    public void testRandomOrderingParsing() throws ArgumentTypeException {
        PositionalParser parser = new PositionalParser();
        parser.addPositional(new PositionalArgument("cmd10", 10));
        parser.addPositional(new PositionalArgument("cmd1", 1));
        parser.addPositional(new PositionalArgument("cmd5", 5));
        parser.addPositional(new PositionalArgument("cmd4", 4));
        parser.addPositional(new PositionalArgument("cmd7", 7));
        parser.addPositional(new PositionalArgument("cmd2", 2));
        parser.addPositional(new PositionalArgument("cmd3", 3));
        parser.addPositional(new PositionalArgument("cmd9", 9));
        parser.addPositional(new PositionalArgument("cmd6", 6));
        parser.addPositional(new PositionalArgument("cmd8", 8));

        String[] args = new String[] {
                "cmd1", "cmd2", "cmd3", "cmd4", "cmd5",
                "cmd6", "cmd7", "cmd8", "cmd9", "cmd10",
        };

        ParsedArguments parsedArguments;

        try {
            parsedArguments = parser.parse(args);
        } catch (PositionalArgumentException e) {
            fail("cannot parse a valid keyword sequence");
            return;
        }

        assertEquals(parsedArguments.getString("cmd1"), "cmd1");
        assertEquals(parsedArguments.getString("cmd2"), "cmd2");
        assertEquals(parsedArguments.getString("cmd3"), "cmd3");
        assertEquals(parsedArguments.getString("cmd4"), "cmd4");
        assertEquals(parsedArguments.getString("cmd5"), "cmd5");
        assertEquals(parsedArguments.getString("cmd6"), "cmd6");
        assertEquals(parsedArguments.getString("cmd7"), "cmd7");
        assertEquals(parsedArguments.getString("cmd8"), "cmd8");
        assertEquals(parsedArguments.getString("cmd9"), "cmd9");
        assertEquals(parsedArguments.getString("cmd10"), "cmd10");
    }

    @Test
    public void testInvalidNumbering() {
        PositionalParser parser = new PositionalParser();
        parser.addPositional(new PositionalArgument("cmd0", 0));
        parser.addPositional(new PositionalArgument("cmd1", 1));

        String[] args = new String[] {
                "cmd1",
                "cmd2"
        };

        try {
            parser.parse(args);
        } catch (PositionalArgumentException e) {
            return;
        }

        fail("parsed an invalid keyword sequence");
    }

    @Test
    public void testOverrideKeywords() throws ArgumentTypeException {
        PositionalParser parser = new PositionalParser();
        parser.addPositional(new PositionalArgument("cmd", 1));
        parser.addPositional(new PositionalArgument("cmd2", 1));

        String[] args = new String[] {
                "value"
        };

        ParsedArguments parsedArguments;
        try {
            parsedArguments = parser.parse(args);
        } catch (PositionalArgumentException e) {
            fail("couldn't parse a valid keyword sequence");
            return;
        }

        assertEquals(parsedArguments.getString("cmd2"), "value");
    }
}
