package edu.pingpong.enzinium.contracts;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TokenContractTest {


    private Address jack = null;
    private Address rachel = null;
    private TokenContract token = null;
    private double delta = 0.01;

    @Before
    public void setupTokenContract() {
        jack = new Address();
        jack.generateKeyPair();
        rachel = new Address();
        rachel.generateKeyPair();


        token = new TokenContract(jack);

        token.addOwner(jack.getPK(), 50);
    }
    
    @Test
    public void gettersAndSettersTest() {

        token.setName("RuPaul");
        token.setSymbol("ASCII");
        token.setTotalSupply(69);
        token.setTokenPrice(55.5);

        assertEquals("RuPaul", token.getName());
        assertEquals("ASCII", token.symbol());
        assertEquals(69, token.totalSupply(), delta);
        assertEquals(55.5, token.getTokenPrice(), delta);
    }

    @Test
    public void toStringTest() {

        token.setName("RuPaul");
        token.setSymbol("ASCII");
        token.setTotalSupply(69);

        final String expected = "\nName: RuPaul\nSymbol: ASCII\nTotal supply: 69.0\nOwner Public Key: " + jack.getPK().hashCode();
        assertEquals(expected, token.toString());
    }

    @Test
    public void checkAddAndNumOwners() {
        assertEquals(1, token.numOwners());
    }

    @Test
    public void balanceOfTest() {
        assertEquals(50, token.balanceOf(jack.getPK()), delta);
        // We check for addresses that have never existed, the default case
        assertEquals(0d, token.balanceOf(rachel.getPK()), delta);
    }

    @Test
    public void firstTransferTest() {

        token.transfer(rachel.getPK(), 10.0);

        assertEquals(40, token.balanceOf(jack.getPK()), delta);
        assertEquals(10, token.balanceOf(rachel.getPK()), delta);
        assertEquals(2, token.getBalances().size());
    }

    @Test
    public void firstTransferFailTest() {

        token.transfer(rachel.getPK(), 60.0);

        assertEquals(50, token.balanceOf(jack.getPK()), delta);
        assertEquals(1, token.getBalances().size());
    }
}
