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


    // transfer sucede una //!SOBRECARGA de métodos
    public void transfer(PublicKey recipient, Double units) {
        try {
            this.require(this.balanceOf(this.ownerPK) >= units);
            this.getBalances().compute(this.ownerPK, (PK, tokens) -> tokens - units );
            this.getBalances().put(recipient, balanceOf(recipient) + units);
        }
        catch(Exception e){}// --> This is called 'fail silently'
    }

    public void transfer(PublicKey sender, PublicKey recipient, double units) {
        try{
            this.require(this.getBalances().get(sender) >= units);
            this.getBalances().compute(sender, (PK, tokens) -> tokens - units);
            this.getBalances().put(recipient, balanceOf(recipient) + units);
        }
        catch(Exception e) {}
    }

    public void require(Boolean holds) throws Exception {
        if(!holds) {
            throw new Exception();
        }
    }

    public void owners(){
        for(Map.Entry<PublicKey, Double> entry : this.balances.entrySet()) {
            if(!entry.getKey().equals(this.ownerPK)){
                System.out.println("Owner: " + entry.getKey().hashCode() + " " + entry.getValue() + " " + this.symbol());
            }
        }
        /** this.getBalances().entrySet()
                        .stream()
                        .filter(o -> o != this.ownerPK)
                        .forEach(System.out.println(this.getBalances()));*/

        // Chequear método David //! INTERESANTE

        //! Método David Felta Gelpi
        /**
         * for (PublicKey pk : this.getBalances().keySet()) {
            if (!pk.equals(this.ownerPK)) {
                System.out.println("Owner: " + pk.hashCode() + " " 
                                             + getBalances().get(pk) + " "
                                             + this.symbol());
            }
        }
         */
    }

    public int totalTokensSold() {
        int tokenSold = 0;
        for(Map.Entry<PublicKey, Double> entry : this.balances.entrySet()) {
            if(!entry.getKey().equals(this.ownerPK)){
                tokenSold += entry.getValue();
            }
        }
        return tokenSold;
        /**
         * return this.getBalances().entrySet()
                                .stream()
                                .map(n -> n + Map.Entry::getValue);
         */
        
    }

    public void payable(PublicKey recipient, Double enziniums) {
        try{
            require(enziniums >= this.getTokenPrice());
            Double units = Math.floor(enziniums / tokenPrice);
            this.transfer(recipient, units);
            this.owner.transferEZI(enziniums - (enziniums - (tokenPrice * units)));
        }
        catch(Exception e) {}
    }
}
