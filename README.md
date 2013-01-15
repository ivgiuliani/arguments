Arguments
=========

Java command line argument parsing made easy.

Usage
-----

### Positional keywords-based arguments
```java
import com.zetapuppis.arguments.*;
public static void main(String[] args) {
    final ParsedArguments parsed;

    try {
        parsed = CommandLineParser.from(args)
                .addPositional("input", 1)
                .addPositional("output", 2)
                .parse()

        File input = parsed.getExistingFile("input");
        File output = parsed.getNewFile("output");
    } catch (CmdLineException ex) {
        System.err.println(ex.getMessage());
        return;
    }
}
```

    $ java -jar app.jar input.txt output.txt

### Switch-based arguments
```java
import com.zetapuppis.arguments.*;
public static void main(String[] args) {
    final ParsedArguments parsed;

    try {
        parsed = CommandLineParser.from(args)
                .addPositional("input", 1)
                .addPositional("output", 2)
                .addSwitch("boolean", false, false)
                .addSwitch("valued-optional", true, false)
                .parse()

        File input = parsed.getExistingFile("input");
        File output = parsed.getNewFile("output");
        boolean hasBoolean = parsed.has("boolean");
        String booleanWithValue = parsed.getString("valued-optional", "default");
    } catch (CmdLineException ex) {
        System.err.println(ex.getMessage());
        return;
    }
}
```

    $ java -jar app.jar input.txt output.txt --valued-optional "value"

### Mixed-style arguments
```java
import com.zetapuppis.arguments.*;
public static void main(String[] args) {
    final ParsedArguments parsed;

    try {
        parsed = CommandLineParser.from(args)
                .addSwitch("input", true, true)
                .addSwitch("output", true, true)
                .addSwitch("boolean", false, false)
                .addSwitch("valued-optional", true, false)
                .parse()

        File input = parsed.getExistingFile("input");
        File output = parsed.getNewFile("output");
        boolean hasBoolean = parsed.has("boolean");
        String booleanWithValue = parsed.getString("valued-optional", "default");
    } catch (CmdLineException ex) {
        System.err.println(ex.getMessage());
        return;
    }
}
```

    $ java -jar app.jar --input input.txt --output output.txt --valued-optional "value"

Installation
------------

### Maven
TODO

Contributing
------------
1. Fork it
2. Create a branch (`git checkout -b my_branch`)
3. Commit your changes (`git commit -am "Fixed bug"`)
4. Push to the brach (`git push origin my_branch`)
5. Open a pull request
6. Wait for review
