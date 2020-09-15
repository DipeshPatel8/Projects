/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.utility;

public class PropertiesException extends Exception {

    public PropertiesException() {
        super();
    }

    public PropertiesException(String message) {
        super(message);
    }

    public PropertiesException(Exception e) {
        super(e);
    }
}
