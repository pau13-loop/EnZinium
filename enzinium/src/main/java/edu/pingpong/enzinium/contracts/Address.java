package edu.pingpong.enzinium.contracts;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Address {
    
    private PublicKey PK = null;
    private PrivateKey SK = null;
    private double balance = 0d;
    private String symbol = "EZI";

    private void setPk(PublicKey PK) {
        this.PK = PK;
    }

    public PublicKey getPK() {
        return this.PK;
    } 

    private void setSK(PrivateKey SK) {
        this.SK = SK;
    }

    private PrivateKey getSK() {
        return this.SK;
    }

    boolean checkSkPresent() {
        return this.getSK() != null ? true : false;
    }

    public double getBalance() {
        return this.balance;
    }

    public void generateKeyPair() {
        KeyPair keys = GenSig.generateKeyPair();
        this.setPk(keys.getPublic());
        this.setSK(keys.getPrivate());
    }

    @Override
    public String toString() {
        return "\nPublic key: " + getPK().hashCode() + "\nBalance: " + getBalance() + " " + this.symbol + "\n";
    }

    void addEZI(double EZI) {
        this.balance += EZI;
    }

    public void transferEZI(double enziniums) {
        this.balance += enziniums;
    }

    public void send(TokenContract contract, double enziniums) {
        if(this.getBalance() >= enziniums){
            contract.payable(this.getPK(), enziniums);
            this.balance -= enziniums;
        }
    }
}
