package main.java.exception;

import java.io.IOException;

public class FileNotValidException extends IOException {
    public FileNotValidException() {
        super("File can only consist of ASCII characters");
    }

    public FileNotValidException(String message){
        super(message);
    }
}
