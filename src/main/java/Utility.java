package main.java;

import main.java.exception.FieldNotPrimeException;

import java.util.ArrayList;

public class Utility {
    public static Long generatePrime(){
        int n = 168;
        ArrayList<Long> list = firstNPrimes(n);
        int index = (int) Math.floor(Math.random() * n);
        return list.get(index);
    }

    public static Long findPrimitiveRoot(Long prime){
        if (!isPrime(prime)) throw new FieldNotPrimeException(prime + " is not a prime number");
        boolean fl = false;
        for (long i = 2; i <= prime - 1; i++){
            for (long exponent = 2; exponent < prime; exponent++){
                if (power(i, exponent, prime) == 1) {
                    fl = true;
                    break;
                }
            }
            if (fl) return i;
        }
        throw new IllegalArgumentException("Primitive root cannot be found");
    }

    public static Long calculateInverse(Long n, Long modulo){
        for (long i = 1; i < modulo; i++){
            if (multiply(n, i, modulo) == 1)
                return i;
        }
        return null;
    }

    public static Long gcd(Long a, Long b){
        if (b == 0L) return a;
        return gcd(b, a % b);
    }

    public static Long power(Long base, Long exponent, Long modulo){
        if (exponent == 0) return 1L;
        Long result = base;
        for (int i = 1; i < exponent; i++)
            result = multiply(result, base, modulo);
        return result;
    }

    public static Long multiply(Long firstFactor, Long secondFactor, Long modulo){
        if (firstFactor == 0 || secondFactor == 0) return 0L;
        Long result = firstFactor;
        for (int i = 1; i < secondFactor; i++)
            result = (result + firstFactor) % modulo;
        return result;
    }

    public static Long subtract(Long a, Long b, Long modulo){
        Long result = a - b;
        while (true){
            if (result < 0) {
                result += modulo;
                continue;
            }
            break;
        }
        return result;
    }

    public static String generateRandomMessage(){
        StringBuilder sb = new StringBuilder();
        int length = (int) (Math.floor(Math.random() * 10) + 1);
        for (int i = 0; i < length; i++){
            char randomCharacter = (char) Math.floor(Math.random() * 128);
            sb.append(randomCharacter);
        }
        return sb.toString();
    }

    public static boolean isMessageValid(String message) {
        for (int i = 0; i < message.length(); i++)
            if (message.charAt(i) >= 128)
                return false;
        return true;
    }

    public static Long[] stringToLongArray(String str){
        ArrayList<Long> arrayList = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ' '){
                arrayList.add(Long.parseLong(str.substring(j, i)));
                j = i + 1;
            }
            else if (i == str.length() - 1)
                arrayList.add(Long.parseLong(str.substring(j)));
        }
        Long[] result = new Long[arrayList.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = arrayList.get(i);
        return result;
    }

    public static String longArrayToString(Long[] arr){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++){
            sb.append(arr[i].toString()).append(" ");
        }
        sb.append(arr[arr.length - 1].toString());
        return sb.toString();
    }

    private static ArrayList<Long> firstNPrimes(long n){
        ArrayList<Long> primes = new ArrayList<>();
        for (long i = 1; i < 1000; i ++){
            if(i == 2 || (i != 1 && i % 2 == 1)) {
                if(i == 2) {
                    primes.add(i);
                }
                else {
                    if (primes.size() == n) break;
                    for(int j = 1; j <= (int) Math.sqrt(i); j ++){
                        if(j > 1 && i % j == 0){
                            break;
                        }
                        else if (j >= (int) Math.sqrt(i)) primes.add(i);
                    }
                }
            }
        }
        return primes;
    }

    private static boolean isPrime(long n){
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for(long i = 5; i  <= Math.sqrt(n); i += 5) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
