package org.atm.service;

import org.atm.model.LoginState;

public interface BankService {
  boolean withdrawFunds(int withdrawalAmount);

  boolean topUpBalance(int topUpAmount);

  int checkBalance();

  LoginState login(String cardNumber, int pinCode);

  void setInvalidAttempt(String cardNumber);
}
