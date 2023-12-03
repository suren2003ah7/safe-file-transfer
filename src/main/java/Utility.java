package main.java;
import main.java.exception.FieldNotPrimeException;
import main.java.exception.NoPrimitiveRootException;

import java.util.ArrayList;

public class Utility {
    public static Long generatePrime(){
        int n = 168;
        ArrayList<Long> list = firstNPrimes(n);
        int index = (int)(Math.random() * n) + 1;;
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
        throw new NoPrimitiveRootException();
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

//    Utility to generate first n prime numbers
    private static ArrayList<Long> firstNPrimes(long n){
        ArrayList<Long> primes = new ArrayList<>();
        for (long i = 1; i < 1000; i ++){
//            if the number is 2 or it is not 1 and it isn't even
            if(i == 2 || (i != 1 && i % 2 == 1)) {
//                if the number is to then add to the list
                if(i == 2) {
                    primes.add(i);
                }
//                otherwise if the number is not 2
                else {
                    if (primes.size() == n) break;
//                    loop over all numbers up to square root of i until finding a divisor
//                    if reaches square root and doesn't find a divisor add the number to the list
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
/*
//    find prime factors of number n
    private static ArrayList<Long> primeFactors(long n){
        ArrayList<Long> list = firstNPrimes(n);
        for(int i = 0; i < list.size(); i ++){
            if((long)n % list.get(i) != 0) list.remove(i--);
        }
        return list;
    }
 */
}
