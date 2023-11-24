package main.java.signatures;

import main.java.Hash;
import main.java.Utility;

import java.util.Objects;

public class DSA {
    private Long privateKey;

    private Long[] publicKey;

    public DSA(){
        this(Utility.generatePrime());
    }

    public DSA(Long prime){
        Long root = Utility.findPrimitiveRoot(prime);
        privateKey = (long) Math.floor(Math.random() * (prime - 3)) + 2;
        publicKey = new Long[]{
                Utility.power(root, privateKey, prime),
                prime,
                root
        };
    }

    public Long[] sign(String text){
        Long[] signature = new Long[2];
        Long hash = Hash.hash(text, publicKey[1]);
        Long K = generateK();
        signature[0] = Utility.power(publicKey[2], K, publicKey[1]);
        Long KInverse = Utility.calculateInverse(K, publicKey[1] - 1);
        signature[1] = calculateS2(KInverse, signature[0], hash);
        return signature;
    }

    public boolean verify(String text, Long[] senderPublicKey, Long[] signature){
        if (!arePrimeAndRootValid(senderPublicKey))
            throw new IllegalArgumentException("Shared data is incorrect");
        Long hash = Hash.hash(text, senderPublicKey[1]);
        Long V1 = Utility.power(senderPublicKey[2], hash, senderPublicKey[1]);
        Long V2 = Utility.multiply(
                Utility.power(senderPublicKey[0], signature[0], senderPublicKey[1]),
                Utility.power(signature[0], signature[1], senderPublicKey[1]),
                senderPublicKey[1]
        );
        return Objects.equals(V1, V2);
    }

    public Long[] getPublicKey(){
        return publicKey;
    }

    private boolean arePrimeAndRootValid(Long[] senderPublicKey){
        return Objects.equals(senderPublicKey[1], publicKey[1]) && Objects.equals(senderPublicKey[2], publicKey[2]);
    }

    private Long generateK(){
        while (true){
            Long K = (long) (Math.floor(Math.random() * (publicKey[1] - 2)) + 1);
            if (isKValid(K))
                return K;
        }
    }

    private boolean isKValid(Long K){
        return Utility.gcd(K, publicKey[1] - 1) == 1 && Utility.calculateInverse(K, publicKey[1] - 1) != null;
    }

    private Long calculateS2(Long KInverse, Long signature1, Long hash){
        Long temp = Utility.multiply(privateKey, signature1, publicKey[1] - 1);
        Long factor = Utility.subtract(hash, temp, publicKey[1] - 1);
        return Utility.multiply(
                KInverse,
                factor,
                publicKey[1] - 1);
    }
}
