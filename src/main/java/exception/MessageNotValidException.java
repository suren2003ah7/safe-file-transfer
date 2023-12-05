package main.java.exception;

public class MessageNotValidException extends IllegalArgumentException{
    public MessageNotValidException(){
        super("Message should only contain ASCII characters");
    }

    public MessageNotValidException(String message){
        super(message);
    }

}
