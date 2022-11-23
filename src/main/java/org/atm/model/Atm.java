package org.atm.model;

public class Atm {
  private int atmId;
  private String atmName;

  private int balance;
  private Bank bank;

  private String address;

  public Atm(int atmId, String atmName, Bank bank, String address) {
    this.atmName = atmName;
    this.bank = bank;
    this.address = address;
    this.atmId = atmId;
  }

  public Atm(String str) {
    String[] parsedArr = str.split(" ");
    this.atmId = Integer.parseInt(parsedArr[0]);
    this.balance = Integer.parseInt(parsedArr[1]);
    this.atmName = parsedArr[2];
    this.address = parsedArr[3];
  }

  public int getAtmId() {
    return atmId;
  }

  public void setAtmId(int atmId) {
    this.atmId = atmId;
  }

  public String getAtmName() {
    return atmName;
  }

  public void setAtmName(String atmName) {
    this.atmName = atmName;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public Bank getBank() {
    return bank;
  }

  public void setBank(Bank bank) {
    this.bank = bank;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
