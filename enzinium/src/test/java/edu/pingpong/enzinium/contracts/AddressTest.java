package edu.pingpong.enzinium.contracts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class AddressTest {

    private Address address = new Address();
    private double delta = 0.01;

    @Test 
    public void checkSkPresentTest() {
        address.generateKeyPair();
        assertTrue(address.checkSkPresent());
    }

    @Test
    public void checkSkNotPresentTest() {
        assertFalse(address.checkSkPresent());
    }

    @Test
    public void checkPkNotNullTest() {
        address.generateKeyPair();
        assertNotNull(address.getPK());
    }  
    
    @Test
    public void getBalanceTest() {
        address.addEZI(500);
        assertEquals(500 , address.getBalance(), delta);
    }

}
