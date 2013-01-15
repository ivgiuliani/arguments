package com.zetapuppis.arguments;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PositionalArgumentTest {
    @Test
    public void testKeywordOrdering() {
        List<PositionalArgument> positionalArguments = new ArrayList<PositionalArgument>();
        positionalArguments.add(new PositionalArgument("kw1", 1));
        positionalArguments.add(new PositionalArgument("kw2", 2));
        positionalArguments.add(new PositionalArgument("kw4", 4));
        positionalArguments.add(new PositionalArgument("kw3", 3));

        Collections.sort(positionalArguments);

        assertEquals(positionalArguments.get(0).getName(), "kw1");
        assertEquals(positionalArguments.get(1).getName(), "kw2");
        assertEquals(positionalArguments.get(2).getName(), "kw3");
        assertEquals(positionalArguments.get(3).getName(), "kw4");
    }
}
