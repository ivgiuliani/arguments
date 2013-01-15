package com.zetapuppis.arguments;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwitchParsingTest {
    @Test
    public void testEmpty() {
        final SwitchParser parser = new SwitchParser();
        try {
            parser.parse(new String[] {});
        } catch (SwitchArgumentException e) {
            fail("cannot parse empty parsing spec");
        }
    }

    @Test
    public void testParseClassicSwitches() throws ArgumentTypeException {
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("required1", "", true, true));
        parser.addSwitch(new SwitchArgument("required2", "", true, true));
        parser.addSwitch(new SwitchArgument("boolean", "", false, false));
        parser.addSwitch(new SwitchArgument("optional", "", true, false));

        String[] args = new String[] {
                "--required1", "required1",
                "--required2", "required2",
                "--optional", "value",
        };

        ParsedArguments parsedArguments;
        try {
            parsedArguments = parser.parse(args);
        } catch (SwitchArgumentException e) {
            fail(String.format("failed parsing valid option case: %s", e.getMessage()));
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
    public void testParseOptionalSwitches() throws ArgumentTypeException {
        ParsedArguments parsedArguments = null;

        boolean failed = false;
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("optional", "", true, false));

        String[] args = new String[] {};

        try {
            parsedArguments = parser.parse(args);
        } catch (SwitchArgumentException e) {
            failed = true;
        }
        assertFalse("not parsed an valid condition", failed);
        assertFalse(parsedArguments.has("optional"));

        args = new String[] {
                "--optional"
        };
        failed = false;
        try {
            parsedArguments = parser.parse(args);
        } catch (SwitchArgumentException e) {
            failed = true;
        }
        assertTrue("expected argument", failed);

        args = new String[] {
                "--optional", "value"
        };
        failed = false;
        try {
            parsedArguments = parser.parse(args);
        } catch (SwitchArgumentException e) {
            failed = true;
        }
        assertFalse("threw invalid on a valid option parsing", failed);

        assertTrue(parsedArguments.has("optional"));
        assertEquals(parsedArguments.getString("optional"), "value");
    }

    @Test
    public void testMissingSwitches() {
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("required1", "", true, true));
        parser.addSwitch(new SwitchArgument("required2", "", true, true));
        parser.addSwitch(new SwitchArgument("required3", "", true, true));

        String[] args = new String[] {
                "--required1", "value",
                "--required2", "value",
        };

        boolean failed = false;
        try {
            parser.parse(args);
        } catch (SwitchArgumentException ex) {
            failed = true;
        }

        assertTrue("option was missing but arguments were parsed anyway", failed);
    }

    @Test(expected = CmdLineException.class)
    public void testSwitchesParsingException() throws SwitchArgumentException {
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("required1", "", true, true));
        parser.addSwitch(new SwitchArgument("required2", "", true, true));
        parser.addSwitch(new SwitchArgument("required3", "", true, true));

        String[] args = new String[] {
                "--required1", "value",
                "--required2", "value",
        };

        parser.parse(args);
    }

    @Test
    public void testOptionsOverride() throws ArgumentTypeException {
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("value", "", true, true));

        String[] args = new String[] {
                "--value", "value1",
                "--value", "value2",
        };

        ParsedArguments parsedArguments = null;
        try {
            parsedArguments = parser.parse(args);
        } catch (CmdLineException ex) {
            fail("cannot parse a valid option sequence");
        }

        assertEquals(parsedArguments.getString("value"), "value2");
    }

    @Test
    public void testSwitchesDatatype() {
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("string", "", true, true));
        parser.addSwitch(new SwitchArgument("int1", "", true, true));
        parser.addSwitch(new SwitchArgument("int2", "", true, true));

        String[] args = new String[] {
                "--string", "string",
                "--int1", "1",
                "--int2", "not an int",
        };

        ParsedArguments parsedArguments = null;
        try {
            parsedArguments = parser.parse(args);
        } catch (CmdLineException ex) {
            fail("cannot parse a valid option sequence");
        }

        try {
            assertEquals(parsedArguments.getString("string"), "string");
            assertEquals(parsedArguments.getInt("int1"), 1);
        } catch (ArgumentTypeException e) {
            fail("cannot convert valid datatypes");
        }

        try {
            parsedArguments.getInt("int2");
            fail("converted invalid datatypes");
        } catch (ArgumentTypeException e) {
            // good, it's supposed to end here
        }
    }

    @Test
    public void testParseShortSwitches() throws ArgumentTypeException {
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("required", "r", true, true));
        parser.addSwitch(new SwitchArgument("boolean", "b", false, false));
        parser.addSwitch(new SwitchArgument("optional", "o", true, false));

        String[] args = new String[] {
                "-r", "required",
                "-b",
                "-o", "value",
        };

        ParsedArguments parsedArguments;
        try {
            parsedArguments = parser.parse(args);
        } catch (SwitchArgumentException e) {
            fail(String.format("failed parsing valid option case: %s", e.getMessage()));
            return;
        }

        assertTrue(parsedArguments.has("required"));
        assertEquals(parsedArguments.getString("required"), "required");
        assertTrue(parsedArguments.has("optional"));
        assertEquals(parsedArguments.getString("optional"), "value");
        assertFalse(parsedArguments.has("invalid"));
        assertTrue(parsedArguments.has("boolean"));
        assertTrue(parsedArguments.has("boolean"));
    }

    @Test(expected = SwitchArgumentException.class)
    public void testUnknownSwitches() throws SwitchArgumentException {
        final SwitchParser parser = new SwitchParser();
        parser.addSwitch(new SwitchArgument("option", "", true, false));

        String[] args = new String[] {
                "--unknown-option"
        };

        parser.parse(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingLongName() {
        new SwitchArgument("", "", false, false);
    }

    @Test
    public void testSwitchInstance_withoutShortName() {
        SwitchArgument opt = new SwitchArgument("name", true, false);
        assertFalse(opt.hasShortName());
    }

    @Test
    public void testSwitchInstance_withShortName() {
        SwitchArgument opt = new SwitchArgument("name", "s", true, false);
        assertTrue(opt.hasShortName());
    }

    @Test
    public void testSwitchInstance_withExplicitelyEmptyShortName() {
        SwitchArgument opt = new SwitchArgument("name", "", true, false);
        assertFalse(opt.hasShortName());
    }
}
