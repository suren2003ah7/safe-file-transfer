package main.java;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static Long hash(String text, Long modulo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(text.getBytes());
            byte[] truncatedBytes = new byte[4];
            System.arraycopy(hashBytes, 0, truncatedBytes, 0, 4);
            long hashValue = bytesToLong(truncatedBytes);
            return hashValue % modulo;
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }

    private static long bytesToLong(byte[] bytes) {
        long result = 0;
        for (byte aByte : bytes) {
            result <<= 8;
            result |= (aByte & 0xFF);
        }
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }
        return hexStringBuilder.toString();
    }
}
