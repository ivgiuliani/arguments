package com.zetapuppis.arguments;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommandLineParserTest {
    private static String[] ARGS_THREE_KEYWORDS = { "kw1", "kw2", "kw3" };
    private static String[] ARGS_SWITCHES_CLASSIC = new String[] {
            "--required1", "required1",
            "--required2", "required2",
            "--optional", "value",
    };
    private static String[] ARGS_MIXED_CLASSIC = new String[] {
            // keywords
            "item1", "item2", "item3",

            // switches
            "--required1", "required1",
            "--required2", "required2",
            "--optional", "value",
    };
    private static String[] ARGS_SHORT_SWITCHES = new String[] {
            "-i", "v1",
            "-o", "v2"
    };

    @Test
    public void testKeywordOnly() throws ArgumentTypeException {
        final CommandLineParser parser = CommandLineParser.from(ARGS_THREE_KEYWORDS);

        try {
            parser.addPositional(new PositionalArgument("keyword1", 1));
            parser.addPositional(new PositionalArgument("keyword2", 2));
            parser.addPositional(new PositionalArgument("keyword3", 3));
        } catch (CmdLineException e) {
            fail("cannot add valid keywords");
        }

        ParsedArguments parsedArguments;
        try {
            parsedArguments = parser.parse();
        } catch (CmdLineException e) {
            fail("raised an invalid exception: " + e.getMessage());
            return;
        }

        assertEquals(parsedArguments.getString("keyword1"), "kw1");
        assertEquals(parsedArguments.getString("keyword2"), "kw2");
        assertEquals(parsedArguments.getString("keyword3"), "kw3");
    }

    @Test
    public void testOptionsOnly() throws ArgumentTypeException {
        CommandLineParser parser = CommandLineParser.from(ARGS_SWITCHES_CLASSIC);
        try {
            parser.addSwitch(new SwitchArgument("required1", "", true, true));
            parser.addSwitch(new SwitchArgument("required2", "", true, true));
            parser.addSwitch(new SwitchArgument("boolean", "", false, false));
            parser.addSwitch(new SwitchArgument("optional", "", true, false));
        } catch (CmdLineException e) {
            fail("cannot add valid options");
        }

        ParsedArguments parsedArguments;
        try {
            parsedArguments = parser.parse();
        } catch (CmdLineException ex) {
            fail("raised an invalid exception: " + ex.getMessage());
            return;
        }

        assertTrue(parsedArguments.has("required1"));
        assertTrue(parsedArguments.has("required2"));
        assertEquals(parsedArguments.getString("required1"), "required1");
        assertEquals(parsedArguments.getString("required2"), "required2");
        assertTrue(parsedArguments.has("optional"));
        assertEquals(parsedArguments.getString("optional"), "value");
        assertFalse(parsedArguments.has("invalid"));
        assertFalse(parsedArguments.has("boolean"));
        assertFalse(parsedArguments.has("boolean"));
    }

    @Test
    public void testShortSwitches() throws CmdLineException {
        ParsedArguments parsed = CommandLineParser.from(ARGS_SHORT_SWITCHES)
                .addSwitch("input", "i", true, true)
                .addSwitch("output", "o", true, true)
                .parse();

        assertTrue(parsed.getString("input").equals("v1"));
        assertTrue(parsed.getString("output").equals("v2"));
    }

    @Test
    public void testDuplicateNames() {
        CommandLineParser parser = CommandLineParser.from(new String[] {});
        try {
            parser.addSwitch(new SwitchArgument("key", "", true, true));
            parser.addPositional(new PositionalArgument("key", 1));
            fail("added duplicate keywords");
        } catch (CmdLineException e) {
            // everything ok, this was expected
        }
    }

    @Test
    public void testMixedKeywordsOptions() throws ArgumentTypeException {
        CommandLineParser parser = CommandLineParser.from(ARGS_MIXED_CLASSIC);
        try {
            parser.addPositional(new PositionalArgument("keyword1", 1));
            parser.addPositional(new PositionalArgument("keyword2", 2));
            parser.addPositional(new PositionalArgument("keyword3", 3));

            parser.addSwitch(new SwitchArgument("required1", "", true, true));
            parser.addSwitch(new SwitchArgument("required2", "", true, true));
            parser.addSwitch(new SwitchArgument("boolean", "", false, false));
            parser.addSwitch(new SwitchArgument("optional", "", true, false));
        } catch (CmdLineException e) {
            fail("cannot add valid keywords");
        }

        ParsedArguments parsedArguments;
        try {
            parsedArguments = parser.parse();
        } catch (CmdLineException e) {
            fail("raised an invalid exception: " + e.getMessage());
            return;
        }

        assertTrue(parsedArguments.has("keyword1"));
        assertTrue(parsedArguments.has("keyword2"));
        assertTrue(parsedArguments.has("keyword3"));
        assertEquals(parsedArguments.getString("keyword1"), "item1");
        assertEquals(parsedArguments.getString("keyword2"), "item2");
        assertEquals(parsedArguments.getString("keyword3"), "item3");

        assertTrue(parsedArguments.has("required1"));
        assertTrue(parsedArguments.has("required2"));
        assertEquals(parsedArguments.getString("required1"), "required1");
        assertEquals(parsedArguments.getString("required2"), "required2");
        assertTrue(parsedArguments.has("optional"));
        assertEquals(parsedArguments.getString("optional"), "value");
        assertFalse(parsedArguments.has("invalid"));
        assertFalse(parsedArguments.has("boolean"));
        assertFalse(parsedArguments.has("boolean"));
    }

    @Test
    public void testShortCalling() throws ArgumentTypeException {
        CommandLineParser parser = CommandLineParser.from(ARGS_MIXED_CLASSIC);
        try {
            parser.addPositional("keyword1", 1);
            parser.addPositional("keyword2", 2);
            parser.addPositional("keyword3", 3);

            parser.addSwitch("required1", "", true, true);
            parser.addSwitch("required2", "", true, true);
            parser.addSwitch("boolean", "", false, false);
            parser.addSwitch("optional", "", true, false);
        } catch (CmdLineException e) {
            fail("cannot add valid keywords");
        }

        ParsedArguments parsedArguments;
        try {
            parsedArguments = parser.parse();
        } catch (CmdLineException e) {
            fail("raised an invalid exception: " + e.getMessage());
            return;
        }

        assertTrue(parsedArguments.has("keyword1"));
        assertTrue(parsedArguments.has("keyword2"));
        assertTrue(parsedArguments.has("keyword3"));
        assertEquals(parsedArguments.getString("keyword1"), "item1");
        assertEquals(parsedArguments.getString("keyword2"), "item2");
        assertEquals(parsedArguments.getString("keyword3"), "item3");

        assertTrue(parsedArguments.has("required1"));
        assertTrue(parsedArguments.has("required2"));
        assertEquals(parsedArguments.getString("required1"), "required1");
        assertEquals(parsedArguments.getString("required2"), "required2");
        assertTrue(parsedArguments.has("optional"));
        assertEquals(parsedArguments.getString("optional"), "value");
        assertFalse(parsedArguments.has("invalid"));
        assertFalse(parsedArguments.has("boolean"));
        assertFalse(parsedArguments.has("boolean"));
    }

    @Test(expected = CmdLineException.class)
    public void testOptionOverrideIsNotAllowed_switches() throws CmdLineException {
        CommandLineParser parser = CommandLineParser.from(new String[] {});
        parser.addSwitch("value", true, true);
        parser.addSwitch("value", true, true);
    }

    @Test(expected = CmdLineException.class)
    public void testOptionOverrideIsNotAllowed_positional() throws CmdLineException {
        CommandLineParser parser = CommandLineParser.from(new String[] {});
        parser.addPositional("keyword", 1);
        parser.addPositional("keyword", 2);
    }

    @Test
    public void testChaining() {
        try {
            CommandLineParser.from(ARGS_MIXED_CLASSIC)
                    .addPositional("item1", 1)
                    .addPositional("item2", 2)
                    .addPositional("item3", 3)
                    .addSwitch("required1", true, true)
                    .addSwitch("required2", true, true)
                    .addSwitch("boolean", false, false)
                    .addSwitch("optional", true, false)
                    .parse();
        } catch (CmdLineException e) {
            fail("Failed parsing valid argument sequence");
        }
    }

    @Test
    public void testChaining_randomOrder() {
        try {
            CommandLineParser.from(ARGS_MIXED_CLASSIC)
                    .addSwitch("required1", true, true)
                    .addPositional("item3", 3)
                    .addSwitch("required2", true, true)
                    .addPositional("item1", 1)
                    .addSwitch("optional", true, false)
                    .addPositional("item2", 2)
                    .addSwitch("boolean", false, false)
                    .parse();
        } catch (CmdLineException e) {
            fail("Failed parsing valid argument sequence");
        }
    }
}
