package main.java.signatures;

import main.java.Hash;
import main.java.Utility;

public class DSA {
    private Long privateKey;

    private Long[] publicKey;

    public DSA(){
        long prime = 4L;
        long root = 3L;
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

    public boolean verify(String text, Long[] publicKey, Long[] S){
        return true;
    }

    private Long generateK(){
        return null;
    }

    private boolean validateK(){
        return false;
    }
}
