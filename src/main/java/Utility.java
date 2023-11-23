package main.java;
import java.util.ArrayList;

public class Utility {
    public static Long generatePrime(){
        int n = 168;
        ArrayList<Long> list = firstNPrimes(n);
        int index = (int)(Math.random() * n) + 1;;
        return list.get(index);
    }

    public static Long findPrimitiveRoot(long prime){
        if (!isPrime(prime)) throw new IllegalArgumentException(prime + " is not a prime number");

        int phi = (int)(prime - 1);
        ArrayList<Long> primeFactors = primeFactors(phi);

        for(int i = 2; i <= phi; i ++){
            boolean fl = false;
            for (Long num : primeFactors){
                if(power(i, (int) (phi / num), (int)prime) == 1)
                {
                    fl = true;
                    break;
                }
            }

            if (!fl) return (long) i;

        }

        throw new IllegalArgumentException("Primitive root cannot be found");
    }

    public static Long calculateInverse(Long n, Long modulo){

//    using Extended Euclid's algorithm

        Long m0 = modulo;
        long y = 0, x = 1;

        if (modulo == 1) return (long) 0;

        while (n > 1) {
            // q is quotient
            long q = n / modulo, t = modulo;

            // m is remainder now, continue using Euclid's algorithm
            modulo = n % modulo;
            n = t;
            t = y;

            // Update x and y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0) x += m0;

        return x;
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

//    method to check if given number is prime
    private static boolean isPrime(long n){
        if (n <= 1) return false;
        if (n <= 3) return true;

//        check for divisibility on 2 and 3 to skip some steps
        if (n % 2 == 0 || n % 3 == 0) return false;

        for(long i = 5; i  <= Math.sqrt(n); i += 5){
            if (n % i == 0) return false;
        }

        return true;
    }

// Modular exponentiation
    private static int power(int a, int pow, int mod) {

        int res = 1; // Initialize result

        //Update a if it is more than or
        // equal to mod
        a = a % mod;

        while (pow > 0) {
            // If pow is odd, multiply a with result
            if ((pow & 1) == 1)
                res = (res * a) % mod;

            // pow should be even now
            pow = pow >> 1; // pow = pow/2
            a = (a * a) % mod;
        }
        return res;
    }

//    find prime factors of number n
    private static ArrayList<Long> primeFactors(int n){
        ArrayList<Long> list = firstNPrimes(n);
        for(int i = 0; i < list.size(); i ++){
            if((long)n % list.get(i) != 0) list.remove(i--);
        }
        return list;
    }
}
