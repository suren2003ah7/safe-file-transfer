package main.java;

import main.java.cipher.RSA;
import main.java.signatures.DSA;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String INTRO_TEXT = "Hello and welcome to safe file transfer! Please choose a method for " +
            "generating public and private keys! Write your desired message in the message_to_be_sent.txt file located in " +
            "resources folder. You can only use ASCII characters for your messages!";

    private static final String INVALID_INPUT = "Invalid input!";

    private static final Scanner sc = new Scanner(System.in);

    private static final String MESSAGE_TO_BE_SENT_PATH = "src/main/resources/message_to_be_sent.txt";

    private static final String CIPHERTEXT_TO_BE_SENT_PATH = "src/main/resources/ciphertext_to_be_sent.txt";

    private static final String RECEIVED_CIPHERTEXT_PATH = "src/main/resources/received_ciphertext.txt";

    private static final String RECEIVED_MESSAGE_PATH = "src/main/resources/received_message.txt";

    private static final String SIGNATURE_TO_BE_SENT_PATH = "src/main/resources/signature_to_be_sent.txt";

    private static final String SIGNATURE_CIPHER_TO_BE_SENT_PATH = "src/main/resources/signature_cipher_to_be_sent.txt";

    private static final String RECEIVED_CIPHER_SIGNATURE_PATH = "src/main/resources/received_cipher_signature.txt";

    private static final String RECEIVED_SIGNATURE_PATH = "src/main/resources/received_signature.txt";
    private static RSA rsa;

    private static DSA dsa;

    public static void main(String[] args) throws IOException {
        System.out.println(INTRO_TEXT);
        while (true){
            System.out.println("1: Generate keys automatically.");
            System.out.println("2: Manually provide according input values.");
            int init = sc.nextInt();
            generateRSAAndDSA(init);
            if (rsa == null || dsa == null)
                continue;
            break;
        }
        main:
        while (true){
            System.out.println("1: Encrypt only.");
            System.out.println("2: Sign only.");
            System.out.println("3: Sign and encrypt.");
            System.out.println("4: Decrypt only.");
            System.out.println("5: Verify only.");
            System.out.println("6: Decrypt and verify.");
            System.out.println("7: Get RSA public key.");
            System.out.println("8: Get DSA public key.");
            System.out.println("9: Quit.");
            int init = sc.nextInt();
            String plaintext = FileManager.readFile(MESSAGE_TO_BE_SENT_PATH);
            String ciphertext = FileManager.readFile(RECEIVED_CIPHERTEXT_PATH);
            Long[] cipher = Utility.stringToLongArray(ciphertext);
            String receivedMessage = FileManager.readFile(RECEIVED_MESSAGE_PATH);
            String receivedSignatureText = FileManager.readFile(RECEIVED_SIGNATURE_PATH);
            Long[] receivedSignature = Utility.stringToLongArray(receivedSignatureText);
            String receivedCipherSignatureText = FileManager.readFile(RECEIVED_CIPHER_SIGNATURE_PATH);
            Long[] receivedCipherSignature = Utility.stringToLongArray(receivedCipherSignatureText);
            switch (init){
                case 1:
                    Long[] receiverPublicKey = new Long[2];
                    System.out.println("Insert the first parameter (e) of the receiver's public key.");
                    receiverPublicKey[0] = sc.nextLong();
                    System.out.println("Insert the second parameter (N) of the receiver's public key.");
                    receiverPublicKey[1] = sc.nextLong();
                    Long[] onlyEncryption = encrypt(plaintext, receiverPublicKey);
                    String onlyEncryptionText = Utility.longArrayToString(onlyEncryption);
                    System.out.println(onlyEncryptionText);
                    FileManager.writeToFile(CIPHERTEXT_TO_BE_SENT_PATH, onlyEncryptionText);
                    break;
                case 2:
                    Long[] onlySignature = sign(plaintext);
                    String onlySignatureText = Utility.longArrayToString(onlySignature);
                    System.out.println(onlySignatureText);
                    FileManager.writeToFile(SIGNATURE_TO_BE_SENT_PATH, onlySignatureText);
                    break;
                case 3:
                    Long[] signature = sign(plaintext);
                    Long[] receiverPublicKeyForBoth = new Long[2];
                    System.out.println("Insert the first parameter (e) of the receiver's public key.");
                    receiverPublicKeyForBoth[0] = sc.nextLong();
                    System.out.println("Insert the second parameter (N) of the receiver's public key.");
                    receiverPublicKeyForBoth[1] = sc.nextLong();
                    Long[] plaintextEncryption = encrypt(plaintext, receiverPublicKeyForBoth);
                    Long[] signatureEncryption = encrypt(signature, receiverPublicKeyForBoth);
                    String plaintextEncryptionText = Utility.longArrayToString(plaintextEncryption);
                    String signatureEncryptionText = Utility.longArrayToString(signatureEncryption);
                    System.out.println("Plaintext encryption");
                    System.out.println(plaintextEncryptionText);
                    System.out.println("Signature encryption");
                    System.out.println(signatureEncryptionText);
                    FileManager.writeToFile(CIPHERTEXT_TO_BE_SENT_PATH, plaintextEncryptionText);
                    FileManager.writeToFile(SIGNATURE_CIPHER_TO_BE_SENT_PATH, signatureEncryptionText);
                    break;
                case 4:
                    String decrypted = decrypt(cipher);
                    System.out.println(decrypted);
                    FileManager.writeToFile(RECEIVED_MESSAGE_PATH, decrypted);
                    break;
                case 5:
                    System.out.println(verify(receivedMessage, receivedSignature));
                    break;
                case 6:
                    String message = decrypt(cipher);
                    System.out.println("Decrypted message: " + message);
                    Long[] decryptedSignature = decryptSignature(receivedCipherSignature);
                    String decryptedSignatureText = Utility.longArrayToString(decryptedSignature);
                    System.out.println(verify(message, decryptedSignature));
                    FileManager.writeToFile(RECEIVED_MESSAGE_PATH, message);
                    FileManager.writeToFile(RECEIVED_SIGNATURE_PATH, decryptedSignatureText);
                    break;
                case 7:
                    Long[] rsaPublicKey = rsa.getPublicKey();
                    printLongArray(rsaPublicKey);
                    break;
                case 8:
                    Long[] dsaPublicKey = dsa.getPublicKey();
                    printLongArray(dsaPublicKey);
                    break;
                case 9:
                    break main;
                default:
                    System.out.println(INVALID_INPUT);
            }
        }
    }

    private static void generateRSAAndDSA(int choice){
        switch (choice){
            case 1:
                rsa = new RSA();
                dsa = new DSA();
                break;
            case 2:
                System.out.println("Insert first prime for RSA");
                Long p1 = sc.nextLong();
                System.out.println("Insert second prime for RSA");
                Long p2 = sc.nextLong();
                rsa = new RSA(p1, p2);
                System.out.println("Insert prime for DSA");
                Long p = sc.nextLong();
                dsa = new DSA(p);
                break;
            default:
                System.out.println(INVALID_INPUT);
        }
    }

    private static void printLongArray(Long[] arr){
        for (Long aLong : arr)
            System.out.println(aLong.toString());
    }

    private static Long[] encrypt(String plaintext, Long[] receiverPublicKey){
        return rsa.encrypt(plaintext, receiverPublicKey);
    }

    private static Long[] encrypt(Long[] signature, Long[] receiverPublicKey){
        return rsa.encrypt(signature, receiverPublicKey);
    }

    private static String decrypt(Long[] cipher){
        return rsa.decrypt(cipher);
    }

    private static Long[] decryptSignature(Long[] cipher){
        return rsa.decryptSignature(cipher);
    }

    private static Long[] sign(String plaintext){
        return dsa.sign(plaintext);
    }

    private static boolean verify(String message, Long[] signature) {
        Long[] senderPublicKey = new Long[3];
        System.out.println("Insert the first parameter (Ya) of the sender's public key.");
        senderPublicKey[0] = sc.nextLong();
        System.out.println("Insert the second parameter (prime) of the sender's public key.");
        senderPublicKey[1] = sc.nextLong();
        System.out.println("Insert the third parameter (root) of the sender's public key.");
        senderPublicKey[2] = sc.nextLong();
        return dsa.verify(message, senderPublicKey, signature);
    }
}
