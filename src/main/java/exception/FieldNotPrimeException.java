package main.java.exception;

public class FieldNotPrimeException extends IllegalArgumentException{
    public FieldNotPrimeException(){
        super("Field is not Prime");
    }

    public FieldNotPrimeException(String message){
        super(message);
    }
}
