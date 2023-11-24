package main.java;

public class Hash {
    public static Long hash(String text, Long modulo){
        long result = 0L;
        for (int i = 0; i < text.length(); i++)
            result = (result + text.charAt(i)) % modulo;
        return result;
    }
}
