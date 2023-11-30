package main.java.cipher;

import main.java.Utility;

public class RSA {
    private final Long[] publicKey;
    private final Long[] privateKey;

    public RSA(){
        this(Utility.generatePrime(), Utility.generatePrime());
    }

    public RSA(Long firstPrime, Long secondPrime){
        while (true){
            if (!arePrimesValid(firstPrime, secondPrime)){
                firstPrime = Utility.generatePrime();
                secondPrime = Utility.generatePrime();
                continue;
            }
            break;
        }
        publicKey = new Long[2];
        publicKey[1] = firstPrime * secondPrime;
        Long phiN = (firstPrime - 1) * (secondPrime - 1);
        publicKey[0] = generateE(phiN);
        privateKey = new Long[3];
        privateKey[1] = firstPrime;
        privateKey[2] = secondPrime;
        privateKey[0] = generateD(phiN);
    }

    public Long[] getPublicKey(){
        return publicKey;
    }

    public Long[] encrypt(String plaintext, Long[] publicKey){
        if (!Utility.isMessageValid(plaintext))
            throw new IllegalArgumentException("Message should only contain ASCII characters");
        Long[] domain = convertStringToLongArray(plaintext);
        for (int i = 0; i < domain.length; i++)
            domain[i] = Utility.power(domain[i], publicKey[0], publicKey[1]);
        return domain;
    }

    public String decrypt(Long[] cipher){
        StringBuilder sb = new StringBuilder();
        Long N = privateKey[1] * privateKey[2];
        for (Long aLong : cipher)
            sb.append((char) Utility.power(aLong, privateKey[0], N).intValue());
        return sb.toString();
    }

    private Long[] convertStringToLongArray(String text){
        Long[] result = new Long[text.length()];
        for (int i = 0; i < text.length(); i++)
            result[i] = (long) text.charAt(i);
        return result;
    }

    private Long generateE(Long phiN){
        while (true){
            Long E = (long) (Math.floor(Math.random() * (phiN - 2)) + 2);
            if (isEValid(E, phiN))
                return E;
        }
    }

    private boolean isEValid(Long E, Long phiN){
        return Utility.gcd(E, phiN) == 1;
    }

    private Long generateD(Long phiN){
        return Utility.calculateInverse(publicKey[0], phiN);
    }

    private boolean arePrimesValid(Long firstPrime, Long secondPrime){
        return firstPrime * secondPrime >= 128 && !firstPrime.equals(secondPrime);
    }
}
