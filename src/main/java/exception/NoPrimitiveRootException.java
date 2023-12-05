package main.java.exception;

public class NoPrimitiveRootException extends IllegalArgumentException{
    public NoPrimitiveRootException(){
        super("Primitive root cannot be found");
    }

    public NoPrimitiveRootException(String message){
        super(message);
    }

}
