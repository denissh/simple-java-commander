package ru.sdp.cli;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String message) {
        super(message);
    }
}
