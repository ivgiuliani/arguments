package com.zetapuppis.arguments;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ParsedArgumentsTest {
    private ParsedArguments mParsedArguments;

    @Before
    public void setUp() {
        mParsedArguments = new ParsedArguments();
    }

    @Test
    public void testIteration() {
        mParsedArguments.set("item1", "value1");
        mParsedArguments.set("item2", "value2");
        mParsedArguments.set("item3", "value3");
        mParsedArguments.set("item4", "value4");
        mParsedArguments.set("item5", "value5");

        Set<String> keys = new HashSet<String>();
        Set<String> values = new HashSet<String>();

        for (Map.Entry<String, String> item : mParsedArguments) {
            keys.add(item.getKey());
            values.add(item.getValue());
        }

        assertEquals(keys.size(), 5);
        assertEquals(values.size(), 5);

        assertTrue(keys.contains("item1"));
        assertTrue(keys.contains("item2"));
        assertTrue(keys.contains("item3"));
        assertTrue(keys.contains("item4"));
        assertTrue(keys.contains("item5"));

        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
        assertTrue(values.contains("value3"));
        assertTrue(values.contains("value4"));
        assertTrue(values.contains("value5"));
    }

    @Test
    public void testGetString() throws ArgumentTypeException {
        mParsedArguments.set("string", "value");
        assertEquals(mParsedArguments.getString("string"), "value");
    }

    @Test
    public void testGetInt() {
        mParsedArguments.set("integer", "123");
        try {
            assertEquals(mParsedArguments.getInt("integer"), 123);

            // every type must always be convertible to string
            assertEquals(mParsedArguments.getString("integer"), "123");
        } catch (ArgumentTypeException e) {
            fail("cannot get valid string value");
        }

        mParsedArguments.set("integer", "not-an-integer");
        try {
            mParsedArguments.getInt("integer");
            fail("parsed an invalid integer");
        } catch (ArgumentTypeException e) {
            // everything's fine
        }
    }

    @Test
    public void testGetOptional() {
        mParsedArguments.set("exists");
        assertTrue(mParsedArguments.has("exists"));
        assertFalse(mParsedArguments.has("not-exists"));
    }

    @Test
    public void testGetChoices() {
        mParsedArguments.set("set1", "allowed1");
        mParsedArguments.set("set2", "allowed2");
        mParsedArguments.set("set3", "allowed3");

        final String[] choices = new String[] {
                "allowed1", "allowed2"
        };

        try {
            assertEquals(mParsedArguments.getChoice("set1", choices), "allowed1");
            assertEquals(mParsedArguments.getChoice("set2", choices), "allowed2");
        } catch (ArgumentTypeException e) {
            fail("failed parsing a valid option set");
        }

        try {
            mParsedArguments.getChoice("set3", choices);
            fail("choice was not allowed");
        } catch (ArgumentTypeException e) {
            // everything ok
        }
    }

    @Test
    public void testRevertToDefault() throws ArgumentTypeException {
        assertEquals(mParsedArguments.getString("string", "defaultString"), "defaultString");
        assertEquals(mParsedArguments.getInt("integer", 1812), 1812);
        assertEquals(mParsedArguments.getChoice("choice-string", new String[] {
                "allowed1", "allowed2", "allowed3"
        }, "allowed-default"), "allowed-default");
        assertEquals(mParsedArguments.getFile("file", new File("/path/to/file")),
                new File("/path/to/file"));
    }
}
