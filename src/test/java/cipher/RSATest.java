package cipher;

import main.java.Utility;
import main.java.cipher.RSA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RSATest {
    private static final Long[] PRIMES = new Long[]{997L, 541L};

    private static final RSA rsa = new RSA(PRIMES[0], PRIMES[1]);

    @Test
    void shouldSuccessEncryptionAndDecryptionOfCharacter(){
        // given
        String message = "X";

        // when
        Long[] encryption = rsa.encrypt(message, rsa.getPublicKey());
        String decryption = rsa.decrypt(encryption);

        // then
        assertEquals(encryption.length, 1);
        assertEquals(encryption[0], Utility.power(
                (long) message.charAt(0),
                rsa.getPublicKey()[0],
                rsa.getPublicKey()[1]));
        assertEquals(decryption.length(), 1);
        assertEquals(decryption, message);
    }

    @Test
    void shouldSuccessEncryptionAndDecryptionOfText(){
        // given
        String message = "Test";

        // when
        Long[] encryption = rsa.encrypt(message, rsa.getPublicKey());
        String decryption = rsa.decrypt(encryption);

        // then
        assertEquals(encryption.length, 4);
        for (int i = 0; i < encryption.length; i++)
            assertEquals(encryption[i], Utility.power(
                    (long) message.charAt(i),
                    rsa.getPublicKey()[0],
                    rsa.getPublicKey()[1]
            ));
        assertEquals(decryption.length(), 4);
        assertEquals(decryption, message);
    }

    @Test
    void shouldSuccessEncryptionAndDecryptionOfManyTexts(){
        // given
        String[] messages = new String[100];
        for (int i = 0; i < messages.length; i++)
            messages[i] = Utility.generateRandomMessage();
        Long[][] encryptions = new Long[100][];
        String[] decryptions = new String[100];

        // when
        for (int i = 0; i < messages.length; i++){
            encryptions[i] = rsa.encrypt(messages[i], rsa.getPublicKey());
            decryptions[i] = rsa.decrypt(encryptions[i]);
        }

        // then
        assertArrayEquals(decryptions, messages);
    }
}
