package com.zetapuppis.arguments.examples;

import com.zetapuppis.arguments.CmdLineException;
import com.zetapuppis.arguments.CommandLineParser;
import com.zetapuppis.arguments.ParsedArguments;

/**
 * Expected output:
 * <pre>
 *   switch-arguments-example --name John --surname Doe
 *   John Doe
 *
 *   switch-arguments-example --name John --surname Doe --nickname White
 *   John "White" Doe
 *
 *   switch-arguments-example --name John --decorate --surname Doe --nickname White
 *   _-* John "White" Doe *-_
 *
 *   switch-arguments-example --name John --decorate --surname Doe
 *   _-* John Doe *-_
 * </pre>
 */
public class SwitchArgumentsExample {
    private final static String DECORATION_LEFT = "_-* ";
    private final static String DECORATION_RIGHT = " *-_";

    public static void main(String[] args) {
        final ParsedArguments parsed;
        String name, surname, nickname;
        boolean decorate;

        try {
            parsed = CommandLineParser.from(args)
                    .addSwitch("name", true, true)
                    .addSwitch("surname", true, true)
                    .addSwitch("nickname", true, false)
                    .addSwitch("decorate", false, false)
                    .parse();

            name = parsed.getString("name");
            surname = parsed.getString("surname");
            nickname = parsed.getString("nickname", "");
            decorate = parsed.has("decorate");
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            return;
        }

        StringBuilder builder = new StringBuilder(name);
        builder.append(' ');
        if (!nickname.isEmpty()) {
            builder.append('"');
            builder.append(nickname);
            builder.append('"');
            builder.append(' ');
        }
        builder.append(surname);

        if (decorate) {
            builder.insert(0, DECORATION_LEFT);
            builder.append(DECORATION_RIGHT);
        }

        System.out.println(builder.toString());
    }
}
