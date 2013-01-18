package com.zetapuppis.arguments;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LineUtilsTest.class,
                CommandLineParserTest.class,
                PositionalArgumentTest.class,
                PositionalParserTest.class,
                ParsedArgumentsTest.class,
                SwitchParsingTest.class })
public class AllTests {

}
