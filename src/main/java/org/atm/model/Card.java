package org.atm.model;

public class Card {

  private String cardNumber;
  private int pinCode;
  private int balance;
  private int invalidInputAttempt;
  private long blockDate;

  public Card(String str) {
    String[] parsedArr = str.split(" ");
    this.cardNumber = parsedArr[0];
    this.pinCode = Integer.parseInt(parsedArr[1]);
    this.balance = Integer.parseInt(parsedArr[2]);
    this.invalidInputAttempt = Integer.parseInt(parsedArr[3]);
    this.blockDate = Long.parseLong(parsedArr[4]);
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public int getPinCode() {
    return pinCode;
  }

  public void setPinCode(int pinCode) {
    this.pinCode = pinCode;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public int getInvalidInputAttempt() {
    return invalidInputAttempt;
  }

  public void setInvalidInputAttempt(int invalidInputAttempt) {
    this.invalidInputAttempt = invalidInputAttempt;
  }

  public long getBlockDate() {
    return blockDate;
  }

  public void setBlockDate(long blockDate) {
    this.blockDate = blockDate;
  }
}
