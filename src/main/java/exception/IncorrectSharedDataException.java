package main.java.exception;

public class IncorrectSharedDataException extends IllegalArgumentException {
    public IncorrectSharedDataException(){
        super("Shared data is incorrect");
    }

    public IncorrectSharedDataException(String message){
        super(message);
    }
}

