package org.atm;

import org.atm.model.Bank;
import org.atm.service.AtmService;
import org.atm.service.BankService;
import org.atm.service.impl.AtmServiceImpl;
import org.atm.service.impl.BankServiceImpl;

public class Main {
  public static void main(String[] args) {
    Bank alfaBank = new Bank("Alfa bank", "+375 (29,44,25) 733-33-32", "ul. Ozheshko, 6a");

    BankService alfaBankService = new BankServiceImpl(alfaBank);
    AtmService alfaAtmService = new AtmServiceImpl(alfaBank, alfaBankService);

    alfaAtmService.showAtmMenu();
  }
}
