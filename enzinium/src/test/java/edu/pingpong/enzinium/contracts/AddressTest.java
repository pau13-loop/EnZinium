package edu.pingpong.enzinium.contracts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class AddressTest {

    private Address mathew = new Address();
    private final double DELTA = 0.01;

    @Test 
    public void checkSkPresentTest() {
        mathew.generateKeyPair();
        assertTrue(mathew.checkSkPresent());
    }

    @Test
    public void checkSkNotPresentTest() {
        assertFalse(mathew.checkSkPresent());
    }

    @Test
    public void checkPkNotNullTest() {
        mathew.generateKeyPair();
        assertNotNull(mathew.getPK());
    }  
    
    @Test
    public void getBalanceTest() {
        mathew.addEZI(500);
        assertEquals(500 , mathew.getBalance(), DELTA);
    }

    @Test
    public void transferEZITest() {
        mathew.transferEZI(25d);
        assertEquals(25, mathew.getBalance(), DELTA);
    }
}
