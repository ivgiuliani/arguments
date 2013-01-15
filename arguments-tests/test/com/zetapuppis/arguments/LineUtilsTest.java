package com.zetapuppis.arguments;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineUtilsTest {
    @Test
    public void testArgShift() {
        final String[] args = new String[] { "1", "2", "3" };
        assertArrayEquals(LineUtils.shiftArgs(args), new String[]{"2", "3"});
        assertArrayEquals(LineUtils.shiftArgs(args, 1), new String[] {"2", "3"});
        assertArrayEquals(LineUtils.shiftArgs(args, 2), new String[] {"3"});
        assertArrayEquals(LineUtils.shiftArgs(args, 3), new String[] {});
        assertArrayEquals(LineUtils.shiftArgs(args, 4), new String[] {});
    }
}
