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
                (long) (Math.pow(root, privateKey) % prime),
                prime,
                root
        };
    }

    public Long[] sign(String text){
        Long[] S = new Long[2];
        Long hash = Hash.hash(text, publicKey[1]);
        Long K = generateK();
        S[0] = (long) (Math.pow(publicKey[2], K) % publicKey[1]);
        Long KInverse = Utility.calculateInverse(K, publicKey[1] - 1);
        S[1] = (KInverse * (hash - privateKey * S[0])) % (publicKey[1] - 1);
        return S;
    }

    public boolean verify(String text, Long[] senderPublicKey, Long[] S){
        if (!arePrimeAndRootValid(senderPublicKey))
            throw new IllegalArgumentException("Shared data is incorrect");
        Long hash = Hash.hash(text, senderPublicKey[1]);
        Long V1 = (long) (Math.pow(senderPublicKey[2], hash) % senderPublicKey[1]);
        Long V2 = (long) (
                ((Math.pow(senderPublicKey[0], S[0]) % senderPublicKey[1]) *
                        (Math.pow(S[0], S[1]) % senderPublicKey[1])) % senderPublicKey[1]
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
            Long K = (long) (Math.floor(Math.random() * (publicKey[1] - 1)) + 1);
            if (isKValid(K))
                return K;
        }
    }

    private boolean isKValid(Long K){
        return false;
    }
}
