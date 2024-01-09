package org.speedtyping.result.exceptions.classes;

public class ConnectException extends RuntimeException {
    public ConnectException() {
        super("can't connect to internal system");
    }
}
