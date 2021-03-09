package edu.pingpong.enzinium.contracts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AddressTest {

    private Address mathew = new Address();
    private TokenContract contract = null;
    private final double DELTA = 0.01;

    @Before
    public void setupAddress() {
        mathew.generateKeyPair();
        contract = new TokenContract(mathew);
    }

    @Test
    public void checkSkPresentTest() {
        assertTrue(mathew.checkSkPresent());
    }

    @Test
    public void checkSkNotPresentTest() {
        Address rachel = new Address();
        assertFalse(rachel.checkSkPresent());
    }

    @Test
    public void checkPkNotNullTest() {
        assertNotNull(mathew.getPK());
    }

    @Test
    public void getBalanceTest() {
        mathew.addEZI(500);
        assertEquals(500, mathew.getBalance(), DELTA);
    }

    @Test
    public void toStringTest() {
        String expected = "\nPublic key: " + mathew.getPK().hashCode() + "\nBalance: 0.0 EZI\n";
        assertEquals(expected, mathew.toString());
    }

    @Test
    public void transferEZITest() {
        mathew.transferEZI(25d);
        assertEquals(25, mathew.getBalance(), DELTA);
    }

    /**
     *     @Test
    public void sendTest() {
        mathew.addEZI(20);
        mathew.send(contract, 10);
        assertEquals(10, mathew.getBalance(), DELTA);
    }
     */
}
