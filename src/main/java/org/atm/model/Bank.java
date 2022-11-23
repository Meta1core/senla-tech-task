package org.atm.model;

public class Bank {
  private String bankName;
  private String phone;
  private String address;

  public Bank(String bankName, String phone, String address) {
    this.bankName = bankName;
    this.phone = phone;
    this.address = address;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
