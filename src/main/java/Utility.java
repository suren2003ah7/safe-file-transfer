package main.java;
import java.util.ArrayList;

public class Utility {
    public static Long generatePrime(){
        int n = 168;
        ArrayList<Long> list = firstNPrimes(n);
        int index = (int)(Math.random() * n) + 1;;
        return list.get(index);
    }

    public static Long findPrimitiveRoot(Long prime){
        return null;
    }

    public static void main(String[] args) {
        System.out.println(gcd(14, 24));
    }
    public static Long calculateInverse(Long n, Long modulo){
//        do the prime factorization
    return null;
    }

//    Method to compute GCD of two integers
    public static long gcd(long a, long b){
        if (b == 0) return a;
        return gcd(b, a % b);
    }

//    Utility to generate first n prime numbers
    private static ArrayList<Long> firstNPrimes(int n){
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
}
