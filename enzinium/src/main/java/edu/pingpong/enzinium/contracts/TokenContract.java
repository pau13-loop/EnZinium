package edu.pingpong.enzinium.contracts;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class TokenContract {
    
    private final Map<PublicKey, Double> balances = new HashMap<>();

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

    public String symbol() {
        return this.symbol;
    }

    public void setTotalSupply(int totalSupply) {
        this.totalSupply = totalSupply;
    }

    public int totalSupply() {
        return this.totalSupply;
    }

    public void setTokenPrice(double tokenPrice) {
        this.tokenPrice = tokenPrice;
    }

    public double getTokenPrice() {
        return this.tokenPrice;
    }

    public Map<PublicKey, Double> getBalances() {
        return this.balances;
    }

    @Override
    public String toString() {
        return "\nName: " + this.getName() + 
                "\nSymbol: " + this.symbol() +
                "\nTotal supply: " + this.totalSupply + 
                "\nOwner Public Key: " + this.ownerPK.hashCode();
    }

    public void addOwner(PublicKey PK, double units) {
        //putIfAbsent will add the subject always that still not being included inside the dictionary !
        this.balances.putIfAbsent(PK, units);
    }

    public int numOwners() {
        return this.balances.size();
    }

    public double balanceOf(PublicKey owner) {
        //getOrDefault will always return a result, this onw we are asking for or the default value we have assigned
        return this.getBalances().getOrDefault(owner, 0d);
    }
}
