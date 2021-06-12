package application.junit;

import application.gameMechanics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class regexpTest {

    /**
     * min. 3 word characters
     * max. 10 word characters + optional 3 digits at end
     */
    @Test
    public void testValidNickname() {
        assertTrue(gameMechanics.isValidNickname("USERNAME"));
        assertTrue(gameMechanics.isValidNickname("ABC"));
        assertTrue(gameMechanics.isValidNickname("_CoolName_"));
        assertTrue(gameMechanics.isValidNickname("ABCDEFGHIJ123"));

        assertFalse(gameMechanics.isValidNickname("ABCDEFGHIJL"));
        assertFalse(gameMechanics.isValidNickname("ABCDEFGHIJ1234"));
        assertFalse(gameMechanics.isValidNickname("Bob Tim"));
        assertFalse(gameMechanics.isValidNickname("::XX::"));
        assertFalse(gameMechanics.isValidNickname("A"));
    }


    /**
     * min. 8 characters
     * min. 1 digit
     * min. 1 lower & upper letter
     * min. one special character (@, #, $, %, ^, &, +, =, .)
     */
    @Test
    public void testValidPassword() {
        assertTrue(gameMechanics.isValidPassword("mKiazw1."));
        assertTrue(gameMechanics.isValidPassword("aA2@oap34"));
        assertTrue(gameMechanics.isValidPassword("=AABcd1234"));
        assertTrue(gameMechanics.isValidPassword("aA1.aaaa"));

        assertFalse(gameMechanics.isValidPassword("ABCD"));
        assertFalse(gameMechanics.isValidPassword("Password"));
        assertFalse(gameMechanics.isValidPassword("Bob123"));
        assertFalse(gameMechanics.isValidPassword("IlikeCats123"));
    }
}