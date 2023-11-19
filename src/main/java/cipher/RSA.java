package main.java.cipher;

public class RSA {
    private Long[] publicKey;
    private Long[] privateKey;

    public RSA(){
        // empty
    }

    public Long[] getPublicKey(){
        return publicKey;
    }

    public String encrypt(String plaintext, Long[] publicKey){
        return "";
    }

    public String decrypt(String ciphertext){
        return "";
    }

    private boolean isPublicKeyValid(){
        return true;
    }

    private boolean isMessageValid(){
        return true;
    }

    private Long power(Long a, Long b){
        return null;
    }

    private Long generatePrime(){
        return null;
    }

    private Long generateE(Long phiN){
        return null;
    }

    private Long generateD(Long phiN){
        return null;
    }
}
