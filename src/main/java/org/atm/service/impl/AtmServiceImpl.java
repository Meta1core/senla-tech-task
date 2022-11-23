package org.atm.service.impl;

import org.atm.database.AtmDataAccess;
import org.atm.database.DataAccess;
import org.atm.menu.AtmMenu;
import org.atm.model.Atm;
import org.atm.model.Bank;
import org.atm.model.LoginState;
import org.atm.service.AtmService;
import org.atm.service.BankService;

public class AtmServiceImpl implements AtmService {
  private final DataAccess<Atm> atmDatabase;
  private final BankService bankService;
  private final Atm currentAtm;

  public AtmServiceImpl(Bank bank, BankService bankService) {
    atmDatabase = new AtmDataAccess();
    currentAtm = atmDatabase.getObject();
    currentAtm.setBank(bank);
    this.bankService = bankService;
  }

  @Override
  public void cashOut(int cash) {
    System.out.println("Sending request to bank...");
    if (currentAtm.getBalance() >= cash) {
      if (bankService.withdrawFunds(cash)) {
        atmDatabase.updateObject(currentAtm);
        currentAtm.setBalance(currentAtm.getBalance() - cash);
        atmDatabase.updateObject(currentAtm);
        System.out.println("Please, take your cash");
      }
    } else {
      System.out.println("ATM doesn't have that much cash");
    }
  }

  @Override
  public void cashIn(int cash) {
    System.out.println("Sending request to bank...");
    if (bankService.topUpBalance(cash)) {
      atmDatabase.updateObject(currentAtm);
      currentAtm.setBalance(currentAtm.getBalance() + cash);
      atmDatabase.updateObject(currentAtm);
      System.out.println("Successful cash top up!");
    }
  }

  @Override
  public int checkBalance() {
    return bankService.checkBalance();
  }

  @Override
  public Atm getConnectedAtm() {
    return currentAtm;
  }

  @Override
  public void showAtmMenu() {
    AtmMenu atmMenu = new AtmMenu(this);
    atmMenu.getUserCredentials();
  }

  @Override
  public LoginState loginUser(String cardNumber, int pinCode) {
    System.out.println("Sending request to bank...");
    return bankService.login(cardNumber, pinCode);
  }
}
