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
    private double totalSupply = 0d;
    private double tokenPrice = 0d;

    public TokenContract(Address owner) {
        this.ownerPK = owner.getPK();
        this.owner = owner;
    }

    public Address owner() {
        return this.owner;
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

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public double totalSupply() {
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
        return "\nName: " + getName() + "\nSymbol: " + symbol() + "\nTotal supply: " + this.totalSupply
                + "\nOwner Public Key: " + this.ownerPK.hashCode();
    }

    public void addOwner(PublicKey PK, double units) {
        // putIfAbsent will add the subject always that still not being included inside
        // the dictionary !
        this.balances.putIfAbsent(PK, units);
    }

    public int numOwners() {
        return this.balances.size();
    }

    public double balanceOf(PublicKey owner) {
        // getOrDefault will always return a result, this onw we are asking for or the
        // default value we have assigned
        return getBalances().getOrDefault(owner, 0d);
    }

    // transfer sucede una //!SOBRECARGA de m??todos
    public void transfer(PublicKey recipient, Double units) {
        try {
            require(balanceOf(this.ownerPK) >= units);
            getBalances().compute(this.ownerPK, (PK, tokens) -> tokens - units);
            getBalances().put(recipient, balanceOf(recipient) + units);
        } catch (Exception e) {
        } // --> This is called 'fail silently'
    }

    public void transfer(PublicKey sender, PublicKey recipient, double units) {
        try {
            require(getBalances().get(sender) >= units);
            getBalances().compute(sender, (PK, tokens) -> tokens - units);
            getBalances().put(recipient, balanceOf(recipient) + units);
        } catch (Exception e) {
        }
    }

    public void require(Boolean holds) throws Exception {
        if (!holds) {
            throw new Exception();
        }
    }

    public void owners() {
        getBalances().keySet().forEach(k -> {
            if (!k.equals(this.ownerPK)) {
                System.out.println("Owner: " + k.hashCode() + " " + getBalances().get(k) + " " + symbol());
            }
        });
    }

    public int totalTokensSold() {
        int tokenSold = 0;
        for (Map.Entry<PublicKey, Double> entry : this.balances.entrySet()) {
            if (!entry.getKey().equals(this.ownerPK)) {
                tokenSold += entry.getValue();
            }
        }
        return tokenSold;
    }

    public void payable(PublicKey recipient, Double enziniums) {
        try {
            require(enziniums >= getTokenPrice());
            Double units = Math.floor(enziniums / tokenPrice);
            transfer(recipient, units);
            this.owner.transferEZI(enziniums - (enziniums - (tokenPrice * units)));
        } catch (Exception e) {
        }
    }
}
