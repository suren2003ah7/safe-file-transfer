package signatures;

import main.java.signatures.DSA;
import main.java.Utility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DSATest {

    private static final Long PRIME = 10007L;

    private final DSA dsa = new DSA(PRIME);

    @Test
    void shouldSignAndVerifyMessage() {
        // given
        String message = "Test";
        Long[] publicKey = dsa.getPublicKey();

        // when
        Long[] signature = dsa.sign(message);

        // then
        assertEquals(signature.length, 2);
        assertTrue(dsa.verify(message, publicKey, signature));
    }

    @Test
    void shouldSignAndNotVerifyMessage() {
        // given
        String message = "Test";
        Long[] publicKey = dsa.getPublicKey();

        // when
        Long[] signature = dsa.sign(message);
        Long[] falseSignature = new Long[]{signature[0] - 1, signature[1] - 1};

        // then
        assertEquals(signature.length, 2);
        assertFalse(dsa.verify(message, publicKey, falseSignature));
    }

    @Test
    void shouldSignAndVerifyManyMessages(){
        // given
        String[] messages = new String[100];
        for (int i = 0; i < 100; i++)
            messages[i] = Utility.generateRandomMessage();
        Long[] publicKey = dsa.getPublicKey();
        Long[][] signatures = new Long[100][2];

        // when
        for (int i = 0; i < 100; i++)
            signatures[i] = dsa.sign(messages[i]);

        // then
        for (int i = 0; i < 100; i++)
            assertTrue(dsa.verify(messages[i], publicKey, signatures[i]));
    }
}