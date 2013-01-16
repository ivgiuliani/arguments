package com.zetapuppis.arguments.examples;

import com.zetapuppis.arguments.CmdLineException;
import com.zetapuppis.arguments.CommandLineParser;
import com.zetapuppis.arguments.ParsedArguments;

/**
 * Expected output:
 * <pre>
 *   mixed-style-example add --op1 5 --op2 3
 *   8
 *
 *   mixed-style-example sub --op1 5 --op2 3
 *   2
 *
 *   mixed-style-example add --op1 1 --op2 2 --easter-egg
 *   EASTER EGG!!!
 * </pre>
 */
public class MixedStyleExample {
    public static void main(String[] args) {
        final ParsedArguments parsed;
        String op;
        int op1, op2;
        boolean enableEasterEgg;

        try {
            parsed = CommandLineParser.from(args)
                    .addPositional("op", 1)
                    .addSwitch("op1", true, true)
                    .addSwitch("op2", true, true)
                    .addSwitch("easter-egg", false, false)
                    .parse();

            op = parsed.getChoice("op", new String[]{"add", "sub"});
            op1 = parsed.getInt("op1");
            op2 = parsed.getInt("op2");
            enableEasterEgg = parsed.has("easter-egg");
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            return;
        }

        if (enableEasterEgg) {
            System.out.println("EASTER EGG!!!");
            return;
        }

        if (op.equals("add")) {
            System.out.println(op1 + op2);
        } else if (op.equals("sub")) {
            System.out.println(op1 - op2);
        }
    }
}
