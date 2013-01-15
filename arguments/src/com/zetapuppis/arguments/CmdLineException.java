package com.zetapuppis.arguments;

/**
 * Generic exception raised by command line handler like
 * wrong parameter type
 */
public class CmdLineException extends Exception {
    public CmdLineException(final String message) {
        super(message);
    }
}
