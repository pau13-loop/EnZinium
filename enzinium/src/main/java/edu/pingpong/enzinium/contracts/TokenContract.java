package edu.pingpong.enzinium.contracts;

import java.security.PublicKey;
import java.util.HashMap;

public class TokenContract {
    
    private final HashMap<PublicKey, Double> balances = new HashMap<>();

    private PublicKey ownerPK = null;
    private Address owner = null;
    private String name = null;
    private String symbol = null;
    private int totalSupply = 0;
    private double tokenPrice = 0d;

    public TokenContract(Address owner) {
        this.ownerPK = owner.getPK();
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setTotalSupply(int totalSupply) {
        this.totalSupply = totalSupply;
    }

    public int getTotalSupply() {
        return this.totalSupply;
    }

    public void setTokenPrice(double tokenPrice) {
        this.tokenPrice = tokenPrice;
    }

    public double getTokenPrice() {
        return this.tokenPrice;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + 
                "\nTotal supply: " + this.totalSupply + " " + this.getSymbol();
    }
}
