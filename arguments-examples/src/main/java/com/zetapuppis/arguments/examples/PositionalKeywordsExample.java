package com.zetapuppis.arguments.examples;

import com.zetapuppis.arguments.*;

/**
 * Expected output:
 * <pre>
 *   positional-keywords-example 2 * 3
 *   6
 *
 *   positional-keywords-example 2 + 3
 *   5
 *
 *   positional-keywords-example 2 - 3
 *   -1
 *
 *   positional-keywords-example 6 / 2
 *   3
 * </pre>
 */
public class PositionalKeywordsExample {
    public static void main(String[] args) {
        final ParsedArguments parsed;
        int operand1, operand2;
        char operator;

        try {
            parsed = CommandLineParser.from(args)
                    .addPositional("operand1", 1)
                    .addPositional("operand2", 3)
                    .addPositional("operator", 2)
                    .parse();

            operand1 = parsed.getInt("operand1");
            operand2 = parsed.getInt("operand2");
            operator = parsed.getCharChoice("operator", new char[] {
                    '+', '-', '*', '/'
            });
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            return;
        }

        switch(operator) {
            case '+':
                System.out.println(operand1 + operand2);
                break;
            case '-':
                System.out.println(operand1 - operand2);
                break;
            case '*':
                System.out.println(operand1 * operand2);
                break;
            case '/':
                System.out.println(operand1 / operand2);
                break;
        }
    }
}
