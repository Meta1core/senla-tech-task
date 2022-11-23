package org.atm.service;

import org.atm.model.Atm;
import org.atm.model.LoginState;

public interface AtmService {
  void cashOut(int cash);

  void cashIn(int cash);

  int checkBalance();

  void showAtmMenu();

  Atm getConnectedAtm();

  LoginState loginUser(String cardNumber, int pinCode);
}
