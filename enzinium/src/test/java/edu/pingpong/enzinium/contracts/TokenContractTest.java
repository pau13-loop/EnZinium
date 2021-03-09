package edu.pingpong.enzinium.contracts;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TokenContractTest {


    private Address jack = null;
    private Address rachel = null;
    private TokenContract token = null;
    private final double DELTA = 0.01;

    @Before
    public void setupTokenContract() {
        jack = new Address();
        jack.generateKeyPair();
        rachel = new Address();
        rachel.generateKeyPair();

        token = new TokenContract(jack);
        token.setTokenPrice(5d);

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
        assertEquals(69, token.totalSupply(), DELTA);
        assertEquals(55.5, token.getTokenPrice(), DELTA);
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
        assertEquals(50, token.balanceOf(jack.getPK()), DELTA);
        // We check for addresses that have never existed, the default case
        assertEquals(0d, token.balanceOf(rachel.getPK()), DELTA);
    }

    @Test
    public void transferTest() {

        token.transfer(rachel.getPK(), 5d);

        assertEquals(45, token.balanceOf(jack.getPK()), DELTA);
        assertEquals(5, token.balanceOf(rachel.getPK()), DELTA);
        assertEquals(2, token.getBalances().size());

        //! Test cases for the second transfer method

        Address michael = new Address();
        michael.generateKeyPair();
        token.transfer(rachel.getPK(), michael.getPK(), 3d);

        assertEquals(2, token.balanceOf(rachel.getPK()), DELTA);
        assertEquals(3, token.balanceOf(michael.getPK()), DELTA);
        assertEquals(3, token.getBalances().size());
    }

    @Test
    public void transferFailTest() {

        token.transfer(rachel.getPK(), 60d);

        assertEquals(50, token.balanceOf(jack.getPK()), DELTA);
        assertEquals(1, token.getBalances().size());

        //! Test cases for the second transfer method

        token.transfer(rachel.getPK(), 5d);
        Address michael = new Address();
        michael.generateKeyPair();
        token.transfer(rachel.getPK(), michael.getPK(), 10d);

        assertEquals(5, token.balanceOf(rachel.getPK()), DELTA);
        assertEquals(2, token.getBalances().size());
    }

    @Test
    public void totalTokensSoldTest() {
        Address michael = new Address();
        michael.generateKeyPair();
        Address greta = new Address();
        greta.generateKeyPair();
        token.transfer(rachel.getPK(), 5d);
        token.transfer(rachel.getPK(), michael.getPK(), 3d);
        token.transfer(greta.getPK(), 3d);

        assertEquals(8, token.totalTokensSold());
    }

    @Test
    public void payableTest() {
        rachel.transferEZI(30d);
        token.payable(rachel.getPK(), rachel.getBalance());

        assertEquals(6, token.balanceOf(rachel.getPK()), DELTA);
        assertEquals(30, token.owner().getBalance(), DELTA);

        // not enough enziniums
        token.payable(rachel.getPK(), 1d);
        assertEquals(6, token.balanceOf(rachel.getPK()), DELTA);
        assertEquals(30, token.owner().getBalance(), DELTA); 

        //buy a ticket and a half
        token.payable(rachel.getPK(), 7d);
        assertEquals(7, token.balanceOf(rachel.getPK()), DELTA);
        assertEquals(35, token.owner().getBalance(), DELTA);
    }
}
