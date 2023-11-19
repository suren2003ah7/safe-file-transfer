public class Hash {
    public static Long hash(String text, int modulo){
        long result = 0L;
        for (int i = 0; i < text.length(); i++)
            result += (int) text.charAt(i);
        return result % modulo;
    }
}
